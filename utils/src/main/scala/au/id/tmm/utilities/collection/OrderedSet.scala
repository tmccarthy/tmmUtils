package au.id.tmm.utilities.collection

import java.util.function.Consumer

import com.google.common.collect.ImmutableSet

import scala.collection.JavaConverters._

class OrderedSet[A] private (private val data: ImmutableSet[A]) extends Set[A] {
  override def contains(elem: A): Boolean = data.contains(elem)

  override def +(elem: A): Set[A] = {
    if (contains(elem)) {
      this
    } else {
      val newData = ImmutableSet.builder[A]
        .addAll(data)
        .add(elem)
        .build

      new OrderedSet(newData)
    }
  }

  override def -(elem: A): Set[A] = {
    if (contains(elem)) {
      val newData = ImmutableSet.builder[A]

      toStream.filterNot(_ == elem).foreach(newData.add)

      new OrderedSet(newData.build)
    } else {
      this
    }
  }

  override def iterator: Iterator[A] = data.iterator.asScala

  override def empty: OrderedSet[A] = OrderedSet()

  override def foreach[U](f: (A) => U): Unit = {
    data.forEach(new Consumer[A] {
      override def accept(element: A): Unit = f(element)
    })
  }

  override def size: Int = data.size
}

object OrderedSet {
  private val EMPTY: OrderedSet[Any] = new OrderedSet(ImmutableSet.of[Any])

  def apply[A](elems: A*): OrderedSet[A] = {
    if (elems.isEmpty) {
      EMPTY.asInstanceOf[OrderedSet[A]]
    } else {
      val data = ImmutableSet.builder[A]

      elems.foreach(data.add)

      wrapping(data.build)
    }
  }

  def wrapping[A](data: ImmutableSet[A]): OrderedSet[A] = new OrderedSet(data)
}