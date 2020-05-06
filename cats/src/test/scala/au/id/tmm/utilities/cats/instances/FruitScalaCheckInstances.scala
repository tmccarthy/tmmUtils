package au.id.tmm.utilities.cats.instances

import au.id.tmm.utilities.testing.Fruit
import org.scalacheck.{Arbitrary, Cogen, Gen}

object FruitScalaCheckInstances {

  implicit val arbitraryFruit: Arbitrary[Fruit] = Arbitrary(Gen.oneOf(Fruit.ALL))

  implicit val fruitCogen: Cogen[Fruit] = {
    val hashFruit: Fruit => Int = (Fruit.ALL.zipWithIndex.toMap _)

    Cogen[Fruit](hashFruit.andThen(_.toLong))
  }

}
