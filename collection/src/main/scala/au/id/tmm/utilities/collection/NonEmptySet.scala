package au.id.tmm.utilities.collection

import com.github.ghik.silencer.silent

import scala.collection.{AbstractSet, View}
import scala.collection.immutable.Set

/**
  * A Set that is guaranteed to be non-empty. Equality of elements is based on universal equality.
  */
final class NonEmptySet[A] private (val underlying: Set[A]) extends AbstractSet[A] with Set[A] with Serializable {

  override def toSet[B >: A]: Set[B] = underlying.toSet

  override def incl(elem: A): NonEmptySet[A] = new NonEmptySet(underlying.incl(elem))

  override def excl(elem: A): Set[A] = underlying.excl(elem)

  override def contains(elem: A): Boolean = underlying.contains(elem)

  override def iterator: Iterator[A] = underlying.iterator

  override def diff(that: collection.Set[A]): Set[A] = this.underlying.diff(that)

  override def removedAll(that: IterableOnce[A]): Set[A] = this.underlying.removedAll(that)

  override def subsetOf(that: collection.Set[A]): Boolean = this.underlying.subsetOf(that)

  override def subsets(len: Int): Iterator[Set[A]] = this.underlying.subsets(len)

  override def subsets(): Iterator[Set[A]] = this.underlying.subsets

  override def intersect(that: collection.Set[A]): Set[A] = this.underlying.intersect(that)

  override def isTraversableAgain: Boolean = this.underlying.isTraversableAgain

  override def head: A = this.underlying.head

  @silent("overrides concrete")
  @deprecated("Use .head")
  override def headOption: Some[A] = Some(this.underlying.head)

  override def tail: Set[A] = this.underlying.tail

  override def last: A = this.underlying.last

  @silent("overrides concrete")
  @deprecated("Use .last")
  override def lastOption: Some[A] = Some(this.underlying.last)

  override def view: View[A] = this.underlying.view

  override def sizeCompare(otherSize: Int): Int = this.underlying.sizeCompare(otherSize)

  override def sizeCompare(that: Iterable[_]): Int = this.underlying.sizeCompare(that)

  override def toString: String = mkString("NonEmptySet(", ", ", ")")

  override def map[B](f: A => B): NonEmptySet[B] = new NonEmptySet[B](underlying.map(f))

  def flatMap[B](f: A => NonEmptySet[B]): NonEmptySet[B] = new NonEmptySet[B](underlying.flatMap(f))

  def flatten[B](implicit ev: A <:< NonEmptySet[B]): NonEmptySet[B] = new NonEmptySet[B](underlying.flatten)

  override def canEqual(that: Any): Boolean = that.isInstanceOf[NonEmptySet[_]]

  override def equals(other: Any): Boolean = other match {
    case that: NonEmptySet[_] =>
      super.equals(that) &&
        underlying == that.underlying
    case _ => false
  }

  override def hashCode(): Int = underlying.hashCode()

  override def filter(pred: A => Boolean): Set[A] = underlying.filter(pred)

  override def partition(p: A => Boolean): (Set[A], Set[A]) = underlying.partition(p)

  override def splitAt(n: Int): (Set[A], Set[A]) = underlying.splitAt(n)

  override def take(n: Int): Set[A] = underlying.take(n)

  override def takeRight(n: Int): Set[A] = underlying.takeRight(n)

  override def takeWhile(p: A => Boolean): Set[A] = underlying.takeWhile(p)

  override def span(p: A => Boolean): (Set[A], Set[A]) = underlying.span(p)

  override def drop(n: Int): Set[A] = underlying.drop(n)

  override def dropRight(n: Int): Set[A] = underlying.dropRight(n)

  override def dropWhile(p: A => Boolean): Set[A] = underlying.dropWhile(p)

  override def grouped(size: Int): Iterator[Set[A]] = underlying.grouped(size)

  override def sliding(size: Int): Iterator[Set[A]] = underlying.sliding(size)

  override def sliding(size: Int, step: Int): Iterator[Set[A]] = underlying.sliding(size, step)

