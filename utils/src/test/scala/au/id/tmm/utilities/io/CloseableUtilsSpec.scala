package au.id.tmm.utilities.io

import java.io.Closeable

import au.id.tmm.utilities.io.CloseableUtils.ImprovedCloseable
import au.id.tmm.utilities.testing.ImprovedFlatSpec
import scala.language.reflectiveCalls

class CloseableUtilsSpec extends ImprovedFlatSpec {

  val dummyCloseable = new Closeable {
    var isClosed = false

    override def close(): Unit = isClosed = true
  }

  behaviour of "the split closeable"

  it should "not close the underlying closeable when only the left of the split closeables has been closed" in {
    val (leftCloseable, _) = dummyCloseable.split()

    leftCloseable.close()

    assert(!dummyCloseable.isClosed)
  }

  it should "not close the underlying closeable when only the right of the split closeables has been closed" in {
    val (_, rightCloseable) = dummyCloseable.split()

    rightCloseable.close()

    assert(!dummyCloseable.isClosed)
  }

  it should "close the underlying closeable when both split closeables have been closed" in {
    val (leftCloseable, rightCloseable) = dummyCloseable.split()

    leftCloseable.close()
    rightCloseable.close()

    assert(dummyCloseable.isClosed)
  }
}
