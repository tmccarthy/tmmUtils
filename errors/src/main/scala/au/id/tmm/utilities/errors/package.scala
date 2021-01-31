package au.id.tmm.utilities

package object errors {

  type ExceptionOr[+A]    = Either[Exception, A]
  type ThrowableOr[+A]    = Either[Throwable, A]
  type ErrorMessageOr[+A] = Either[String, A]

}
