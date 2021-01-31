package au.id.tmm.utilities.errors

import au.id.tmm.utilities.testing.syntax.TestingEitherOps
import au.id.tmm.utilities.errors.syntax.throwableOrOps
import org.scalatest.flatspec.AnyFlatSpec

import scala.util.control.ControlThrowable

class ThrowableOrSpec extends AnyFlatSpec {

  "ThrowableOr.catchNonFatal" should "return success" in {
    val throwableOrString = ThrowableOr.catchNonFatal("hello")

    assert(throwableOrString.get === "hello")
  }

  it should "catch non-fatal throwables" in {
    val exception = GenericException("hello")

    val throwableOrString = ThrowableOr.catchNonFatal(throw exception)

    assert(throwableOrString.leftGet === exception)
  }

  it should "not catch fatal throwables" in {
    intercept[ControlThrowable] {
      ThrowableOr.catchNonFatal(throw new ControlThrowable() {})
    }
  }

  "ThrowableOr.throwFatal" should "throw a ControlThrowable" in {
    intercept[ControlThrowable] {
      Left(new ControlThrowable() {}).throwFatal
    }
  }

  it should "not throw for a Right" in {
    Right(()).throwFatal

    succeed
  }

  it should "not throw an exception" in {
    Left(new Exception()).throwFatal

    succeed
  }

  "ThrowableOr.throwErrors" should "throw a ControlThrowable" in {
    val throwableOr: Either[Throwable, Unit] = Left(new ControlThrowable() {})

    intercept[ControlThrowable] {
      throwableOr.throwErrors
    }
  }

  it should "not throw an exception" in {
    val throwableOr: Either[Throwable, Unit] = Left(new Exception)

    throwableOr.throwErrors

    succeed
  }

  it should "not throw for a Right" in {
    val throwableOr: Either[Throwable, Unit] = Right(())

    throwableOr.throwErrors

    succeed
  }

  it should "narrow the left-hand type for values where the type is not an exception" in {
    val throwableOr: Either[Throwable, Unit] = Right(())

    throwableOr.throwErrors: Either[Exception, Unit]

    succeed
  }

  it should "not compile where the left-hand type is an exception subtype" in assertDoesNotCompile(
    """val throwableOr: Either[RuntimeException, Unit] = Right(())
      |
      |    throwableOr.throwErrors
      |
      |    succeed
      |""".stripMargin,
  )

}
