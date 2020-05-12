package au.id.tmm.utilities.collection.cats

import au.id.tmm.utilities.collection.NonEmptySet
import cats.data.{NonEmptyList, NonEmptyVector}

package object syntax {

  implicit def toNonEmptySetOps[A](nonEmptySet: NonEmptySet[A]): NonEmptySetOps[A] = new NonEmptySetOps(nonEmptySet)

  implicit def nelToNonEmptySetOps[A](nel: NonEmptyList[A]): ToNonEmptySetOps.Nel[A] = new ToNonEmptySetOps.Nel[A](nel)
  implicit def nevToNonEmptySetOps[A](nev: NonEmptyVector[A]): ToNonEmptySetOps.Nev[A] =
    new ToNonEmptySetOps.Nev[A](nev)

}
