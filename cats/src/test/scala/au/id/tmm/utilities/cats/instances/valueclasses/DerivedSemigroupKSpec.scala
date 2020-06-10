package au.id.tmm.utilities.cats.instances.valueclasses

import cats.laws.discipline.SemigroupKTests
import cats.tests.CatsSuite
import cats.{SemigroupK, ~>}

class DerivedSemigroupKSpec extends CatsSuite {

  private implicit val semigroupK: SemigroupK[WrappedList] = deriveSemigroupK[WrappedList, List](
    wrap = λ[List ~> WrappedList](WrappedList.apply(_)),
    unwrap = λ[WrappedList ~> List](_.list),
  )

  checkAll("SemigroupK for wrapper around list", SemigroupKTests[WrappedList].semigroupK[Int])

}
