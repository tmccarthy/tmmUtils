package au.id.tmm.utilities.errors

import scala.util.control.NonFatal

object ThrowableOr {
  def catchNonFatal[A](block: => A): ThrowableOr[A] =
    try Right(block)
    catch {
      case NonFatal(t) => Left(t)
    }
}
