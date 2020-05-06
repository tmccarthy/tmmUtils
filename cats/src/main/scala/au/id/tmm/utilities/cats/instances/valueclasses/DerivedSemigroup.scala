package au.id.tmm.utilities.cats.instances.valueclasses

import cats.kernel.Semigroup

private[valueclasses] class DerivedSemigroup[A, B : Semigroup](unwrap: A => B, wrap: B => A) extends Semigroup[A] {
  override def combine(x: A, y: A): A = wrap(Semigroup[B].combine(unwrap(x), unwrap(y)))
}
