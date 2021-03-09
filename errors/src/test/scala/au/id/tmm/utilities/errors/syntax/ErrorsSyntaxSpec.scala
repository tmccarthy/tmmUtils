package au.id.tmm.utilities.errors.syntax

import au.id.tmm.utilities.errors.{ErrorMessageOr, ExceptionOr, GenericException, StructuredException}
import au.id.tmm.utilities.testing.syntax.TestingEitherOps
import munit.FunSuite

class ErrorsSyntaxSpec extends FunSuite {

  test("an error message or string can be converted to an exception") {
    val errorMessageOrString: ErrorMessageOr[String] = Left("error message")

    assertEquals(errorMessageOrString.wrapLeftInException.leftGet, GenericException("error message"))
  }

  test("a throwable or string can wrap an exception with another exception") {
    val cause = new Exception()

    val exceptionOrString: ExceptionOr[String] = Left(cause)

    val wrapped = exceptionOrString.wrapException(e => GenericException("wrapped", e))

    assertEquals(wrapped.leftGet, GenericException("wrapped", cause))
  }

  test("a throwable or string can wrap an exception with a message") {
    val cause = new Exception()

    val exceptionOrString: ExceptionOr[String] = Left(cause)

    val wrapped = exceptionOrString.wrapExceptionWithMessage("wrapped")

    assertEquals(wrapped.leftGet, GenericException("wrapped", cause))
  }

  test("a throwable or string can wrap an exception with a structured exception") {
    val cause = new Exception()

    val exceptionOrString: ExceptionOr[String] = Left(cause)

    val wrapped = exceptionOrString.wrapExceptionWithStructured(name = "wrapper", "field1" -> "value1")

    val expectedException = StructuredException(
      name = "wrapper",
      "field1" -> "value1",
    ).withCause(cause)

    assertEquals(wrapped.leftGet, expectedException)
  }

  test("a throwable or string can get the right value") {
    val success = Right("hello")

    assertEquals(success.getOrThrow, "hello")
  }

  test("a throwable or string can throw the left value") {
    val exception = GenericException("hello")

    val exceptionOrNothing = Left(exception)

    val thrown: GenericException = intercept[GenericException](exceptionOrNothing.getOrThrow)

    assertEquals(thrown, exception)
  }

}
