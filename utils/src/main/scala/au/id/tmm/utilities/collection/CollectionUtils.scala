package au.id.tmm.utilities.collection

import scala.collection.Traversable
import scala.collection.generic.CanBuildFrom
import scala.collection.immutable.{SortedSet, TreeSet}
import scala.language.higherKinds

object CollectionUtils {
  implicit class Crossable[X](xs: Traversable[X]) {

    /**
      * Computes the <a href="https://en.wikipedia.org/wiki/Cartesian_product">cartesian product</a> of the two given
      * collections.
      */
    def cross[Y](ys: Traversable[Y]): Traversable[(X, Y)] = for {x <- xs; y <- ys} yield (x, y)
  }

  implicit class Sortable[X](sortable: Iterable[X]) {
    def toSortedSet(implicit ordering: Ordering[X]): SortedSet[X] = TreeSet(sortable.toList :_*)(ordering)
  }

  implicit class DoubleMapOps[K](map: Map[K, Double]) {

    /**
      * Returns a new `Map`, where the value for each key is the sum of the corresponding values in the input maps. If
      * only one of the maps has a mapping for a given key, its value is used in the resulting map.
      */
    def +(that: Map[K, Double]): Map[K, Double] =
      this.mergeWith(that, key => this.map.getOrElse(key, 0d) + that.getOrElse(key, 0d))

    /**
      * Returns a new `Map`, where the value for each key is the difference between the corresponding values in the
      * input maps. If a mapping exists in the right map, but not in the left map, the resulting value is `0`.
      * @throws java.lang.ArithmeticException if a mapping exists for a key in the left map, but not the right map
      */
    def /(that: Map[K, Double]): Map[K, Double] =
      this.mergeWith(that, key => this.map.getOrElse(key, 0d) / that(key))

    private def mergeWith(that: Map[K, Double], newValueForKey: K => Double): Map[K, Double] = {
      val newKeys = this.map.keySet ++ that.keySet

      val newEntries = newKeys.toStream
        .map(key => key -> newValueForKey(key))

      newEntries.toMap
    }

    /**
      * Returns a new `Map` where each value has been divided by the given scalar.
      */
    def /(k: Double): Map[K, Double] = {
      if (k == 0) {
        throw new ArithmeticException()
      }

      map.mapValues(_ / k)
    }
  }

  implicit class TraversableOps[A, C[X] <: Traversable[X]](traversable: C[A]) {

    /**
      * Returns a new traversable that groups consecutive elements with the same key, as computed by the given function.
      * Inspired by the Python itertools <a href="https://docs.python.org/2/library/itertools.html#itertools.groupby">
      *   groupby</a> method.
      */
    def groupedBy[K](keyFunction: A => K)
                    (implicit
                     groupCbf: CanBuildFrom[C[_], A, C[A]],
                     mainCbf: CanBuildFrom[C[(K, C[A])], (K, C[A]), C[(K, C[A])]]
                    ): C[(K, C[A])] = {

      val keyGroupedIterator: KeyGroupedIterator[K, A, C] = new KeyGroupedIterator(traversable, keyFunction)(groupCbf)

      keyGroupedIterator.to[C]
    }
  }

  private class KeyGroupedIterator[K, A, C[X] <: Traversable[X]](underlying: C[A],
                                                                 keyFunction: A => K,
                                                                )(canBuildFrom: CanBuildFrom[_, A, C[A]]) extends Iterator[(K, C[A])] {
    private val bufferedUnderlyingIterator: BufferedIterator[A] = underlying.toIterator.buffered

    override def hasNext: Boolean = bufferedUnderlyingIterator.hasNext

    override def next(): (K, C[A]) = {
      val firstElement = bufferedUnderlyingIterator.next()
      val key = keyFunction(firstElement)

      val builder = canBuildFrom()

      builder += firstElement

      while (bufferedUnderlyingIterator.headOption.map(keyFunction).contains(key)) {
        builder += bufferedUnderlyingIterator.next()
      }

      (key, builder.result())
    }
  }
}
