package au.id.tmm.utilities.collection.cats.syntax

import au.id.tmm.utilities.collection.NonEmptySet
import cats.data.{NonEmptyList, NonEmptyVector}

class NonEmptySetOps[A](nonEmptySet: NonEmptySet[A]) {

  def toNel: NonEmptyList[A] = NonEmptyList.fromListUnsafe(nonEmptySet.toList)

  def toNev: NonEmptyVector[A] = NonEmptyVector.fromVectorUnsafe(nonEmptySet.toVector)

}
