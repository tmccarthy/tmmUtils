package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.NonEmptyDupelessSeq
import au.id.tmm.utilities.collection.cats.instances.ScalacheckInstances._
import au.id.tmm.utilities.collection.cats.instances.nonEmptyDupelessSeq._
import cats.data.Validated
import cats.kernel.laws.discipline.{BandTests, HashTests}
import cats.laws.discipline.arbitrary._
import cats.laws.discipline.{NonEmptyTraverseTests, SemigroupKTests, TraverseTests}
import cats.tests.CatsSuite

class NonEmptyDupelessSeqInstancesLawsSpec extends CatsSuite {

  checkAll("Hash for NonEmptyDupelessSeq", HashTests[NonEmptyDupelessSeq[Int]].hash)
  checkAll("Band for NonEmptyDupelessSeq", BandTests[NonEmptyDupelessSeq[Int]].band)
  checkAll("SemigroupK for NonEmptyDupelessSeq", SemigroupKTests[NonEmptyDupelessSeq].semigroupK[Int])
  checkAll(
    "Traverse for NonEmptyDupelessSeq",
    TraverseTests[NonEmptyDupelessSeq].traverse[Int, Int, Int, Set[Int], Validated[Int, *], Option],
  )
  checkAll(
    "NonEmptyTraverse for NonEmptyDupelessSeq",
    NonEmptyTraverseTests[NonEmptyDupelessSeq].nonEmptyTraverse[Option, Int, Int, Int, Int, Option, Validated[Unit, *]],
  )

  // TODO unlawful tests

}
