package au.id.tmm.utilities.concurrent

import au.id.tmm.utilities.concurrent.FutureUtils.TryFutureOps
import au.id.tmm.utilities.testing.ImprovedFlatSpec

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

class FutureUtilsSpec extends ImprovedFlatSpec {

  "a successful try" can "be converted to a future" in {
    val asFuture = Success("asdf").toFuture

    val actualValue = Await.result(asFuture, Duration.Inf)

    assert(actualValue === "asdf")
  }

  "a failed try" can "be converted to a future" in {
    val exception = new Exception()
    val asFuture = Failure(exception).toFuture

    val thrownException = intercept[Exception](Await.result(asFuture, Duration.Inf))

    assert(thrownException eq exception)
  }

}
