package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.NonEmptySet
import au.id.tmm.utilities.collection.cats.instances.ScalacheckInstances._
import au.id.tmm.utilities.collection.cats.instances.nonEmptySet._
import cats.data.Validated
import cats.kernel.laws.discipline.{HashTests, SemilatticeTests}
import cats.laws.discipline.arbitrary._
import cats.laws.discipline.{SemigroupKTests, UnorderedTraverseTests}
import cats.tests.CatsSuite

// TODO laws check for the "unlawful" monad
class NonEmptySetInstancesLawsSpec extends CatsSuite {

  checkAll("Hash for tmmUtils NonEmptySet", HashTests[NonEmptySet[Int]](catsStdHashForTmmUtilsNonEmptySet).hash)
  checkAll("Semilattice for tmmUtils NonEmptySet", SemilatticeTests[NonEmptySet[Int]].semilattice)
  checkAll("SemigroupK for tmmUtils NonEmptySet", SemigroupKTests[NonEmptySet].semigroupK[Int])
  checkAll(
    "UnorderedTraverse for tmmUtils NonEmptySet",
    UnorderedTraverseTests[NonEmptySet].unorderedTraverse[Int, Int, Int, Validated[Int, *], Option])

}
