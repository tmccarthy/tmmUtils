package au.id.tmm.utilities.collection

import scala.collection.SortedSet
import scala.collection.immutable.TreeSet

object CollectionUtils {
  implicit class Crossable[X](xs: Traversable[X]) {
    def cross[Y](ys: Traversable[Y]): Traversable[(X, Y)] = for {x <- xs; y <- ys} yield (x, y)
  }

  implicit class Sortable[X](sortableSet: Iterable[X]) {
    def toSortedSet(implicit ordering: Ordering[X]): SortedSet[X] = TreeSet(sortableSet.toList :_*)(ordering)
  }
}
