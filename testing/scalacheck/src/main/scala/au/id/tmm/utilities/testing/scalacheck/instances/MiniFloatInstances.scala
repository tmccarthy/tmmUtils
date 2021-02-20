package au.id.tmm.utilities.testing.scalacheck.instances

import au.id.tmm.utilities.testing.MiniFloat
import org.scalacheck.{Arbitrary, Cogen, Gen}

trait MiniFloatInstances {

  implicit val tmmUtilsArbitraryForMiniFloat: Arbitrary[MiniFloat] =
    Arbitrary(Gen.oneOf(MiniFloat.allValues))

  implicit val tmmUtilsCogenForMiniFloat: Cogen[MiniFloat] =
    Cogen.cogenFloat.contramap(_.toFloat)

}
