package au.id.tmm.utilities.cats.instances

import au.id.tmm.utilities.cats.instances.FruitScalaCheckInstances._
import au.id.tmm.utilities.cats.instances.mapOverKeys._
import au.id.tmm.utilities.testing.Fruit
import cats.laws.discipline.{FunctorTests, MonoidKTests}
import cats.tests.CatsSuite

class MapOverKeysInstancesSpec extends CatsSuite {

  override implicit val generatorDrivenConfig: PropertyCheckConfiguration = PropertyCheckConfiguration(
    minSuccessful = 1_000,
  )

  checkAll("Functor for map over keys", FunctorTests[Map[*, Int]].functor[Int, Fruit, Boolean])
  checkAll("MonoidK for map over keys", MonoidKTests[Map[*, Int]].monoidK[Fruit])
}
