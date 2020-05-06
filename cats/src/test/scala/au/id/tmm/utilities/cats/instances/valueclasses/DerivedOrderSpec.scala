package au.id.tmm.utilities.cats.instances.valueclasses

import cats.kernel.Order
import cats.kernel.laws.discipline.OrderTests
import cats.tests.CatsSuite

class DerivedOrderSpec extends CatsSuite {

  private implicit val order: Order[WrappedList[Int]] = deriveOrder[WrappedList[Int], List[Int]](
    unwrap = _.list,
  )

  checkAll("Order for wrapper around list", OrderTests[WrappedList[Int]].order)

}
