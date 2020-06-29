package au.id.tmm.utilities.testing.cats.instances

import au.id.tmm.utilities.testing.TrafficLight
import cats.laws.discipline.ExhaustiveCheck
import cats.{Hash, Show}

trait TrafficLightInstances {

  implicit val tmmUtilsCatsInstancesForTrafficLight
    : Hash[TrafficLight] with Show[TrafficLight] with ExhaustiveCheck[TrafficLight] =
    new Hash[TrafficLight] with Show[TrafficLight] with ExhaustiveCheck[TrafficLight] {
      override def hash(x: TrafficLight): Int                     = x.hashCode()
      override def eqv(x: TrafficLight, y: TrafficLight): Boolean = x == y
      override def show(t: TrafficLight): String                  = t.name
      override def allValues: List[TrafficLight]                  = TrafficLight.ALL.toList
    }

}
