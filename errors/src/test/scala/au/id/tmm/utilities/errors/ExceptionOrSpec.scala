package au.id.tmm.utilities.errors

import au.id.tmm.utilities.testing.syntax.TestingEitherOps
import org.scalatest.FlatSpec

import scala.util.control.ControlThrowable

class ExceptionOrSpec extends FlatSpec {

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

}
