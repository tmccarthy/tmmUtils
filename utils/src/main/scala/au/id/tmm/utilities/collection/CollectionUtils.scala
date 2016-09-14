package au.id.tmm.utilities.collection

import scala.collection.immutable.{SortedSet, TreeSet}

object CollectionUtils {
  implicit class Crossable[X](xs: Traversable[X]) {
    def cross[Y](ys: Traversable[Y]): Traversable[(X, Y)] = for {x <- xs; y <- ys} yield (x, y)
  }

  implicit class Sortable[X](sortable: Iterable[X]) {
    def toSortedSet(implicit ordering: Ordering[X]): SortedSet[X] = TreeSet(sortable.toList :_*)(ordering)
  }
}
