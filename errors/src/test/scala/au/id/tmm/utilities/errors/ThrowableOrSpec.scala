package au.id.tmm.utilities.errors

import au.id.tmm.utilities.testing.syntax.TestingEitherOps
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

}
