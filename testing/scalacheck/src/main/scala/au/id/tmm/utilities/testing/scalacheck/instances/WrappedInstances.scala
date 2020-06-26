package au.id.tmm.utilities.testing.scalacheck.instances

import au.id.tmm.utilities.testing.Wrapped
import org.scalacheck.{Arbitrary, Cogen}

trait WrappedInstances {

  implicit def tmmUtilsScalacheckArbitraryForWrapped[A : Arbitrary]: Arbitrary[Wrapped[A]] =
    Arbitrary(Arbitrary.arbitrary[A].map(Wrapped.apply[A]))

  implicit def tmmUtilsScalacheckCogenForWrapped[A : Cogen]: Cogen[Wrapped[A]] = Cogen[A].contramap(_.unwrap)

}
