package au.id.tmm.utilities.testing.scalacheck.instances

import au.id.tmm.utilities.testing.Animal
import org.scalacheck.{Arbitrary, Cogen, Gen}

trait AnimalInstances {

  implicit val tmmUtilsScalacheckArbitraryForAnimal: Arbitrary[Animal] = Arbitrary(Gen.oneOf(Animal.ALL))
  implicit val tmmUtilsScalacheckCogenForAnimal: Cogen[Animal]         = Cogen.cogenInt.contramap(_.hashCode)

  implicit val tmmUtilsScalacheckArbitraryForAnimalCategory: Arbitrary[Animal.Category] = Arbitrary(
    Gen.oneOf(Animal.Category.ALL),
  )
  implicit val tmmUtilsScalacheckCogenForAnimalCategory: Cogen[Animal.Category] = Cogen.cogenInt.contramap(_.hashCode)

}
