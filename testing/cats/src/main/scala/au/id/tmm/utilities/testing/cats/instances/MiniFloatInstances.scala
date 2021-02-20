package au.id.tmm.utilities.testing.cats.instances

import au.id.tmm.utilities.testing.MiniFloat
import cats.kernel.{Hash, Order}
import cats.laws.discipline.ExhaustiveCheck

trait MiniFloatInstances {

  implicit val tmmUtilsExhaustiveCheckForMiniFloat: ExhaustiveCheck[MiniFloat] = {
    val allValuesAsList = MiniFloat.allValues.toList

    new ExhaustiveCheck[MiniFloat] {
      override def allValues: List[MiniFloat] = allValuesAsList
    }
  }

  implicit val tmmUtilsOrderForMiniFloat: Order[MiniFloat] with Hash[MiniFloat] = new Order[MiniFloat]
    with Hash[MiniFloat] {
    override def compare(x: MiniFloat, y: MiniFloat): Int = Order[Float].compare(x.toFloat, y.toFloat)
    override def hash(x: MiniFloat): Int                  = Hash[Float].hash(x.toFloat)
  }

}
