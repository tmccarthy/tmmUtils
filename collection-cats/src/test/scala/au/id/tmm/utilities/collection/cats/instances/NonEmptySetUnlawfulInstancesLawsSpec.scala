package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.NonEmptySet
import au.id.tmm.utilities.collection.cats.instances.ScalacheckInstances.arbitraryNonEmptySet
import au.id.tmm.utilities.collection.cats.instances.nonEmptySet._
import au.id.tmm.utilities.collection.cats.instances.nonEmptySet.unlawful._
import au.id.tmm.utilities.testing.AdHocTestIgnore
import cats.data.Validated
import cats.laws.discipline.arbitrary._
import cats.laws.discipline.{MonadTests, NonEmptyTraverseTests, SemigroupalTests}
import cats.tests.CatsSuite

class NonEmptySetUnlawfulInstancesLawsSpec extends CatsSuite with AdHocTestIgnore {

  private implicit val nonEmptySetIsomorphism: SemigroupalTests.Isomorphisms[NonEmptySet] =
    SemigroupalTests.Isomorphisms.invariant[NonEmptySet]

  checkAll(
    "Monad for NonEmptySet",
    MonadTests[NonEmptySet].monad[Int, Int, Int],
  )
  checkAll(
    "NonEmptyTraverse for NonEmptySet",
    NonEmptyTraverseTests[NonEmptySet].nonEmptyTraverse[Option, Int, Int, Int, Int, Option, Validated[Unit, *]],
  )

  override protected val ignoredTestNames: Set[String] = Set(
    "NonEmptyTraverse for NonEmptySet.nonEmptyTraverse.traverse order consistency",
    // TODO these should probably pass
    "NonEmptyTraverse for NonEmptySet.nonEmptyTraverse.forall is lazy",
    "NonEmptyTraverse for NonEmptySet.nonEmptyTraverse.foldRight is lazy",
    "NonEmptyTraverse for NonEmptySet.nonEmptyTraverse.exists is lazy",
  )

}
