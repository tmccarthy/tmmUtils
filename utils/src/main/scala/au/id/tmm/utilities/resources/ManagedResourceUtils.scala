package au.id.tmm.utilities.resources

import resource.ExtractableManagedResource

import scala.util.{Failure, Success, Try}

object ManagedResourceUtils {
  implicit class ExtractableManagedResourceOps[+R](resource: ExtractableManagedResource[R]) {
    def toTry: Try[R] = resource.either match {
      case Left(exceptions) => Failure(new ManagedResourceException(exceptions.toVector))
      case Right(result) => Success(result)
    }
  }
}
