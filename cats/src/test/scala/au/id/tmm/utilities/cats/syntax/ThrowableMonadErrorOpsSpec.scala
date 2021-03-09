package au.id.tmm.utilities.cats.syntax

import au.id.tmm.utilities.cats.syntax.monadError._
import cats.instances.try_.catsStdInstancesForTry
import munit.FunSuite

import scala.util.{Failure, Success, Try}

class ThrowableMonadErrorOpsSpec extends FunSuite {

  test("the monad error syntax should allow wrapping an exception with a message") {
    val e               = new Exception("unwrapped")
    val t: Try[Nothing] = Failure(e)
    assertEquals(t.wrapExceptionWithMessage("wrapped").failed.map(_.getMessage), Success("wrapped"))
  }

}
