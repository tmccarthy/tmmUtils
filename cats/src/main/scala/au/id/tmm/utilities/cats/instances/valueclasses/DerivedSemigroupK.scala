package au.id.tmm.utilities.cats.instances.valueclasses

import cats.{SemigroupK, ~>}

class DerivedSemigroupK[F[_], G[_] : SemigroupK] private[valueclasses] (wrap: F ~> G, unwrap: G ~> F)
    extends SemigroupK[F] {
  override def combineK[A](x: F[A], y: F[A]): F[A] = unwrap(SemigroupK[G].combineK(wrap(x), wrap(y)))
}
