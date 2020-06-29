package au.id.tmm.utilities.cats.classes

import au.id.tmm.utilities.testing.WrappedK
import au.id.tmm.utilities.cats.syntax.invariantK._
import au.id.tmm.utilities.testing.cats.instances.wrapped._
import au.id.tmm.utilities.testing.scalacheck.instances.wrapped._
import cats.arrow.FunctionK
import cats.data.Validated
import cats.laws.discipline.arbitrary._
import cats.laws.discipline.{AlternativeTests, MonadTests, SemigroupalTests, TraverseTests}
import cats.{Alternative, MonadError, Traverse, ~>}
import org.scalatest
import org.scalatest.flatspec.AnyFlatSpec
import org.typelevel.discipline.scalatest.FlatSpecDiscipline

class InvariantKSpec extends AnyFlatSpec with FlatSpecDiscipline with scalatest.prop.Configuration {

  private type WrappedOption[A] = WrappedK[Option, A]
  private type WrappedList[A]   = WrappedK[List, A]

  private implicit val instancesUnderTestForWrappedOption: MonadError[WrappedOption, Unit] = {
    val fFG: Option ~> WrappedOption = new FunctionK[Option, WrappedOption] {
      override def apply[A](fa: Option[A]): WrappedOption[A] = WrappedK(fa)
    }

    val fGF: WrappedOption ~> Option = new FunctionK[WrappedOption, Option] {
      override def apply[A](fa: WrappedOption[A]): Option[A] = fa.unwrap
    }

    InvariantK[MonadError[*[_], Unit]].imapK[Option, WrappedOption](MonadError[Option, Unit])(fFG)(fGF)
  }

  private implicit val traverseUnderTestForWrappedList: Traverse[WrappedList] =
    Traverse[List].imapK[WrappedList](λ[List ~> WrappedList](WrappedK.apply(_)))(λ[WrappedList ~> List](_.unwrap))

  private implicit val alternativeUnderTestForWrappedList: Alternative[WrappedList] =
    Alternative[List].imapK[WrappedList](λ[List ~> WrappedList](WrappedK.apply(_)))(λ[WrappedList ~> List](_.unwrap))

  private implicit val isomorphismsForWrappedList: SemigroupalTests.Isomorphisms[WrappedList] =
    SemigroupalTests.Isomorphisms.invariant[WrappedList](tmmUtilsInvariantForWrappedK)

  checkAll(
    "Monad for InvariantK[MonadError[WrappedOption]]",
    MonadTests[WrappedOption].monad[Int, Int, Int],
  )

  checkAll(
    "Traverse for InvariantK[Traverse[WrappedList]]",
    TraverseTests[WrappedList].traverse[Int, Int, Int, Set[Int], Validated[Int, *], Option],
  )

  checkAll(
    "Alternative for InvariantK[Alternative[WrappedList]]",
    AlternativeTests[WrappedList].alternative[Int, Int, Int],
  )

}
