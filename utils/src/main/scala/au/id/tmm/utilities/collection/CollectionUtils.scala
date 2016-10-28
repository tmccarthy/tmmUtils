package au.id.tmm.utilities.collection

import scala.collection.immutable.{SortedSet, TreeSet}

object CollectionUtils {
  implicit class Crossable[X](xs: Traversable[X]) {
    def cross[Y](ys: Traversable[Y]): Traversable[(X, Y)] = for {x <- xs; y <- ys} yield (x, y)
  }

  implicit class Sortable[X](sortable: Iterable[X]) {
    def toSortedSet(implicit ordering: Ordering[X]): SortedSet[X] = TreeSet(sortable.toList :_*)(ordering)
  }

  implicit class DoubleMapOps[K](map: Map[K, Double]) {

    def +(that: Map[K, Double]): Map[K, Double] =
      this.mergeWith(that, key => this.map.getOrElse(key, 0d) + that.getOrElse(key, 0d))

    def /(that: Map[K, Double]): Map[K, Double] =
      this.mergeWith(that, key => this.map.getOrElse(key, 0d) / that(key))

    private def mergeWith(that: Map[K, Double], newValueForKey: K => Double): Map[K, Double] = {
      val newKeys = this.map.keySet ++ that.keySet

      val newEntries = newKeys.toStream
        .map(key => key -> newValueForKey(key))

      newEntries.toMap
    }

    def /(k: Double): Map[K, Double] = {
      if (k == 0) {
        throw new ArithmeticException()
      }

      map.mapValues(_ / k)
    }
  }
}
