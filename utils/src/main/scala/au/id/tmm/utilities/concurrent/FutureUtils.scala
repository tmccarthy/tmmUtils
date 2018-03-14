package au.id.tmm.utilities.concurrent

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Awaitable, Future}
import scala.util.Try

object FutureUtils {
  implicit class TryFutureOps[A](aTry: Try[A]) {

    /**
      * Invokes [[scala.concurrent.Future#fromTry]] on this `Try`
      */
    def toFuture: Future[A] = Future.fromTry(aTry)
  }

  /**
    * Just [[scala.concurrent.Await#result]] with a default timeout of [[scala.concurrent.duration.Duration#Inf Inf]]
    */
  def await[A](awaitable: Awaitable[A], atMost: Duration = Duration.Inf): A = Await.result(awaitable, atMost)
}
