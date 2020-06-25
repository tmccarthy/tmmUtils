package au.id.tmm.utilities.cats.instances.valueclasses

import au.id.tmm.utilities.testing.scalacheck.instances.fruit._
import au.id.tmm.utilities.testing.Fruit
import cats.laws.discipline.FunctorTests
import cats.tests.CatsSuite
import cats.{Functor, ~>}

class DerivedFunctorSpec extends CatsSuite {

  private implicit val functor: Functor[WrappedList] = deriveFunctor[WrappedList, List](
    wrap = λ[List ~> WrappedList](WrappedList.apply(_)),
    unwrap = λ[WrappedList ~> List](_.list),
  )

  checkAll("Functor for wrapper around list", FunctorTests[WrappedList].functor[Int, Fruit, Boolean])

}
