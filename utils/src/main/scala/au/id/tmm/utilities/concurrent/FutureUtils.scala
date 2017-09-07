package au.id.tmm.utilities.concurrent

import scala.concurrent.Future
import scala.util.Try

object FutureUtils {
  implicit class TryFutureOps[A](aTry: Try[A]) {
    def toFuture: Future[A] = Future.fromTry(aTry)
  }
}
