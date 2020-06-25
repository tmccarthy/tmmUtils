package au.id.tmm.utilities.testing.scalacheck.instances

import au.id.tmm.utilities.testing.TrafficLight
import org.scalacheck.{Arbitrary, Cogen, Gen}

trait TrafficLightInstances {

  implicit val tmmUtilsScalacheckArbitraryForTrafficLight: Arbitrary[TrafficLight] = Arbitrary(
    Gen.oneOf(TrafficLight.ALL),
  )
  implicit val tmmUtilsScalacheckCogenForTrafficLight: Cogen[TrafficLight] = Cogen.cogenInt.contramap(_.hashCode)

}
