package au.id.tmm.utilities.testing.scalacheck.instances

import au.id.tmm.utilities.testing.Fruit
import org.scalacheck.{Arbitrary, Cogen, Gen}

trait FruitInstances {

  implicit val tmmUtilsScalacheckArbitraryForFruit: Arbitrary[Fruit] = Arbitrary(Gen.oneOf(Fruit.ALL))
  implicit val tmmUtilsScalacheckCogenForFruit: Cogen[Fruit]         = Cogen.cogenInt.contramap(_.hashCode)

}
