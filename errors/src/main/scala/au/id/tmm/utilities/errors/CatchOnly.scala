package au.id.tmm.utilities.errors

import au.id.tmm.utilities.errors.CatchOnly.CatchOnlyPartiallyApplied

import scala.reflect.ClassTag

private[errors] trait CatchOnly {

  def catchOnly[T <: Throwable]: CatchOnlyPartiallyApplied[T] = new CatchOnlyPartiallyApplied[T](this)

  def catchOnlyTotallyApplied[T <: Throwable, A](block: => A)(implicit ct: ClassTag[T]): Either[T, A] =
    try {
      Right(block)
    } catch {
      case t: T         => Left(t)
      case t: Throwable => throw t
    }

}

object CatchOnly {

  class CatchOnlyPartiallyApplied[T <: Throwable] private[errors] (self: CatchOnly) {
    def apply[A](block: => A)(implicit ct: ClassTag[T]): Either[T, A] = self.catchOnlyTotallyApplied[T, A](block)
  }

}
