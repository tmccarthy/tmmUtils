package au.id.tmm.utilities.resources

import java.io.Closeable

import au.id.tmm.utilities.resources.ManagedResourceUtils.ExtractableManagedResourceOps
import au.id.tmm.utilities.testing.ImprovedFlatSpec

import scala.util.{Failure, Success}

class ManagedResourceUtilsSpec extends ImprovedFlatSpec {

  "the toTry method" should "return the result if the managed result was successful" in {
    val testResource = new Closeable {
      override def close(): Unit = {}
      def greeting = "Hello World"
    }

    val actualResult = resource.managed(testResource)
      .map(_.greeting)
      .toTry

    assert(actualResult === Success("Hello World"))
  }

  it should "throw with the correct exception if the managed result failed" in {
    val exception = new RuntimeException()

    val testResource = new Closeable {
      override def close(): Unit = {}
      def greeting: Any = throw exception
    }

    val actualResult = resource.managed(testResource)
      .map(_.greeting)
      .toTry

    assert(actualResult === Failure(new ManagedResourceException(Vector(exception))))
  }

  it should "throw with the correct exceptions if the managed result and close call failed" in {
    val greetingsException = new RuntimeException()
    val closeException = new RuntimeException()

    val testResource = new Closeable {
      override def close(): Unit = throw closeException
      def greeting: Any = throw greetingsException
    }

    val actualResult = resource.managed(testResource)
      .map(_.greeting)
      .toTry

    assert(actualResult === Failure(new ManagedResourceException(Vector(greetingsException, closeException))))
  }
}
