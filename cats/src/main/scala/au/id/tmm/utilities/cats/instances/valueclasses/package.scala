package au.id.tmm.utilities.cats.instances

import cats.kernel.{CommutativeMonoid, Eq, Monoid, Order, Semigroup}
import cats.{Functor, MonoidK, SemigroupK, ~>}

package object valueclasses {

  def deriveFunctor[F[_], G[_] : Functor](unwrap: F ~> G, wrap: G ~> F): Functor[F] =
    new DerivedFunctor(unwrap, wrap)

  def deriveMonoidK[F[_], G[_] : MonoidK](unwrap: F ~> G, wrap: G ~> F): MonoidK[F] =
    new DerivedMonoidK(unwrap, wrap)

  def deriveSemigroupK[F[_], G[_] : SemigroupK](unwrap: F ~> G, wrap: G ~> F): SemigroupK[F] =
    new DerivedSemigroupK(unwrap, wrap)

  def deriveEq[A, B : Eq](unwrap: A => B): Eq[A] =
    new DerivedEq(unwrap)

  def deriveOrder[A, B : Order](unwrap: A => B): Order[A] =
    new DerivedOrder(unwrap)

  def deriveSemigroup[A, B : Semigroup](unwrap: A => B, wrap: B => A): Semigroup[A] =
    new DerivedSemigroup(unwrap, wrap)

  def deriveMonoid[A, B : Monoid](unwrap: A => B, wrap: B => A): Monoid[A] =
    new DerivedMonoid(unwrap, wrap)

  def deriveCommutativeMonoid[A, B : CommutativeMonoid](unwrap: A => B, wrap: B => A): CommutativeMonoid[A] =
    new DerivedCommutativeMonoid(unwrap, wrap)

}
