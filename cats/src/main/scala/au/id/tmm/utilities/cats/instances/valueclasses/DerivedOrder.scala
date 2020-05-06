package au.id.tmm.utilities.cats.instances.valueclasses

import cats.kernel.Order

private[valueclasses] class DerivedOrder[A, B : Order] (
  unwrap: A => B,
) extends Order[A] {
  override def eqv(x: A, y: A): Boolean = Order[B].eqv(unwrap(x), unwrap(y))
  override def compare(x: A, y: A): Int = Order[B].compare(unwrap(x), unwrap(y))
}
