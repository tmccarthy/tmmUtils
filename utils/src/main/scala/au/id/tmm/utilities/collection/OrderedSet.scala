package au.id.tmm.utilities.collection

import scala.collection.SetLike

class OrderedSet[A] private (private val data: Set[A], private val iterationOrder: Vector[A]) extends Set[A] with SetLike[A, OrderedSet[A]] {
  override def contains(elem: A): Boolean = data.contains(elem)

  override def +(elem: A): OrderedSet[A] = {
    if (this contains elem) {
      this
    } else {
      new OrderedSet(data + elem, iterationOrder :+ elem)
    }
  }

  override def -(elem: A): OrderedSet[A] = {
    if (this.contains(elem)) {
      new OrderedSet(data - elem, iterationOrder.filterNot(_ == elem))
    } else {
      this
    }
  }

  override def iterator: Iterator[A] = iterationOrder.iterator

  override def empty: OrderedSet[A] = OrderedSet.EMPTY.asInstanceOf[OrderedSet[A]]

  override def foreach[U](f: (A) => U): Unit = {
    iterationOrder.foreach(f)
  }

  override def size: Int = data.size
}

object OrderedSet {
  private val EMPTY: OrderedSet[Any] = new OrderedSet(Set.empty, Vector.empty)

  def empty[A]: OrderedSet[A] = EMPTY.asInstanceOf[OrderedSet[A]]

  def apply[A](): OrderedSet[A] = empty

  def apply[A](elems: A*): OrderedSet[A] = new OrderedSet(elems.toSet, elems.distinct.toVector)
}