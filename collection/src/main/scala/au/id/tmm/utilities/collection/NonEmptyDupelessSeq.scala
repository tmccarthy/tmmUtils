package au.id.tmm.utilities.collection

import com.github.ghik.silencer.silent

import scala.collection.immutable.{AbstractSeq, ArraySeq, IndexedSeq}

class NonEmptyDupelessSeq[+A] private (val underlying: DupelessSeq[A]) extends AbstractSeq[A] with IndexedSeq[A] {

  override def apply(i: Int): A = underlying.apply(i)

  override def length: Int = underlying.length

  override def reverseIterator: Iterator[A] = underlying.reverseIterator

  override def iterator: Iterator[A] = underlying.iterator

  @inline
  override def contains[A1 >: A](elem: A1): Boolean = underlying.contains(elem)

  def -[A1 >: A](elem: A1): DupelessSeq[A] =
    underlying.-(elem)

  override def toList: List[A] = underlying.toList

  override def toSet[B >: A]: NonEmptySet[B] = NonEmptySet.fromSetUnsafe(underlying.toSet)

  def toArraySeq: ArraySeq[A] = underlying.toArraySeq

  override protected[this] def className: String = "NonEmptyDupelessSeq"

  override def canEqual(other: Any): Boolean = other.isInstanceOf[NonEmptyDupelessSeq[_]]

  override def equals(other: Any): Boolean = other match {
    case that: NonEmptyDupelessSeq[_] =>
      this.underlying == that.underlying
    case _ => false
  }

  override def hashCode(): Int = underlying.hashCode()

  override def reverse: NonEmptyDupelessSeq[A] = new NonEmptyDupelessSeq(underlying.reverse)

  override def distinct: NonEmptyDupelessSeq[A] = this

  override def appended[B >: A](elem: B): NonEmptyDupelessSeq[B] =
    new NonEmptyDupelessSeq[B](underlying.appended(elem))

  override def appendedAll[B >: A](suffix: IterableOnce[B]): NonEmptyDupelessSeq[B] =
    new NonEmptyDupelessSeq[B](underlying.appendedAll(suffix))

  override def prepended[B >: A](elem: B): NonEmptyDupelessSeq[B] =
    new NonEmptyDupelessSeq[B](underlying.prepended(elem))

  override def prependedAll[B >: A](prefix: IterableOnce[B]): NonEmptyDupelessSeq[B] =
    new NonEmptyDupelessSeq[B](underlying.prependedAll(prefix))

  override def padTo[B >: A](len: Int, elem: B): NonEmptyDupelessSeq[B] =
    new NonEmptyDupelessSeq[B](underlying.padTo(len, elem))

  override def sorted[B >: A](implicit ord: Ordering[B]): NonEmptyDupelessSeq[A] =
    new NonEmptyDupelessSeq[A](underlying.sorted[B](ord))

  override def diff[B >: A](that: collection.Seq[B]): DupelessSeq[A] =
    underlying.diff[B](that)

  override def intersect[B >: A](that: collection.Seq[B]): DupelessSeq[A] =
    underlying.intersect(that)

  override def sortBy[B](f: A => B)(implicit ord: Ordering[B]): NonEmptyDupelessSeq[A] =
    new NonEmptyDupelessSeq[A](underlying.sortBy(f)(ord))

  override def indices: Range =
    underlying.indices

  override def isEmpty: Boolean = false

  override def partition(p: A => Boolean): (DupelessSeq[A], DupelessSeq[A]) =
    underlying.partition(p)

  override def unzip[A1, A2](implicit asPair: A => (A1, A2)): (NonEmptyDupelessSeq[A1], NonEmptyDupelessSeq[A2]) =
    underlying.unzip[A1, A2](asPair) match {
      case (left, right) => (new NonEmptyDupelessSeq[A1](left), new NonEmptyDupelessSeq[A2](right))
    }

  override def unzip3[A1, A2, A3](
    implicit
    asTriple: A => (A1, A2, A3),
  ): (NonEmptyDupelessSeq[A1], NonEmptyDupelessSeq[A2], NonEmptyDupelessSeq[A3]) =
    underlying.unzip3[A1, A2, A3](asTriple) match {
      case (left, centre, right) =>
        (new NonEmptyDupelessSeq[A1](left), new NonEmptyDupelessSeq[A2](centre), new NonEmptyDupelessSeq[A3](right))
    }

  override def flatMap[B](f: A => IterableOnce[B]): DupelessSeq[B] =
    underlying.flatMap(f)

  override def collect[B](pf: PartialFunction[A, B]): DupelessSeq[B] =
    underlying.collect(pf)

  override def flatten[B](implicit toIterableOnce: A => IterableOnce[B]): DupelessSeq[B] =
    underlying.flatten

  override def filter(pred: A => Boolean): DupelessSeq[A] =
    underlying.filter(pred)

  override def takeRight(n: Int): DupelessSeq[A] =
    underlying.takeRight(n)

  override def dropRight(n: Int): DupelessSeq[A] =
    underlying.dropRight(n)

  override def take(n: Int): DupelessSeq[A] =
    underlying.take(n)

  override def drop(n: Int): DupelessSeq[A] =
    underlying.drop(n)

  override def last: A = underlying.last

