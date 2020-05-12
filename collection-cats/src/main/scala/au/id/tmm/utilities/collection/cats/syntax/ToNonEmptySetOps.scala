package au.id.tmm.utilities.collection.cats.syntax

import au.id.tmm.utilities.collection.NonEmptySet
import cats.data.{NonEmptyList, NonEmptyVector}

object ToNonEmptySetOps {

  class Nel[A] private[syntax] (nel: NonEmptyList[A]) {
    def toTmmUtilsNonEmptySet: NonEmptySet[A] =
      NonEmptySet.fromHeadTail(nel.head, nel.tail)
  }

  class Nev[A] private[syntax] (nev: NonEmptyVector[A]) {
    def toTmmUtilsNonEmptySet: NonEmptySet[A] =
      NonEmptySet.fromIterableUnsafe(nev.toVector)
  }

}
