package au.id.tmm.utilities.errors

import au.id.tmm.utilities.errors.ProductExceptionSpec._
import org.scalatest.FlatSpec

class ProductExceptionSpec extends FlatSpec {

  "an exception that is a product type" should "have a message from its toString" in {
    assert(MessageException("hello").getMessage === "MessageException(hello)")
  }

  "an exception with a cause that is a product type" should "have a message from its toString" in {
    assert(MessageExceptionWithCause("hello", new Exception()).getMessage === "MessageExceptionWithCause(hello,java.lang.Exception)")
  }

  it should "have a cause" in {
    val cause = new Exception()

    val exception = MessageExceptionWithCause("hello", cause)

    assert(exception.getCause === cause)
  }

}

object ProductExceptionSpec {
  final case class MessageException(message: String) extends ProductException
  final case class MessageExceptionWithCause(message: String, cause: Exception) extends ProductException.WithCause(cause)
}