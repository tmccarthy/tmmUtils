package au.id.tmm.utilities.collection

import java.io.Closeable

import au.id.tmm.utilities.io.CloseableUtils.ImprovedCloseable

import scala.collection.GenTraversableOnce

/**
  * @define closeBoth The [[CloseableIterator]]s returned by this method split the closeable using the
  *                   [[au.id.tmm.utilities.io.CloseableUtils.ImprovedCloseable.split()]] method. Hence, the underlying
  *                   resource will not be closed until *both* of the returned iterators have been closed.
  */
trait CloseableIterator[+A] extends Iterator[A] with Closeable { self =>

  //==== Iterator methods =====

  override def hasNext: Boolean

  override def next(): A

  override def close(): Unit

  //==== Methods for making implementation easier ====

  protected def mapUnderlying[B](f: Iterator[A] => Iterator[B]): CloseableIterator[B]

  protected def splitUnderlying[U >: A](f: Iterator[A] => (Iterator[U], Iterator[U])): (CloseableIterator[U], CloseableIterator[U])

  //==== Methods overridden to improve the return types ====

  override def seq: CloseableIterator[A] = mapUnderlying(_.seq)

  override def take(n: Int): CloseableIterator[A] = mapUnderlying(_.take(n))

  override def drop(n: Int): CloseableIterator[A] = mapUnderlying(_.drop(n))

  override def slice(from: Int, until: Int): CloseableIterator[A] = mapUnderlying(_.slice(from, until))

  override def map[B](f: (A) => B): CloseableIterator[B] = mapUnderlying(_.map(f))

  override def ++[B >: A](that: => GenTraversableOnce[B]): CloseableIterator[B] = mapUnderlying(_ ++ that)

  override def flatMap[B](f: (A) => GenTraversableOnce[B]): CloseableIterator[B] = mapUnderlying(_.flatMap(f))

  override def filter(p: (A) => Boolean): CloseableIterator[A] = mapUnderlying(_.filter(p))

  override def withFilter(p: (A) => Boolean): CloseableIterator[A] = mapUnderlying(_.withFilter(p))

  override def filterNot(p: (A) => Boolean): CloseableIterator[A] = mapUnderlying(_.filterNot(p))

  override def collect[B](pf: PartialFunction[A, B]): CloseableIterator[B] = mapUnderlying(_.collect(pf))

  override def scanLeft[B](z: B)(op: (B, A) => B): CloseableIterator[B] = mapUnderlying(_.scanLeft(z)(op))

  override def scanRight[B](z: B)(op: (A, B) => B): CloseableIterator[B] = mapUnderlying(_.scanRight(z)(op))

  override def takeWhile(p: (A) => Boolean): CloseableIterator[A] = mapUnderlying(_.takeWhile(p))

  override def dropWhile(p: (A) => Boolean): CloseableIterator[A] = mapUnderlying(_.dropWhile(p))

  override def padTo[A1 >: A](len: Int, elem: A1): CloseableIterator[A1] = mapUnderlying(_.padTo(len, elem))

  override def patch[B >: A](from: Int, patchElems: Iterator[B], replaced: Int): CloseableIterator[B] = mapUnderlying(_.patch(from, patchElems, replaced))

  override def zip[B](that: Iterator[B]): CloseableIterator[(A, B)] = mapUnderlying(_.zip(that))

  override def zipWithIndex: CloseableIterator[(A, Int)] = mapUnderlying(_.zipWithIndex)

  override def zipAll[B, A1 >: A, B1 >: B](that: Iterator[B], thisElem: A1, thatElem: B1): CloseableIterator[(A1, B1)] =
    mapUnderlying(_.zipAll(that, thisElem, thatElem))

  /**
    * $closeBoth
    */
  override def partition(p: (A) => Boolean): (CloseableIterator[A], CloseableIterator[A]) = splitUnderlying(_.partition(p))

  /**
    * $closeBoth
    */
  override def span(p: (A) => Boolean): (CloseableIterator[A], CloseableIterator[A]) = splitUnderlying(_.span(p))

  /**
    * $closeBoth
    */
  override def duplicate: (CloseableIterator[A], CloseableIterator[A]) = splitUnderlying(_.duplicate)

  override def grouped[B >: A](size: Int): CloseableGroupedIterator[B] = new CloseableGroupedIterator[B](this, size, size)

  override def sliding[B >: A](size: Int, step: Int): CloseableGroupedIterator[B] = new CloseableGroupedIterator[B](this, size, step)

  override def toIterator: CloseableIterator[A] = this

  @deprecated(message = "Unsupported", since = "0.1")
  override def buffered = throw new UnsupportedOperationException()

  class CloseableGroupedIterator[B >: A](normalIterator: CloseableIterator[A], size: Int, step: Int)
    extends super.GroupedIterator[B](normalIterator, size, step)
      with CloseableIterator[Seq[B]] {

    val underlying = new self.GroupedIterator[B](normalIterator, size, step)

    override protected def mapUnderlying[C](f: (Iterator[Seq[B]]) => Iterator[C]): CloseableIterator[C] = {
      new WrappingCloseableIterator[C](f(underlying), normalIterator)
    }

    override protected def splitUnderlying[U >: Seq[B]](f: (Iterator[Seq[B]]) => (Iterator[U], Iterator[U])): (CloseableIterator[U], CloseableIterator[U]) = {
      val (leftPartition, rightPartition) = f(underlying)

      val (leftCloseable, rightCloseable) = normalIterator.split()

      val left = new WrappingCloseableIterator[U](leftPartition, leftCloseable)
      val right = new WrappingCloseableIterator[U](rightPartition, rightCloseable)

      (left, right)
    }

    override def close() = normalIterator.close()
  }
}

object CloseableIterator {
  def apply[A](iterator: Iterator[A], closeable: Closeable): CloseableIterator[A] =
    new WrappingCloseableIterator[A](iterator, closeable)
}

class WrappingCloseableIterator[+A](private val underlying: Iterator[A], private val closeable: Closeable)
  extends CloseableIterator[A] {

  override def hasNext: Boolean = underlying.hasNext

  override def next(): A = underlying.next()

  override def close(): Unit = closeable.close()

  override protected def mapUnderlying[B](f: Iterator[A] => Iterator[B]): CloseableIterator[B] =
    new WrappingCloseableIterator[B](f(underlying), closeable)


  override protected def splitUnderlying[U >: A](f: (Iterator[A]) => (Iterator[U], Iterator[U])): (CloseableIterator[U], CloseableIterator[U]) = {
    val (leftPartition, rightPartition) = f(underlying)

    val (leftCloseable, rightCloseable) = closeable.split()

    val left = new WrappingCloseableIterator[U](leftPartition, leftCloseable)
    val right = new WrappingCloseableIterator[U](rightPartition, rightCloseable)

    (left, right)
  }
}