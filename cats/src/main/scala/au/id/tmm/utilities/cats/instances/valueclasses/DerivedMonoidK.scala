package au.id.tmm.utilities.cats.instances.valueclasses

import cats.{MonoidK, ~>}

class DerivedMonoidK[F[_], G[_] : MonoidK] private[valueclasses] (wrap: F ~> G, unwrap: G ~> F) extends MonoidK[F] {
  override def empty[A]: F[A]                      = unwrap(MonoidK[G].empty)
  override def combineK[A](x: F[A], y: F[A]): F[A] = unwrap(MonoidK[G].combineK(wrap(x), wrap(y)))
}
