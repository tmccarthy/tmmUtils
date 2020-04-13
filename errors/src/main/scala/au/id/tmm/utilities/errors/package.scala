package au.id.tmm.utilities

import scala.util.control.NonFatal

package object errors {

  type ExceptionOr[+A] = Either[Exception, A]

  object ExceptionOr {
    def catchIn[A](block: => A): ExceptionOr[A] =
      try Right(block) catch {
        case e: Exception => Left(e)
      }
  }

  type ThrowableOr[+A] = Either[Throwable, A]

  object ThrowableOr {
    def catchNonFatal[A](block: => A): ThrowableOr[A] =
      try Right(block) catch {
        case NonFatal(t) => Left(t)
      }
  }

  type ErrorMessageOr[+A] = Either[String, A]

}
