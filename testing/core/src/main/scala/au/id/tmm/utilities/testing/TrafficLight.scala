package au.id.tmm.utilities.testing

import scala.collection.immutable.ArraySeq

sealed trait TrafficLight {
  override def hashCode: Int =
    this match {
      case TrafficLight.Red    => 0
      case TrafficLight.Yellow => 1
      case TrafficLight.Green  => 2
    }
}

object TrafficLight {
  val ALL: ArraySeq[TrafficLight] = ArraySeq(Red, Yellow, Green)

  case object Red    extends TrafficLight
  case object Yellow extends TrafficLight
  case object Green  extends TrafficLight

  implicit val ordering: Ordering[TrafficLight] = Ordering.by(_.hashCode)
}
