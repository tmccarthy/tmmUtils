package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.{NonEmptyDupelessSeq, NonEmptySet}
import org.scalacheck.{Arbitrary, Cogen, Gen}

object ScalacheckInstances {

  implicit def arbitraryNonEmptySet[A : Arbitrary]: Arbitrary[NonEmptySet[A]] =
    Arbitrary {
      Gen.nonEmptyBuildableOf[Set[A], A](Arbitrary.arbitrary[A]).map(NonEmptySet.fromSetUnsafe)
    }

  implicit def cogenNonEmptySet[A : Cogen : Ordering]: Cogen[NonEmptySet[A]] =
    Cogen.cogenSet[A].contramap(_.toSet)

  implicit def arbitraryNonEmptyDupelessSeq[A : Arbitrary]: Arbitrary[NonEmptyDupelessSeq[A]] =
    Arbitrary {
      Gen.nonEmptyBuildableOf[List[A], A](Arbitrary.arbitrary[A]).map(NonEmptyDupelessSeq.fromIterableUnsafe)
    }

  implicit def cogenNonEmptyDupelessSeq[A : Cogen : Ordering]: Cogen[NonEmptyDupelessSeq[A]] =
    Cogen.cogenList[A].contramap(_.toList)

}
