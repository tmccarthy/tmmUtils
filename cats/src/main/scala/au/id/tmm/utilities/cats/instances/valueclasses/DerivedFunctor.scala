package au.id.tmm.utilities.cats.instances.valueclasses

import cats.{Functor, ~>}

private[valueclasses] class DerivedFunctor[F[_], G[_] : Functor] (
  wrap: G ~> F,
  unwrap: F ~> G,
) extends Functor[F] {
  override def map[A, B](fa: F[A])(f: A => B): F[B] = wrap(Functor[G].map(unwrap(fa))(f))
}
