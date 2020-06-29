package au.id.tmm.utilities.testing.cats.instances

import au.id.tmm.utilities.testing.{Wrapped, WrappedK}
import cats.Invariant
import cats.kernel.{Eq, Hash}
import cats.laws.discipline.ExhaustiveCheck

trait WrappedInstances extends LowPriorityWrappedInstances {

  implicit def tmmUtilsHashForWrapped[A : Hash]: Hash[Wrapped[A]] =
    new Hash[Wrapped[A]] {
      override def hash(x: Wrapped[A]): Int                   = Hash[A].hash(x.unwrap)
      override def eqv(x: Wrapped[A], y: Wrapped[A]): Boolean = Eq[A].eqv(x.unwrap, y.unwrap)
    }

  implicit val tmmUtilsInvariantForWrapped: Invariant[Wrapped] = new Invariant[Wrapped] {
    override def imap[A, B](fa: Wrapped[A])(f: A => B)(g: B => A): Wrapped[B] = Wrapped(f(fa.unwrap))
  }

  implicit def tmmUtilsExhaustiveCheckForWrapped[A : ExhaustiveCheck]: ExhaustiveCheck[Wrapped[A]] =
    ExhaustiveCheck.instance(ExhaustiveCheck[A].allValues.map(Wrapped.apply))

  implicit def tmmUtilsHashForWrappedK[F[_], A](implicit hashFA: Hash[F[A]]): Hash[WrappedK[F, A]] =
    new Hash[WrappedK[F, A]] {
      override def hash(x: WrappedK[F, A]): Int                       = Hash[F[A]].hash(x.unwrap)
      override def eqv(x: WrappedK[F, A], y: WrappedK[F, A]): Boolean = Eq[F[A]].eqv(x.unwrap, y.unwrap)
    }

  implicit def tmmUtilsInvariantForWrappedK[F[_] : Invariant]: Invariant[WrappedK[F, *]] =
    new Invariant[WrappedK[F, *]] {
      override def imap[A, B](fa: WrappedK[F, A])(f: A => B)(g: B => A): WrappedK[F, B] =
        WrappedK(Invariant[F].imap(fa.unwrap)(f)(g))
    }

}

private[instances] trait LowPriorityWrappedInstances {

  implicit def tmmUtilsEqForWrapped[A : Eq]: Eq[Wrapped[A]] =
    new Eq[Wrapped[A]] {
      override def eqv(x: Wrapped[A], y: Wrapped[A]): Boolean = Eq[A].eqv(x.unwrap, y.unwrap)
    }

  implicit def tmmUtilsEqForWrappedK[F[_], A](implicit eqFA: Eq[F[A]]): Eq[WrappedK[F, A]] =
    new Eq[WrappedK[F, A]] {
      override def eqv(x: WrappedK[F, A], y: WrappedK[F, A]): Boolean = Eq[F[A]].eqv(x.unwrap, y.unwrap)
    }

}
