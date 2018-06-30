package au.id.tmm.utilities.collection

import java.io.Closeable

import au.id.tmm.utilities.io.CloseableUtils.ImprovedCloseable

import scala.annotation.unchecked.uncheckedVariance
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.ArrayBuffer
import scala.collection.{GenTraversableOnce, mutable}
import scala.language.{higherKinds, reflectiveCalls}

/**
  * An $iterator that exposes a $close method which closes an underlying resource.
  *
  * @define closeableIterator [[au.id.tmm.utilities.collection.CloseableIterator <code>CloseableIterator</code>]]
  * @define close     [[au.id.tmm.utilities.collection.CloseableIterator#close() <code>close()</code>]]
  * @define iterator  [[scala.collection.Iterator <code>Iterator</code>]]
  * @define closeable [[java.io.Closeable <code>Closeable</code>]]
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

  // Signature is copied from the overridden method
  override def to[Col[_]](implicit cbf: CanBuildFrom[Nothing, A, Col[A @uncheckedVariance]]): Col[A @uncheckedVariance] = {
    try {
      super.to(cbf)
    } finally {
      close()
    }
  }

//  @deprecated(message = "Unsupported", since = "0.1")
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

    override def close(): Unit = normalIterator.close()
  }
}

object CloseableIterator {

  /**
    * Construct a $closeableIterator that iterates over the given iterator and closes the given resource. Note that
    * instances created with this method will invoke the $close method via reflection. Where possible, use the
    * `apply` method that accepts a $closeable.
    * @param iterator  the underlying $iterator over which the $closeableIterator will iterate
    * @param closeable an object that exposes a <code>close()</code> method which will be invoked when the
    *                  $closeableIterator is closed.
    */
  def apply[A](iterator: Iterator[A], closeable: {def close(): Unit}): CloseableIterator[A] =
    CloseableIterator(iterator, () => closeable.close())

  /**
    * Construct a $closeableIterator that iterates over the given iterator and closes the given resource.
    * @param iterator   the underlying $iterator over which the $closeableIterator will iterate
    * @param closeable  the resource to which the $close call will be delegated
    */
  def apply[A](iterator: Iterator[A], closeable: Closeable): CloseableIterator[A] =
    new WrappingCloseableIterator[A](iterator, closeable)

  private object NoOpCloseable extends Closeable {
    override def close(): Unit = {}
  }

  /**
    * Construct a $closeableIterator that iterates over the given iterator, with a no-op implementation of the $close
    * method.
    * @param iterator   the underlying $iterator over which the $closeableIterator will iterate
    */
  def withoutResource[A](iterator: Iterator[A]): CloseableIterator[A] =
    new WrappingCloseableIterator[A](iterator, NoOpCloseable)

  implicit class IterableConstruction[+A](iterable: Iterable[A]) {
    def toCloseableIterator: CloseableIterator[A] = withoutResource(iterable.iterator)

    def toCloseableIteratorWith(closeable: Closeable): CloseableIterator[A] = apply(iterable.toIterator, closeable)

    def toCloseableIteratorWith(closeable: {def close(): Unit}): CloseableIterator[A] = apply(iterable.toIterator, closeable)
  }

  implicit class IteratorConstruction[+A](iterator: Iterator[A]) {
    def toCloseableIterator: CloseableIterator[A] = withoutResource(iterator)

    def toCloseableIteratorWith(closeable: Closeable): CloseableIterator[A] = apply(iterator, closeable)

    def toCloseableIteratorWith(closeable: {def close(): Unit}): CloseableIterator[A] = apply(iterator, closeable)
  }

  class CloseableIteratorBuilder[A](initialElems: Iterable[A], closeable: Closeable) extends mutable.Builder[A, CloseableIterator[A]] {
    private[this] var elems: ArrayBuffer[A] = ArrayBuffer[A](initialElems.toSeq:_*)

    override def +=(elem: A): this.type = {
      elems += elem
      this
    }
    override def clear(): Unit = elems.clear()
    override def result(): CloseableIterator[A] = elems.toCloseableIteratorWith(closeable)
  }

  implicit def CloseableIteratorCanBuildFrom[A]: CanBuildFrom[Iterable[_ <: A], A, CloseableIterator[A]] =
    new CanBuildFrom[Iterable[_ <: A], A, CloseableIterator[A]] {
      override def apply(from: Iterable[_ <: A]): mutable.Builder[A, CloseableIterator[A]] = new CloseableIteratorBuilder[A](from, NoOpCloseable)
      override def apply(): mutable.Builder[A, CloseableIterator[A]] = new CloseableIteratorBuilder[A](Nil, NoOpCloseable)
    }

  implicit def CloseableIteratorCanBuildFromOther[A]: CanBuildFrom[CloseableIterator[_], A, CloseableIterator[A]] =
    new CanBuildFrom[CloseableIterator[_], A, CloseableIterator[A]] {
      override def apply(from: CloseableIterator[_]): mutable.Builder[A, CloseableIterator[A]] = new CloseableIteratorBuilder[A](Nil, from)
      override def apply(): mutable.Builder[A, CloseableIterator[A]] = new CloseableIteratorBuilder[A](Nil, NoOpCloseable)
    }
}

/**
  * An implementation of $closeableIterator, which combines an $iterator with a $closeable.
  * @param underlying the underlying $iterator over which the $closeableIterator will iterate
  * @param closeable  the resource to which the $close call will be delegated
  * @tparam A         the element type
  */
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
