package au.id.tmm.utilities.collection.cats

import au.id.tmm.utilities.collection.NonEmptySet

package object syntax {

  implicit def toNonEmptySetOps[A](nonEmptySet: NonEmptySet[A]): NonEmptySetOps[A] = new NonEmptySetOps(nonEmptySet)

}
