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
    override def compare(x: MiniFloat, y: MiniFloat): Int = MiniFloat.Orderings.IeeeOrdering.compare(x, y)
    override def hash(x: MiniFloat): Int                  = java.lang.Float.hashCode(x.toFloat)
  }

}
