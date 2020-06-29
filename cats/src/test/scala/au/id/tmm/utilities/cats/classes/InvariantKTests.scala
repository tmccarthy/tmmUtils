package au.id.tmm.utilities.cats.classes

import cats.kernel.laws.IsEq
import cats.kernel.laws.IsEqArrow
import au.id.tmm.utilities.cats.syntax.invariantK._
import cats.arrow.FunctionK
import cats.kernel.Eq
import cats.~>
import org.scalacheck.Arbitrary
import org.typelevel.discipline.Laws
import org.scalacheck.Prop._
import cats.laws.discipline._

trait InvariantKLaws[T[_[_]]] {

  implicit def T: InvariantK[T]

  def invariantKIdentity[F[_]](tf: T[F]): IsEq[T[F]] =
    tf.imapK[F](FunctionK.id)(FunctionK.id) <-> tf

  def invariantKComposition[F[_], G[_], H[_]](
    tf: T[F],
    fFG: F ~> G,
    fGF: G ~> F,
    fGH: G ~> H,
    fHG: H ~> G,
  ): IsEq[T[H]] =
    tf.imapK[G](fFG)(fGF).imapK[H](fGH)(fHG) <-> tf.imapK[H](fGH.compose(fFG))(fGF.compose(fHG))

}

object InvariantKLaws {
  def apply[T[_[_]]](implicit invariantK: InvariantK[T]): InvariantKLaws[T] =
    new InvariantKLaws[T] {
      override implicit def T: InvariantK[T] = invariantK
    }
}

trait InvariantKTests[T[_[_]]] extends Laws {
  def laws: InvariantKLaws[T]

  def invariantK[F[_], G[_], H[_]](
    implicit
    arbTF: Arbitrary[T[F]],
    arbfFG: Arbitrary[F ~> G],
    arbfGF: Arbitrary[G ~> F],
    arbfGH: Arbitrary[G ~> H],
    arbfHG: Arbitrary[H ~> G],
    eqTF: Eq[T[F]],
    eqTH: Eq[T[H]],
  ): RuleSet =
    new DefaultRuleSet(
      name = "invariantK",
      parent = None,
      "invariantK identity" -> forAll[T[F], IsEq[T[F]]](laws.invariantKIdentity[F] _),
      "invariant composition" -> forAll[T[F], F ~> G, G ~> F, G ~> H, H ~> G, IsEq[T[H]]](
        laws.invariantKComposition[F, G, H] _,
      ),
    )

}

object InvariantKTests {
  def apply[T[_[_]] : InvariantK]: InvariantKTests[T] =
    new InvariantKTests[T] {
      override def laws: InvariantKLaws[T] = InvariantKLaws[T]
    }
}
