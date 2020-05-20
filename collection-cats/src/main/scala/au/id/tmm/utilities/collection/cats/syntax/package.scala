package au.id.tmm.utilities.collection.cats

import au.id.tmm.utilities.collection.{NonEmptyDupelessSeq, NonEmptySet}
import cats.data.{NonEmptyList, NonEmptyVector}

package object syntax {

  implicit def toNonEmptySetOps[A](nonEmptySet: NonEmptySet[A]): NonEmptySetOps[A] =
    new NonEmptySetOps(nonEmptySet)
  implicit def nelToNonEmptySetOps[A](nel: NonEmptyList[A]): ToNonEmptySetOps.Nel[A] =
    new ToNonEmptySetOps.Nel[A](nel)
  implicit def nevToNonEmptySetOps[A](nev: NonEmptyVector[A]): ToNonEmptySetOps.Nev[A] =
    new ToNonEmptySetOps.Nev[A](nev)

  implicit def toNonEmptyDupelessSeqOps[A](nonEmptySet: NonEmptyDupelessSeq[A]): NonEmptyDupelessSeqOps[A] =
    new NonEmptyDupelessSeqOps(nonEmptySet)
  implicit def nelToNonEmptyDupelessSeqOps[A](nel: NonEmptyList[A]): ToNonEmptyDupelessSeqOps.Nel[A] =
    new ToNonEmptyDupelessSeqOps.Nel[A](nel)
  implicit def nevToNonEmptyDupelessSeqOps[A](nev: NonEmptyVector[A]): ToNonEmptyDupelessSeqOps.Nev[A] =
    new ToNonEmptyDupelessSeqOps.Nev[A](nev)

}
