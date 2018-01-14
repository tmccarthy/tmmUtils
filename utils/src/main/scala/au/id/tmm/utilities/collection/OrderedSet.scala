package au.id.tmm.utilities.collection

import scala.collection.generic.{CanBuildFrom, SetFactory}
import scala.collection.{SetLike, mutable}

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

  override def empty: OrderedSet[A] = OrderedSet.empty

  override def foreach[U](f: (A) => U): Unit = {
    iterationOrder.foreach(f)
  }

  override def size: Int = data.size
}

object OrderedSet extends SetFactory[OrderedSet] {
  private val EMPTY: OrderedSet[Any] = new OrderedSet(Set.empty, Vector.empty)

  override def empty[A]: OrderedSet[A] = EMPTY.asInstanceOf[OrderedSet[A]]

  def apply[A](): OrderedSet[A] = empty

  override def apply[A](elems: A*): OrderedSet[A] = new OrderedSet(elems.toSet, elems.distinct.toVector)

  override def newBuilder[A]: OrderedSetBuilder[A] = new OrderedSetBuilder()

  class OrderedSetBuilder[A] extends mutable.Builder[A, OrderedSet[A]] {
    private val set: mutable.Set[A] = mutable.Set()
    private val iterationOrder: mutable.ArrayBuffer[A] = mutable.ArrayBuffer()

    override def +=(elem: A): OrderedSetBuilder.this.type = {
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

    override def result(): OrderedSet[A] = {
      if (set.isEmpty) {
        OrderedSet.empty
      } else {
        new OrderedSet[A](set.toSet, iterationOrder.toVector)
      }
    }

    override def sizeHint(size: Int): Unit = {
      set.sizeHint(size)
      iterationOrder.sizeHint(size)
    }
  }

  implicit def cbf[A]: CanBuildFrom[OrderedSet[A], A, OrderedSet[A]] = setCanBuildFrom
}