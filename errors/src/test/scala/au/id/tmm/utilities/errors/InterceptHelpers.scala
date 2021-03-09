package au.id.tmm.utilities.errors

import munit.Assertions

import scala.util.control.ControlThrowable

private[errors] object InterceptHelpers {
  def interceptControlThrowable[A](f: => A): Unit =
    try {
      f
      Assertions.fail("Expected ControlThrowable but none was thrown")
    } catch {
      case _: ControlThrowable => ()
    }
}
