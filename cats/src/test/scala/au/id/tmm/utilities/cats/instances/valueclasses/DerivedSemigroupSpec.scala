package au.id.tmm.utilities.cats.instances.valueclasses

import cats.kernel.Semigroup
import cats.kernel.laws.discipline.SemigroupTests
import cats.tests.CatsSuite

class DerivedSemigroupSpec extends CatsSuite {

  private implicit val semigroup: Semigroup[WrappedList[Int]] = deriveSemigroup[WrappedList[Int], List[Int]](
    unwrap = _.list,
    wrap = WrappedList.apply,
  )

  checkAll("Semigroup for wrapper around list", SemigroupTests[WrappedList[Int]].semigroup)

}
