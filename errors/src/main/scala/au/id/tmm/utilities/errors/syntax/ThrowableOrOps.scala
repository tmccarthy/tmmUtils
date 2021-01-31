package au.id.tmm.utilities.errors.syntax

import au.id.tmm.utilities.errors.{GenericException, StructuredException}

import scala.util.control.NonFatal

final class ThrowableOrOps[E <: Throwable, +A] private[syntax] (throwableOrA: Either[E, A]) {

  def wrapException[E2 <: Throwable](wrapFn: E => E2): Either[E2, A] = throwableOrA.left.map(wrapFn)

  def wrapExceptionWithMessage(message: String): Either[GenericException, A] =
    wrapException(t => GenericException(message, t))

  def wrapExceptionWithStructured(name: String, fields: (String, Any)*): Either[StructuredException, A] =
    wrapException(t => StructuredException(name, fields: _*).withCause(t))

  def getOrThrow: A =
    throwableOrA match {
      case Right(a) => a
      case Left(t)  => throw t
    }

  /**
    * Throws if this is a `Left` and the `Throwable` does not match the `scala.util.control.NonFatal` pattern.
    */
  def throwFatal: Either[E, A] =
    throwableOrA.left.map {
      case e @ NonFatal(_) => e
      case fatal           => throw fatal
    }

  /**
    * Throws if this is a `Left` and the `Throwable` is not an `Exception`.
    */
  def throwErrors(implicit ev: Exception <:< E): Either[Exception, A] =
    throwableOrA.left.map {
      case e: Exception => e
      case t            => throw t
    }

}
