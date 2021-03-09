package au.id.tmm.utilities.errors

import au.id.tmm.utilities.errors.ProductExceptionSpec._
import munit.FunSuite

class ProductExceptionSpec extends FunSuite {

  test("an exception that is a product type should have a message from its toString") {
    assertEquals(MessageException("hello").getMessage, "MessageException(hello)")
  }

  test("an exception with a cause that is a product type should have a message from its toString") {
    assertEquals(
      MessageExceptionWithCause(
        "hello",
        new Exception(),
      ).getMessage,
      "MessageExceptionWithCause(hello,java.lang.Exception)",
    )
  }

  test("an exception with a cause that is a product type should have a cause") {
    val cause = new Exception()

    val exception = MessageExceptionWithCause("hello", cause)

    assertEquals(exception.getCause, cause)
  }

}

object ProductExceptionSpec {
  final case class MessageException(message: String) extends ProductException
  final case class MessageExceptionWithCause(message: String, cause: Exception)
      extends ProductException.WithCause(cause)
}
