package au.id.tmm.utilities.errors

import au.id.tmm.utilities.errors.InterceptHelpers.interceptControlThrowable
import au.id.tmm.utilities.errors.syntax.throwableOrOps
import au.id.tmm.utilities.testing.syntax.TestingEitherOps
import munit.FunSuite

import scala.util.control.ControlThrowable

class ThrowableOrSpec extends FunSuite {

  test("ThrowableOr.catchNonFatal should return success") {
    val throwableOrString = ThrowableOr.catchNonFatal("hello")

    assertEquals(throwableOrString.get, "hello")
  }

  test("ThrowableOr.catchNonFatal should catch non-fatal throwables") {
    val exception = GenericException("hello")

    val throwableOrString = ThrowableOr.catchNonFatal(throw exception)

    assertEquals(throwableOrString.leftGet, exception)
  }

  test("ThrowableOr.catchNonFatal should not catch fatal throwables") {
    interceptControlThrowable {
      ThrowableOr.catchNonFatal(throw new ControlThrowable() {})
    }
  }

  test("ThrowableOr.throwFatal should throw a ControlThrowable") {
    interceptControlThrowable {
      Left(new ControlThrowable() {}).throwFatal
    }
  }

  test("ThrowableOr.throwFatal should not throw for a Right") {
    Right(()).throwFatal
  }

  test("ThrowableOr.throwFatal should not throw an exception") {
    Left(new Exception()).throwFatal
  }

  test("ThrowableOr.throwErrors should throw a ControlThrowable") {
    val throwableOr: Either[Throwable, Unit] = Left(new ControlThrowable() {})

    interceptControlThrowable {
      throwableOr.throwErrors
    }
  }

  test("ThrowableOr.throwErrors should not throw an exception") {
    val throwableOr: Either[Throwable, Unit] = Left(new Exception)

    throwableOr.throwErrors
  }

  test("ThrowableOr.throwErrors should not throw for a Right") {
    val throwableOr: Either[Throwable, Unit] = Right(())

    throwableOr.throwErrors
  }

  test("ThrowableOr.throwErrors should narrow the left-hand type for values where the type is not an exception") {
    val throwableOr: Either[Throwable, Unit] = Right(())

    throwableOr.throwErrors: Either[Exception, Unit]
  }

  test("ThrowableOr.throwErrors should not compile where the left-hand type is an exception subtype") {
    compileErrors {
      """val throwableOr: Either[RuntimeException, Unit] = Right(())

            throwableOr.throwErrors

            succeed
        """
    }
  }

}
