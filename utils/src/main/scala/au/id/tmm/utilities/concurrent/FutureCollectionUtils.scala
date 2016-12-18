package au.id.tmm.utilities.concurrent

import scala.concurrent.{ExecutionContext, Future}

object FutureCollectionUtils {

  implicit class FutureSetOps[A](futureSet: Set[A]) {

    def filterEventually(p: A => Future[Boolean])(implicit ec: ExecutionContext): Future[Set[A]] = {

      def filterElement(element: A): Future[Option[A]] = {
        p(element).map { matchesPredicate =>
          if (matchesPredicate) Some(element) else None
        }
      }

      val eventuallyFilteredToOptions: Set[Future[Option[A]]] = futureSet.map(filterElement)

      Future.sequence(eventuallyFilteredToOptions).map(_.flatten)
    }
  }
}
