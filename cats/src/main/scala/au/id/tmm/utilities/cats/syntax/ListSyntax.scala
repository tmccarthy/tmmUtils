package au.id.tmm.utilities.cats.syntax

import cats.data.NonEmptyList

final class ListSyntax[A] private[syntax] (list: List[A]) {

  def groupByNelUniversalEquals[K](f: A => K): Map[K, NonEmptyList[A]] =
    list.groupBy(f).map {
      case (k, list) => k -> NonEmptyList.fromListUnsafe(list)
    }

  def groupMapNelUniversalEquals[K, B](key: A => K)(f: A => B): Map[K, NonEmptyList[B]] =
    list.groupMap(key)(f).map {
      case (k, list) => k -> NonEmptyList.fromListUnsafe(list)
    }

}
