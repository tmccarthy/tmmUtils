package au.id.tmm.utilities.collection

import com.google.common.collect.ImmutableBiMap

import scala.collection.JavaConverters._
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable

/**
  * An implementation of a bidirectional map, backed by an instance of [[com.google.common.collect.ImmutableBiMap]].
  * @tparam K the key type
  * @tparam V the value type
  */
class BiMap[K, V] private(private val underlying: ImmutableBiMap[K, V])
  extends Map[K, V]
    with PartialFunction[K, V] {

  override def isDefinedAt(key: K): Boolean = underlying.containsKey(key)

  override def apply(key: K): V = underlying.get(key)

  override def iterator: Iterator[(K, V)] = {
    underlying.entrySet().iterator.asScala.map(entry => (entry.getKey, entry.getValue))
  }

  /**
    * Returns new instance of the map backed by the [[com.google.common.collect.ImmutableBiMap#inverse inverse]] of the
    * underlying [[com.google.common.collect.ImmutableBiMap <code>ImmutableBiMap</code>]]
    */
  lazy val inverse: BiMap[V, K] = new BiMap(underlying.inverse)

  override def +[V1 >: V](entry: (K, V1)): BiMap[K, V1] = {
    val newUnderlying = ImmutableBiMap.builder[K, V1]()
      .putAll(underlying)
      .put(entry._1, entry._2)
      .build()

    new BiMap(newUnderlying)
  }

  override def get(key: K) = Option(underlying.get(key))

  override def -(key: K): BiMap[K, V] = {
    val newUnderlyingBuilder = ImmutableBiMap.builder[K, V]()

    underlying.entrySet().asScala.foreach { entry =>
      if (entry.getKey != key) {
        newUnderlyingBuilder.put(entry)
      }
    }

    new BiMap(newUnderlyingBuilder.build())
  }

  override def empty: Map[K, V] = BiMap.empty.asInstanceOf[Map[K, V]]

  override def foreach[U](f: ((K, V)) => U): Unit = {
    underlying.entrySet().asScala.foreach { javaEntry =>
      f((javaEntry.getKey, javaEntry.getValue))
    }
  }

  override def size: Int = underlying.size()
}

object BiMap {

  implicit def canBuildFrom[K, V]: CanBuildFrom[Iterable[(K, V)], (K, V), BiMap[K, V]] = new BiMapCanBuildFrom[K, V]

  val empty: BiMap[Nothing, Nothing] = new BiMap[Nothing, Nothing](ImmutableBiMap.of())

  def apply[K, V](entries: (K, V)*): BiMap[K, V] = {
    new BiMapCanBuildFrom[K, V]().apply(entries).result()
  }

  def newBuilder[K, V](): BiMapBuilder[K, V] = new BiMapBuilder[K, V]()

  class BiMapCanBuildFrom[K, V] extends CanBuildFrom[Iterable[(K, V)], (K, V), BiMap[K, V]] {
    override def apply(from: Iterable[(K, V)]): mutable.Builder[(K, V), BiMap[K, V]] = {
      val builder = this.apply()

      builder ++= from

      builder
    }

    override def apply(): mutable.Builder[(K, V), BiMap[K, V]] = new BiMapBuilder()
  }

  class BiMapBuilder[K, V] extends mutable.Builder[(K, V), BiMap[K, V]] {
    private var underlyingBuilder = ImmutableBiMap.builder[K, V]()

    override def +=(elem: (K, V)): BiMapBuilder.this.type = {
      underlyingBuilder.put(elem._1, elem._2)

      this
    }

    override def clear(): Unit = underlyingBuilder = ImmutableBiMap.builder[K, V]()

    override def result(): BiMap[K, V] = new BiMap(underlyingBuilder.build())
  }
}