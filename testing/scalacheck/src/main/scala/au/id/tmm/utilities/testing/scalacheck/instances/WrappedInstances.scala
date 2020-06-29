package au.id.tmm.utilities.testing.scalacheck.instances

import au.id.tmm.utilities.testing.{Wrapped, WrappedK}
import org.scalacheck.{Arbitrary, Cogen}

trait WrappedInstances {

  implicit def tmmUtilsScalacheckArbitraryForWrapped[A : Arbitrary]: Arbitrary[Wrapped[A]] =
    Arbitrary(Arbitrary.arbitrary[A].map(Wrapped.apply[A]))

  implicit def tmmUtilsScalacheckArbitraryForWrappedK[F[_], A](
    implicit
    arbitrary: Arbitrary[F[A]],
  ): Arbitrary[WrappedK[F, A]] =
    Arbitrary(Arbitrary.arbitrary[F[A]].map(WrappedK.apply[F, A]))

  implicit def tmmUtilsScalacheckCogenForWrapped[A : Cogen]: Cogen[Wrapped[A]] = Cogen[A].contramap(_.unwrap)

  implicit def tmmUtilsScalacheckCogenForWrappedK[F[_], A](implicit cogen: Cogen[F[A]]): Cogen[WrappedK[F, A]] =
    Cogen[F[A]].contramap(_.unwrap)

}
