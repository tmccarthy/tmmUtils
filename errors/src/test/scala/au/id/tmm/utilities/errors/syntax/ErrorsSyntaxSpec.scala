package au.id.tmm.utilities.errors.syntax

import au.id.tmm.utilities.errors.{ErrorMessageOr, ExceptionOr, GenericException, StructuredException}
import au.id.tmm.utilities.testing.syntax.TestingEitherOps
import org.scalatest.flatspec.AnyFlatSpec

class ErrorsSyntaxSpec extends AnyFlatSpec {

  "an error message or string" can "be converted to an exception" in {
    val errorMessageOrString: ErrorMessageOr[String] = Left("error message")

    assert(errorMessageOrString.wrapLeftInException.leftGet === GenericException("error message"))
  }

  "a throwable or string" can "wrap an exception with another exception" in {
    val cause = new Exception()

    val exceptionOrString: ExceptionOr[String] = Left(cause)

    val wrapped = exceptionOrString.wrapException(e => GenericException("wrapped", e))

    assert(wrapped.leftGet === GenericException("wrapped", cause))
  }

  it can "wrap an exception with a message" in {
    val cause = new Exception()

    val exceptionOrString: ExceptionOr[String] = Left(cause)

    val wrapped = exceptionOrString.wrapExceptionWithMessage("wrapped")

    assert(wrapped.leftGet === GenericException("wrapped", cause))
  }

  it can "wrap an exception with a structured exception" in {
    val cause = new Exception()

    val exceptionOrString: ExceptionOr[String] = Left(cause)

    val wrapped = exceptionOrString.wrapExceptionWithStructured(name = "wrapper", "field1" -> "value1")

    val expectedException = StructuredException(
      name = "wrapper",
      "field1" -> "value1",
    ).withCause(cause)

    assert(wrapped.leftGet === expectedException)
  }

  it can "get the right value" in {
    val success = Right("hello")

    assert(success.getOrThrow === "hello")
  }

  it can "throw the left value" in {
    val exception = GenericException("hello")

    val exceptionOrNothing = Left(exception)

    val thrown: GenericException = intercept[GenericException](exceptionOrNothing.getOrThrow)

    assert(thrown === exception)
  }

}