  override def init: Set[A] = underlying.init

  override def slice(from: Int, until: Int): Set[A] = underlying.slice(from, until)

  override def flatMap[B](f: A => IterableOnce[B]): Set[B] = underlying.flatMap(f)

  override def collect[B](pf: PartialFunction[A, B]): Set[B] = underlying.collect(pf)

  override def concat[B >: A](suffix: IterableOnce[B]): NonEmptySet[B] =
    new NonEmptySet[B](underlying concat suffix)

  override def reduce[B >: A](op: (B, B) => B): B = super.reduce(op)

  @silent("overrides concrete")
  @deprecated("Use .reduce")
  override def reduceOption[B >: A](op: (B, B) => B): Option[B] = super.reduceOption(op)

  override def reduceLeft[B >: A](op: (B, A) => B): B = super.reduceLeft(op)

  override def reduceRight[B >: A](op: (A, B) => B): B = super.reduceRight(op)

  @silent("overrides concrete")
  @deprecated("Use .reduceLeft")
  override def reduceLeftOption[B >: A](op: (B, A) => B): Some[B] = Some(reduceLeft(op))

  @silent("overrides concrete")
  @deprecated("Use .reduceRight")
  override def reduceRightOption[B >: A](op: (A, B) => B): Some[B] = Some(reduceRight(op))

  override def isEmpty: Boolean = super.isEmpty

  override def min[B >: A](implicit ord: Ordering[B]): A = super.min(ord)

  @silent("overrides concrete")
  @deprecated("Use .min")
  override def minOption[B >: A](implicit ord: Ordering[B]): Some[A] = Some(min(ord))

  override def max[B >: A](implicit ord: Ordering[B]): A = super.max(ord)

  @silent("overrides concrete")
  @deprecated("Use .max")
  override def maxOption[B >: A](implicit ord: Ordering[B]): Some[A] = Some(max(ord))

  override def maxBy[B](f: A => B)(implicit cmp: Ordering[B]): A = super.maxBy(f)

  @silent("overrides concrete")
  @deprecated("Use .maxBy")
  override def maxByOption[B](f: A => B)(implicit cmp: Ordering[B]): Some[A] = Some(maxBy(f)(cmp))

  override def minBy[B](f: A => B)(implicit cmp: Ordering[B]): A = super.minBy(f)

  @silent("overrides concrete")
  @deprecated("Use .minBy")
  override def minByOption[B](f: A => B)(implicit cmp: Ordering[B]): Some[A] = Some(minBy(f)(cmp))

}

object NonEmptySet {

  def one[A](head: A): NonEmptySet[A] = new NonEmptySet(Set(head))

  def fromHeadTail[A](head: A, tail: Iterable[A]): NonEmptySet[A] = {
    val builder = Set.newBuilder[A].addOne(head)
    builder.addAll(tail)
    new NonEmptySet(builder.result())
  }

  def of[A](head: A, tail: A*): NonEmptySet[A] = fromHeadTail(head, tail)

  def fromSet[A](set: Set[A]): Option[NonEmptySet[A]] =
    if (set.isEmpty) None else Some(new NonEmptySet(set))

  def fromSetUnsafe[A](set: Set[A]): NonEmptySet[A] =
    if (set.isEmpty)
      throw new IllegalArgumentException("Cannot create NonEmptySet from empty set")
    else
      new NonEmptySet(set)

  def fromIterable[A](iterable: IterableOnce[A]): Option[NonEmptySet[A]] =
    iterable match {
      case s: Set[A] => fromSet(s)
      case i: Iterable[A] => {
        val builder = Set.newBuilder[A]
        builder.addAll(i)
        fromSet(builder.result)
      }
    }

  def fromIterableUnsafe[A](iterable: IterableOnce[A]): NonEmptySet[A] =
    fromIterable(iterable).getOrElse(throw new IllegalArgumentException("Cannot create NonEmptySet from empty set"))

  def fromCons[A](cons: ::[A]): NonEmptySet[A] =
    fromHeadTail(cons.head, cons.tail)

}
