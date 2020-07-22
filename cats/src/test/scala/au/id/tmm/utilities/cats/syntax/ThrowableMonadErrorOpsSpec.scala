package au.id.tmm.utilities.cats.syntax

import org.scalatest.flatspec.AnyFlatSpec
import au.id.tmm.utilities.cats.syntax.monadError._

import scala.util.{Failure, Success, Try}
import cats.instances.try_.catsStdInstancesForTry

class ThrowableMonadErrorOpsSpec extends AnyFlatSpec {

  "the monad error syntax" should "allow wrapping an exception with a message" in {
    val e               = new Exception("unwrapped")
    val t: Try[Nothing] = Failure(e)
    assert(t.wrapExceptionWithMessage("wrapped").failed.map(_.getMessage) === Success("wrapped"))
  }

}
