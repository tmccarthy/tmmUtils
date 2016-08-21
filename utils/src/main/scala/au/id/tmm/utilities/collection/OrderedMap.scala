package au.id.tmm.utilities.collection

import scala.collection.generic.ImmutableMapFactory
import scala.collection.immutable.{HashMap, MapLike}

class OrderedMap[K, +V] private (private val map: HashMap[K, V],
                                 private val iterationOrder: Vector[(K, V)]) extends Map[K, V] with MapLike[K, V, OrderedMap[K, V]] {

  override def +[B1 >: V](entry: (K, B1)): OrderedMap[K, B1] = new OrderedMap[K, B1](map + entry, iterationOrder :+ entry)

  override def get(key: K): Option[V] = map.get(key)

  override def iterator: Iterator[(K, V)] = iterationOrder.iterator

  override def -(key: K): OrderedMap[K, V] = {
    val newMap = map - key
    val newIterationOrder = iterationOrder.filterNot { case(k, _) => k == key }

    new OrderedMap(newMap, newIterationOrder)
  }

  override def empty: OrderedMap[K, V] = OrderedMap.EMPTY.asInstanceOf[OrderedMap[K, V]]
}

object OrderedMap extends ImmutableMapFactory[OrderedMap] {
  private val EMPTY: OrderedMap[Any, Any] = new OrderedMap[Any, Any](HashMap(), Vector())

  override def empty[K, V]: OrderedMap[K, V] = EMPTY.asInstanceOf[OrderedMap[K, V]]
}