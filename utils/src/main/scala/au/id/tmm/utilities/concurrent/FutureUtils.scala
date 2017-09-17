package au.id.tmm.utilities.concurrent

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Awaitable, Future}
import scala.util.Try

object FutureUtils {
  implicit class TryFutureOps[A](aTry: Try[A]) {
    def toFuture: Future[A] = Future.fromTry(aTry)
  }

  def await[A](awaitable: Awaitable[A], atMost: Duration = Duration.Inf): A = Await.result(awaitable, atMost)
}
