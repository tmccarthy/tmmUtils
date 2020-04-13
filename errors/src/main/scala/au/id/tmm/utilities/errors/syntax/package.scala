package au.id.tmm.utilities.errors

package object syntax {

  implicit def errorMessageOrOps[A](errorMessageOrA: ErrorMessageOr[A]): ErrorMessageOrOps[A] =
    new ErrorMessageOrOps[A](errorMessageOrA)

  implicit def throwableOrOps[E <: Throwable, A](throwableOrA: Either[E, A]): ThrowableOrOps[E, A] =
    new ThrowableOrOps[E, A](throwableOrA)

}
