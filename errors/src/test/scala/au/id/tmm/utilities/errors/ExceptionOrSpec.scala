package au.id.tmm.utilities.errors

import au.id.tmm.utilities.testing.syntax.TestingEitherOps
import org.scalatest.flatspec.AnyFlatSpec

import scala.util.control.ControlThrowable

class ExceptionOrSpec extends AnyFlatSpec {

  "ExceptionOr.catchIn" should "return success" in {
    val exceptionOrString = ExceptionOr.catchIn("hello")

    assert(exceptionOrString.get === "hello")
  }

  it should "catch exceptions" in {
    val exception = GenericException("hello")

    val exceptionOrString = ExceptionOr.catchIn(throw exception)

    assert(exceptionOrString.leftGet === exception)
  }

  it should "not non-exception throwables" in {
    intercept[ControlThrowable] {
      ExceptionOr.catchIn(throw new ControlThrowable() {})
    }
  }

  "ExceptionOr.catchOnly" should "return success" in {
    assert(ExceptionOr.catchOnly[RuntimeException](()) === Right(()))
  }

  it should "catch the specified exception type" in {
    val runtimeException = new RuntimeException
    assert(ExceptionOr.catchOnly[RuntimeException](throw runtimeException) === Left(runtimeException))
  }

  it should "catch subtypes of the specified exception type" in {
    val illegalArgumentException = new IllegalArgumentException
    assert(ExceptionOr.catchOnly[RuntimeException](throw illegalArgumentException) === Left(illegalArgumentException))
  }

  "ExceptionOr.flatCatch" should "return success" in {
    assert(ExceptionOr.flatCatch(Right(())) === Right(()))
  }

  it should "return a returned exception" in {
    val exception = new Exception
    assert(ExceptionOr.flatCatch(Left(exception)) === Left(exception))
  }

  it should "return a thrown exception" in {
    val exception = new Exception
    assert(ExceptionOr.flatCatch(throw exception) === Left(exception))
  }

  it should "not catch a ControlThrowable" in {
    intercept[ControlThrowable] {
      ExceptionOr.flatCatch(throw new ControlThrowable() {})
    }
  }

}
