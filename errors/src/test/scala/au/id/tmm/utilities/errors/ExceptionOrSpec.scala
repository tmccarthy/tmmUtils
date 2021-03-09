package au.id.tmm.utilities.errors

import au.id.tmm.utilities.errors.ExceptionOrSpec.NonExceptionThrowable
import au.id.tmm.utilities.testing.syntax.TestingEitherOps
import munit.FunSuite

class ExceptionOrSpec extends FunSuite {

  test("ExceptionOr.catchIn should return success") {
    val exceptionOrString = ExceptionOr.catchIn("hello")

    assertEquals(exceptionOrString.get, "hello")
  }

  test("ExceptionOr.catchIn should catch exceptions") {
    val exception = GenericException("hello")

    val exceptionOrString = ExceptionOr.catchIn(throw exception)

    assertEquals(exceptionOrString.leftGet, exception)
  }

  test("ExceptionOr.catchIn should not non-exception throwables") {
    intercept[NonExceptionThrowable] {
      ExceptionOr.catchIn(throw new NonExceptionThrowable())
    }
  }

  test("ExceptionOr.catchOnly should return success") {
    assertEquals(ExceptionOr.catchOnly[RuntimeException](()), Right(()))
  }

  test("ExceptionOr.catchOnly should catch the specified exception type") {
    val runtimeException = new RuntimeException
    assertEquals(ExceptionOr.catchOnly[RuntimeException](throw runtimeException), Left(runtimeException))
  }

  test("ExceptionOr.catchOnly should catch subtypes of the specified exception type") {
    val illegalArgumentException = new IllegalArgumentException
    assertEquals(
      ExceptionOr.catchOnly[RuntimeException](throw illegalArgumentException),
      Left(illegalArgumentException),
    )
  }

  test("ExceptionOr.flatCatch should return success") {
    assertEquals(ExceptionOr.flatCatch(Right(())), Right(()))
  }

  test("ExceptionOr.flatCatch should return a returned exception") {
    val exception = new Exception
    assertEquals(ExceptionOr.flatCatch(Left(exception)), Left(exception))
  }

  test("ExceptionOr.flatCatch should return a thrown exception") {
    val exception = new Exception
    assertEquals(ExceptionOr.flatCatch(throw exception), Left(exception))
  }

  test("ExceptionOr.flatCatch should not catch a NonExceptionThrowable") {
    intercept[NonExceptionThrowable] {
      ExceptionOr.flatCatch(throw new NonExceptionThrowable())
    }
  }

}

object ExceptionOrSpec {

  private final class NonExceptionThrowable extends Throwable

}
