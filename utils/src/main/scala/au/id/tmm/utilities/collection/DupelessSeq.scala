package au.id.tmm.utilities.collection

import au.id.tmm.utilities.collection.DupelessSeq.DupelessSeqCBF

import scala.collection.generic._
import scala.collection.immutable.{IndexedSeq, Vector}
import scala.collection.{AbstractSeq, IndexedSeqLike, mutable}

/**
  * A sequence without duplicates, and a constant-time `contains` lookup.
  */
class DupelessSeq[A] private (private val iterationOrder: Vector[A], private val elements: Set[A])
  extends AbstractSeq[A]
    with IndexedSeq[A]
    with GenericTraversableTemplate[A, DupelessSeq]
    with IndexedSeqLike[A, DupelessSeq[A]] {

  override def companion: DupelessSeq.type = DupelessSeq

  override def length: Int = iterationOrder.length

  override def apply(idx: Int): A = iterationOrder(idx)

  override def reverse: DupelessSeq[A] = new DupelessSeq(
    iterationOrder = this.iterationOrder.reverse,
    elements = this.elements,
  )

  override def reverseIterator: Iterator[A] = this.iterationOrder.reverseIterator

  override def iterator: Iterator[A] = iterationOrder.iterator

  /**
    * Always returns a reference to this collection, since it will never contain duplicates
    */
  override def distinct: DupelessSeq[A] = this

  /**
    * Tests if some element is contained in this collection. This is a constant time operation.
    */
  @inline
  override def contains[A1 >: A](elem: A1): Boolean = elements.asInstanceOf[Set[Any]].contains(elem)

  private def isBuiltToDupelessSeq[B, That](bf: CanBuildFrom[DupelessSeq[A], B, That]): Boolean = {
    bf match {
      case _: DupelessSeqCBF[_] => true
      case _ => false
    }
  }

  /**
    * Appends the given element to this sequence if it is not already in the sequence. Otherwise, returns this sequence.
    */
  override def +:[B >: A, That](elem: B)(implicit bf: CanBuildFrom[DupelessSeq[A], B, That]): That = {
    if (!isBuiltToDupelessSeq(bf)) {
      return super.+:(elem)(bf)
    }

    if (this.contains(elem)) {
      return this.asInstanceOf[That]
    }

    new DupelessSeq(
      iterationOrder = this.iterationOrder.+:(elem),
      elements = this.elements.asInstanceOf[Set[B]] + elem,
    ).asInstanceOf[That]
  }

  /**
    * Appends the given element to this sequence if it is not already in the sequence. Otherwise, returns this sequence.
    */
  override def :+[B >: A, That](elem: B)(implicit bf: CanBuildFrom[DupelessSeq[A], B, That]): That = {
    if (!isBuiltToDupelessSeq(bf)) {
      return super.:+(elem)(bf)
    }

    if (this.contains(elem)) {
      return this.asInstanceOf[That]
    }

    new DupelessSeq(
      iterationOrder = this.iterationOrder :+ elem,
      elements = this.elements.asInstanceOf[Set[B]] + elem,
    ).asInstanceOf[That]
  }

  override def splitAt(n: Int): (DupelessSeq[A], DupelessSeq[A]) = {
    val splitVectors = iterationOrder.splitAt(n)

    val leftSplit = new DupelessSeq[A](
      iterationOrder = splitVectors._1,
      elements = splitVectors._1.toSet,
    )

    val rightSplit = new DupelessSeq[A](
      iterationOrder = splitVectors._2,
      elements = splitVectors._2.toSet,
    )

    (leftSplit, rightSplit)
  }

  override def padTo[B >: A, That](len: Int, elem: B)(implicit bf: CanBuildFrom[DupelessSeq[A], B, That]): That = {
    if (!isBuiltToDupelessSeq(bf)) {
      return super.padTo(len, elem)(bf)
    }

    if (this.contains(elem)) {
      this.asInstanceOf[That]
    } else {
      this :+ elem
    }

  }

  override def sorted[B >: A](implicit ord: Ordering[B]): DupelessSeq[A] = new DupelessSeq(
    iterationOrder = this.iterationOrder.sorted(ord),
    elements = this.elements,
  )

  def -(elem: A): DupelessSeq[A] = {
    if (this.contains(elem)) {
      new DupelessSeq(
        iterationOrder = this.iterationOrder.filterNot(_ == elem),
        elements = this.elements - elem,
      )
    } else {
      this
    }
  }

  override def toList: List[A] = this.iterationOrder.toList

  override def toSet[B >: A]: Set[B] = this.elements.asInstanceOf[Set[B]]

  override def toVector: Vector[A] = this.iterationOrder
}

object DupelessSeq extends IndexedSeqFactory[DupelessSeq] {
  private val EMPTY: DupelessSeq[Any] = new DupelessSeq(Vector.empty, Set.empty)

  override def empty[A]: DupelessSeq[A] = EMPTY.asInstanceOf[DupelessSeq[A]]

  def apply[A](): DupelessSeq[A] = empty

  override def apply[A](elems: A*): DupelessSeq[A] = new DupelessSeq(elems.distinct.toVector, elems.toSet)

  override def newBuilder[A]: DupelessSeqBuilder[A] = new DupelessSeqBuilder()

  class DupelessSeqBuilder[A] extends mutable.Builder[A, DupelessSeq[A]] {
    private val set: mutable.Set[A] = mutable.Set()
    private val iterationOrder: mutable.ArrayBuffer[A] = mutable.ArrayBuffer()

    override def +=(elem: A): DupelessSeqBuilder.this.type = {
      if (!set.contains(elem)) {
        set += elem
        iterationOrder += elem
      }

      this
    }

    override def clear(): Unit = {
      set.clear()
      iterationOrder.clear()
    }

    override def result(): DupelessSeq[A] = {
      if (set.isEmpty) {
        DupelessSeq.empty
      } else {
        new DupelessSeq[A](iterationOrder.toVector, set.toSet)
      }
    }

    override def sizeHint(size: Int): Unit = {
      set.sizeHint(size)
      iterationOrder.sizeHint(size)
    }
  }

  class DupelessSeqCBF[A] extends CanBuildFrom[DupelessSeq[_], A, DupelessSeq[A]] {
    override def apply(from: DupelessSeq[_]): mutable.Builder[A, DupelessSeq[A]] = from.genericBuilder

    override def apply(): mutable.Builder[A, DupelessSeq[A]] = newBuilder[A]
  }

  implicit def cbf[A]: CanBuildFrom[Coll, A, DupelessSeq[A]] = new DupelessSeqCBF[A]
}