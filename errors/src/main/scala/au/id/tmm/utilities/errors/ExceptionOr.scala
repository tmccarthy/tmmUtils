package au.id.tmm.utilities.errors

object ExceptionOr extends CatchOnly {

  def catchIn[A](block: => A): ExceptionOr[A] =
    try Right(block)
    catch {
      case e: Exception => Left(e)
    }

  def flatCatch[A](block: => ExceptionOr[A]): ExceptionOr[A] =
    catchIn(block).flatten

}
