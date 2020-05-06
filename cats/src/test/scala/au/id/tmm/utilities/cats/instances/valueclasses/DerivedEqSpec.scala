package au.id.tmm.utilities.cats.instances.valueclasses

import cats.kernel.Eq
import cats.kernel.laws.discipline.EqTests
import cats.tests.CatsSuite

class DerivedEqSpec extends CatsSuite {

  private implicit val eq: Eq[WrappedList[Int]] = deriveEq[WrappedList[Int], List[Int]](
    unwrap = _.list,
  )

  checkAll("Eq for wrapper around list", EqTests[WrappedList[Int]].eqv)

}
