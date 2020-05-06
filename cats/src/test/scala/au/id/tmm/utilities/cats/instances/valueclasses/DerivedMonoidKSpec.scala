package au.id.tmm.utilities.cats.instances.valueclasses

import cats.laws.discipline.MonoidKTests
import cats.tests.CatsSuite
import cats.{MonoidK, ~>}

class DerivedMonoidKSpec extends CatsSuite {

  private implicit val monoidK: MonoidK[WrappedList] = deriveMonoidK[WrappedList, List](
    wrap = λ[List ~> WrappedList](WrappedList.apply(_)),
    unwrap = λ[WrappedList ~> List](_.list),
  )

  checkAll("MonoidK for wrapper around list", MonoidKTests[WrappedList].monoidK[Int])

}
