package au.id.tmm.utilities.errors.syntax

import au.id.tmm.utilities.errors.{ErrorMessageOr, GenericException}

final class ErrorMessageOrOps[+A] private[syntax] (errorMessageOrA: ErrorMessageOr[A]) {

  def wrapLeftInException: Either[GenericException, A] = errorMessageOrA.left.map(GenericException.apply)

}
