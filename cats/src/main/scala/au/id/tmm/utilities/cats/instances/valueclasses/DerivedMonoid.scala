package au.id.tmm.utilities.cats.instances.valueclasses

import cats.kernel.Monoid

private[valueclasses] class DerivedMonoid[A, B : Monoid] (
  unwrap: A => B,
  wrap: B => A,
) extends Monoid[A] {
  override def empty: A = wrap(Monoid[B].empty)
  override def combine(x: A, y: A): A = wrap(Monoid[B].combine(unwrap(x), unwrap(y)))
}
