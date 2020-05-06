package au.id.tmm.utilities.cats.instances

import cats.kernel.{CommutativeMonoid, Eq, Monoid, Order, Semigroup}
import cats.{Functor, ~>}

package object valueclasses {

  def deriveFunctor[F[_], G[_] : Functor](wrap: G ~> F, unwrap: F ~> G): Functor[F] = new DerivedFunctor(wrap, unwrap)

  def deriveEq[A, B : Eq](unwrap: A => B): Eq[A] = new DerivedEq(unwrap)

  def deriveOrder[A, B : Order](unwrap: A => B): Order[A] = new DerivedOrder(unwrap)

  def deriveSemigroup[A, B : Semigroup](unwrap: A => B, wrap: B => A): Semigroup[A] =
    new DerivedSemigroup(unwrap, wrap)

  def deriveMonoid[A, B : Monoid](unwrap: A => B, wrap: B => A): Monoid[A] = new DerivedMonoid(unwrap, wrap)

  def deriveCommutativeMonoid[A, B : CommutativeMonoid](unwrap: A => B, wrap: B => A): CommutativeMonoid[A] =
    new DerivedCommutativeMonoid(unwrap, wrap)

}
