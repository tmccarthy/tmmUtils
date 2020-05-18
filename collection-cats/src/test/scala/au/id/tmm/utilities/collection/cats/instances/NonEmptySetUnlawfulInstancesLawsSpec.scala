package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.NonEmptySet
import au.id.tmm.utilities.collection.cats.instances.ScalacheckInstances.arbitraryNonEmptySet
import au.id.tmm.utilities.collection.cats.instances.nonEmptySet._
import au.id.tmm.utilities.collection.cats.instances.nonEmptySet.unlawful._
import cats.laws.discipline.{MonadTests, SemigroupalTests}
import cats.tests.CatsSuite

class NonEmptySetUnlawfulInstancesLawsSpec extends CatsSuite {

  private implicit val nonEmptySetIsomorphism: SemigroupalTests.Isomorphisms[NonEmptySet] =
    SemigroupalTests.Isomorphisms.invariant[NonEmptySet]

  checkAll(
    "Monad for NonEmptySet",
    MonadTests[NonEmptySet].monad[Int, Int, Int],
  )

}
