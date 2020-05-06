package au.id.tmm.utilities.cats.instances.valueclasses

import cats.kernel.CommutativeMonoid

private[valueclasses] class DerivedCommutativeMonoid[A, B : CommutativeMonoid](unwrap: A => B, wrap: B => A)
    extends CommutativeMonoid[A] {
  override def empty: A               = wrap(CommutativeMonoid[B].empty)
  override def combine(x: A, y: A): A = wrap(CommutativeMonoid[B].combine(unwrap(x), unwrap(y)))
}
