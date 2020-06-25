package au.id.tmm.utilities.testing.scalacheck.instances

import au.id.tmm.utilities.testing.Planet
import org.scalacheck.{Arbitrary, Cogen, Gen}

trait PlanetInstances {

  implicit val tmmUtilsScalacheckArbitraryForPlanet: Arbitrary[Planet] = Arbitrary(Gen.oneOf(Planet.ALL))
  implicit val tmmUtilsScalacheckCogenForPlanet: Cogen[Planet]         = Cogen.cogenInt.contramap(_.hashCode)

  implicit val tmmUtilsScalacheckArbitraryForPlanetFeature: Arbitrary[Planet.Feature] = Arbitrary(
    Gen.oneOf(Planet.Feature.ALL),
  )
  implicit val tmmUtilsScalacheckCogenForPlanetFeature: Cogen[Planet.Feature] = Cogen.cogenInt.contramap(_.hashCode)

}
