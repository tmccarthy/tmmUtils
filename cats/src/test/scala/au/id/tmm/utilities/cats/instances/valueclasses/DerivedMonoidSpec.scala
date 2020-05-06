package au.id.tmm.utilities.cats.instances.valueclasses

import cats.kernel.Monoid
import cats.kernel.laws.discipline.MonoidTests
import cats.tests.CatsSuite

class DerivedMonoidSpec extends CatsSuite {

  private implicit val monoid: Monoid[WrappedList[Int]] = deriveMonoid[WrappedList[Int], List[Int]](
    unwrap = _.list,
    wrap = WrappedList.apply,
  )

  checkAll("Monoid for wrapper around list", MonoidTests[WrappedList[Int]].monoid)

}
