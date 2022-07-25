package au.id.tmm.utilities.cats.classes

import au.id.tmm.utilities.cats.syntax.invariantK._
import au.id.tmm.utilities.testing.{CoinToss, TrafficLight, WrappedK}
import au.id.tmm.utilities.testing.cats.instances.all._
import au.id.tmm.utilities.testing.scalacheck.instances.all._
import cats.arrow.FunctionK
import cats.data.Validated
import cats.kernel.Eq
import cats.laws.discipline.arbitrary._
import cats.laws.discipline.{AlternativeTests, ExhaustiveCheck, MonadTests, SemigroupalTests, TraverseTests}
import cats.{Alternative, Functor, MonadError, Traverse, ~>}
import munit.{DisciplineSuite, FunSuite}
import org.scalacheck.{Arbitrary, Gen}

import scala.collection.immutable.ArraySeq

class InvariantKSpec extends FunSuite with DisciplineSuite {

  private type WrappedOption[A]     = WrappedK[Option, A]
  private type WrappedList[A]       = WrappedK[List, A]
  private type MonadErrorUnit[F[_]] = MonadError[F, Unit]

  private val wrapWrappedList = new FunctionK[List, WrappedList] {
    override def apply[A](fa: List[A]): WrappedList[A] = WrappedK(fa)
  }

  private val unwrapWrappedList = new FunctionK[WrappedList, List] {
    override def apply[A](fa: WrappedList[A]): List[A] = fa.unwrap
  }

  private val optionToList = new FunctionK[Option, List] {
    override def apply[A](fa: Option[A]): List[A] = fa.toList
  }

  private val listToHeadOption = new FunctionK[List, Option] {
    override def apply[A](fa: List[A]): Option[A] = fa.headOption
  }

  private val listToLastOption = new FunctionK[List, Option] {
    override def apply[A](fa: List[A]): Option[A] = fa.lastOption
  }

  private val listToSet = new FunctionK[List, Set] {
    override def apply[A](fa: List[A]): Set[A] = fa.toSet
  }

  private val setToList = new FunctionK[Set, List] {
    override def apply[A](fa: Set[A]): List[A] = fa.toList
  }

  private implicit val instancesUnderTestForWrappedOption: MonadError[WrappedOption, Unit] = {
    val fFG: Option ~> WrappedOption = new FunctionK[Option, WrappedOption] {
      override def apply[A](fa: Option[A]): WrappedOption[A] = WrappedK(fa)
    }

    val fGF: WrappedOption ~> Option = new FunctionK[WrappedOption, Option] {
      override def apply[A](fa: WrappedOption[A]): Option[A] = fa.unwrap
    }

    InvariantK[MonadErrorUnit].imapK[Option, WrappedOption](MonadError[Option, Unit])(fFG)(fGF)
  }

  private implicit val traverseUnderTestForWrappedList: Traverse[WrappedList] =
    Traverse[List].imapK[WrappedList](wrapWrappedList)(unwrapWrappedList)

  private implicit val alternativeUnderTestForWrappedList: Alternative[WrappedList] =
    Alternative[List].imapK[WrappedList](wrapWrappedList)(unwrapWrappedList)

  private implicit val isomorphismsForWrappedList: SemigroupalTests.Isomorphisms[WrappedList] =
    SemigroupalTests.Isomorphisms.invariant[WrappedList](tmmUtilsInvariantForWrappedK)

  private implicit def exhaustiveCheckForSet[A : ExhaustiveCheck]: ExhaustiveCheck[Set[A]] = ExhaustiveCheck.forSet[A]

  private implicit def exhaustiveCheckForFunction[A : ExhaustiveCheck, B : ExhaustiveCheck]: ExhaustiveCheck[A => B] = {
    val allAs: List[A]     = ExhaustiveCheck[A].allValues
    val allBs: ArraySeq[B] = ExhaustiveCheck[B].allValues.to(ArraySeq.untagged)

    val bs = allAs.indices.map(i => allBs.apply(i % allBs.size))

    ExhaustiveCheck.instance[A => B] {
      allAs.permutations.map { as =>
        val asMap: Map[A, B] = (as zip bs).toMap
        asMap: (A => B)
      }.toList
    }
  }

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

  checkAll(
    "InvariantK for Functor",
    InvariantKTests[Functor].invariantK[Option, List, Set](
      arbTF = Arbitrary(Gen.const(Functor[Option])),
      arbfFG = Arbitrary(
        Gen.const[Option ~> List](optionToList),
      ),
      arbfGF = Arbitrary(
        Gen.oneOf[List ~> Option](
          listToHeadOption,
          listToLastOption,
        ),
      ),
      arbfGH = Arbitrary(
        Gen.const[List ~> Set](
          listToSet,
        ),
      ),
      arbfHG = Arbitrary(
        Gen.const[Set ~> List](
          setToList,
        ),
      ),
      eqTF = functorEq[Option, TrafficLight, CoinToss],
      eqTH = functorEq[Set, TrafficLight, CoinToss],
    ),
  )

  // This is a bit dumb
  implicit def functorEq[F[_], A, B](
    implicit
    arbFA: ExhaustiveCheck[F[A]],
    arbA: ExhaustiveCheck[A],
    arbB: ExhaustiveCheck[B],
    eqFB: Eq[F[B]],
  ): Eq[Functor[F]] =
    new Eq[Functor[F]] {
      override def eqv(x: Functor[F], y: Functor[F]): Boolean =
        ExhaustiveCheck[(F[A], A => B)].allValues.forall { case (fa, f) =>
          Eq[F[B]].eqv(x.map(fa)(f), y.map(fa)(f))
        }
    }

}
