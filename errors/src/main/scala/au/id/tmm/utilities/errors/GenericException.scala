package au.id.tmm.utilities.errors

final case class GenericException(message: String, cause: Option[Throwable]) extends ProductException.WithCause(cause)

object GenericException {
  def apply(message: String): GenericException                   = GenericException(message, cause = None)
  def apply(message: String, cause: Throwable): GenericException = GenericException(message, Some(cause))
}
