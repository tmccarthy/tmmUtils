package au.id.tmm.utilities.cats.instances.valueclasses

import cats.kernel.CommutativeMonoid
import cats.kernel.laws.discipline.CommutativeMonoidTests
import cats.tests.CatsSuite

class DerivedCommutativeMonoidSpec extends CatsSuite {

  private implicit val monoid: CommutativeMonoid[WrappedInt] = deriveCommutativeMonoid[WrappedInt, Int](
    unwrap = _.asInt,
    wrap = WrappedInt.apply,
  )

  checkAll("CommutativeMonoid for wrapper around list", CommutativeMonoidTests[WrappedInt].monoid)

}
