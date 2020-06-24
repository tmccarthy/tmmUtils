package au.id.tmm.utilities.testing

package object syntax {

  implicit class TestingEitherOps[L, R](either: Either[L, R]) {
    def get: R = either match {
      case Right(r)                  => r
      case left @ Left(t: Throwable) => throw new AssertionError(s"Expected Right, but was $left", t)
      case left @ Left(_)            => throw new AssertionError(s"Expected Right, but was $left")
    }

    def leftGet: L = either match {
      case right @ Right(_) => throw new AssertionError(s"Expected Left, but was $right")
      case Left(l)          => l
    }
  }

}
