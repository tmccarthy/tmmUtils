package au.id.tmm.utilities.concurrent

import au.id.tmm.utilities.concurrent.FutureCollectionUtils.FutureSetOps
import au.id.tmm.utilities.testing.ImprovedFlatSpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class FutureCollectionUtilsSpec extends ImprovedFlatSpec {

  "a collection" can "be filtered by an asynchronous predicate" in {
    val set = Set("apple", "banana", "pear")

    val p: String => Future[Boolean] = s => Future(s.contains("p"))

    val eventuallyAfterFilter = set.filterEventually(p)

    val actualAfterFilter = Await.result(eventuallyAfterFilter, Duration.Inf)

    assert(actualAfterFilter === Set("apple", "pear"))
  }
}