  override def head: A = underlying.head

  @silent("overrides concrete")
  @deprecated("Use .head")
  override def headOption: Option[A] = Some(head)

  @silent("overrides concrete")
  @deprecated("Use .last")
  override def lastOption: Option[A] = Some(last)

  override def splitAt(n: Int): (DupelessSeq[A], DupelessSeq[A]) =
    underlying.splitAt(n)

  override def takeWhile(p: A => Boolean): DupelessSeq[A] =
    underlying.takeWhile(p)

  override def dropWhile(p: A => Boolean): DupelessSeq[A] =
    underlying.dropWhile(p)

  override def tail: DupelessSeq[A] =
    underlying.tail

  override def init: DupelessSeq[A] =
    underlying.init

  override def tails: Iterator[DupelessSeq[A]] =
    underlying.tails

  override def inits: Iterator[DupelessSeq[A]] =
    underlying.inits

  override def foldLeft[B](z: B)(op: (B, A) => B): B =
    underlying.foldLeft(z)(op)

  override def foldRight[B](z: B)(op: (A, B) => B): B =
    underlying.foldRight(z)(op)

  override def fold[A1 >: A](z: A1)(op: (A1, A1) => A1): A1 =
    underlying.fold(z)(op)

  override def reduce[B >: A](op: (B, B) => B): B =
    underlying.reduce(op)

  @silent("overrides concrete")
  @deprecated("Use .reduce")
  override def reduceOption[B >: A](op: (B, B) => B): Option[B] =
    Some(reduce(op))

  override def reduceLeft[B >: A](op: (B, A) => B): B =
    underlying.reduceLeft(op)

  override def reduceRight[B >: A](op: (A, B) => B): B =
    underlying.reduceRight(op)

  @silent("overrides concrete")
  @deprecated("Use .reduceLeft")
  override def reduceLeftOption[B >: A](op: (B, A) => B): Option[B] =
    Some(reduceLeft(op))

  @silent("overrides concrete")
  @deprecated("Use .reduceRight")
  override def reduceRightOption[B >: A](op: (A, B) => B): Option[B] =
    Some(reduceRight(op))

  override def min[B >: A](implicit ord: Ordering[B]): A =
    underlying.min(ord)

  @silent("overrides concrete")
  @deprecated("Use .min")
  override def minOption[B >: A](implicit ord: Ordering[B]): Option[A] =
    Some(underlying.min(ord))

  override def max[B >: A](implicit ord: Ordering[B]): A =
    underlying.max(ord)

  @silent("overrides concrete")
  @deprecated("Use .max")
  override def maxOption[B >: A](implicit ord: Ordering[B]): Option[A] =
    Some(underlying.max(ord))

  override def maxBy[B](f: A => B)(implicit cmp: Ordering[B]): A =
    underlying.maxBy(f)

  @silent("overrides concrete")
  @deprecated("Use .maxBy")
  override def maxByOption[B](f: A => B)(implicit cmp: Ordering[B]): Option[A] =
    Some(underlying.maxBy(f))

  override def minBy[B](f: A => B)(implicit cmp: Ordering[B]): A =
    underlying.minBy(f)

  @silent("overrides concrete")
  @deprecated("Use .minBy")
  override def minByOption[B](f: A => B)(implicit cmp: Ordering[B]): Option[A] =
    Some(underlying.minBy(f))
}

object NonEmptyDupelessSeq {

  def one[A](head: A): NonEmptyDupelessSeq[A] = new NonEmptyDupelessSeq(DupelessSeq(head))

  def fromHeadTail[A](head: A, tail: Iterable[A]): NonEmptyDupelessSeq[A] = {
    val builder = DupelessSeq.newBuilder[A].addOne(head)
    builder.addAll(tail)
    new NonEmptyDupelessSeq(builder.result())
  }

  def of[A](head: A, tail: A*): NonEmptyDupelessSeq[A] = fromHeadTail(head, tail)

  def fromDupelessSeq[A](set: DupelessSeq[A]): Option[NonEmptyDupelessSeq[A]] =
    if (set.isEmpty) None else Some(new NonEmptyDupelessSeq(set))

  def fromDupelessSeqUnsafe[A](set: DupelessSeq[A]): NonEmptyDupelessSeq[A] =
    if (set.isEmpty)
      throw new IllegalArgumentException("Cannot create NonEmptyDupelessSeq from empty set")
    else
      new NonEmptyDupelessSeq(set)

  def fromIterable[A](iterable: IterableOnce[A]): Option[NonEmptyDupelessSeq[A]] =
    iterable match {
      case s: DupelessSeq[A] => fromDupelessSeq(s)
      case i: Iterable[A] => {
        val builder = DupelessSeq.newBuilder[A]
        builder.addAll(i)
        fromDupelessSeq(builder.result())
      }
    }

  def fromIterableUnsafe[A](iterable: IterableOnce[A]): NonEmptyDupelessSeq[A] =
    fromIterable(iterable).getOrElse(
      throw new IllegalArgumentException("Cannot create NonEmptyDupelessSeq from empty set"))

  def fromCons[A](cons: ::[A]): NonEmptyDupelessSeq[A] =
    fromHeadTail(cons.head, cons.tail)

}
