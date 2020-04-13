package au.id.tmm.utilities.errors

import scala.runtime.ScalaRunTime

/**
  * A supertype for `Exception`s that are a `Product`. Allows for `case class` exceptions to have a sensible
  * `getMessage`.
  */
abstract class ProductException extends Exception with Product {
  override def getMessage: String = ScalaRunTime._toString(this)
}

object ProductException {

  /**
    * Extends `ProductException` with support for causes.
    */
  abstract class WithCause(cause: Option[Throwable]) extends ProductException {
    def this(cause: Throwable) = this(Some(cause))

    override def getCause: Throwable = cause.orNull
  }

}
