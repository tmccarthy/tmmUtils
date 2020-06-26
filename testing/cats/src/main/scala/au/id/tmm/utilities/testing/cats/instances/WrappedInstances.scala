package au.id.tmm.utilities.testing.cats.instances

import au.id.tmm.utilities.testing.Wrapped
import cats.kernel.{Eq, Hash}

trait WrappedInstances extends LowPriorityWrappedInstances {

  implicit def tmmUtilsHashForWrapped[A : Hash]: Hash[Wrapped[A]] = new Hash[Wrapped[A]] {
    override def hash(x: Wrapped[A]): Int = Hash[A].hash(x.unwrap)
    override def eqv(x: Wrapped[A], y: Wrapped[A]): Boolean = Eq[A].eqv(x.unwrap, y.unwrap)
  }

}

private[instances] trait LowPriorityWrappedInstances {

  implicit def tmmUtilsEqForWrapped[A : Eq]: Eq[Wrapped[A]] = new Eq[Wrapped[A]] {
    override def eqv(x: Wrapped[A], y: Wrapped[A]): Boolean = Eq[A].eqv(x.unwrap, y.unwrap)
  }

}
