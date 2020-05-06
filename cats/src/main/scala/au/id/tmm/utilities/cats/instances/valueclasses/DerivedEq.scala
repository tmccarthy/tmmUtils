package au.id.tmm.utilities.cats.instances.valueclasses

import cats.kernel.Eq

private[valueclasses] class DerivedEq[A, B : Eq](unwrap: A => B) extends Eq[A] {
  override def eqv(x: A, y: A): Boolean = Eq[B].eqv(unwrap(x), unwrap(y))
}
