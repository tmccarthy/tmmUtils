package au.id.tmm.utilities.cats.syntax

import cats.MonadError

final class ThrowableMonadErrorOps[F[_], E, A] private (fa: F[A])(implicit F: MonadError[F, E], ev: E <:< Throwable) {

  def wrapExceptionWithMessage(message: String): F[A] =
    F.handleErrorWith(fa)(e => F.raiseError[A](new Exception(message, e).asInstanceOf[E]))

}

object ThrowableMonadErrorOps {

  trait ToThrowableMonadErrorOps {
    implicit def toMonadErrorSyntax[F[_], E, A](
      fa: F[A],
    )(implicit
      F: MonadError[F, E],
      ev: E <:< Throwable,
    ): ThrowableMonadErrorOps[F, E, A] =
      new ThrowableMonadErrorOps[F, E, A](fa)
  }

}
