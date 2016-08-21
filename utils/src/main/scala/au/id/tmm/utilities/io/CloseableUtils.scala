package au.id.tmm.utilities.io

import java.io.Closeable
import java.util.concurrent.atomic.AtomicBoolean

object CloseableUtils {
  implicit class ImprovedCloseable(closeable: Closeable) {

    /**
      * Splits the given [[Closeable]] into two. The underlying resource will only be closed when both of the returned
      * references are closed.
      */
    def split(): (Closeable, Closeable) = {
      val closeCheckLock = new Object

      val leftClosed = new AtomicBoolean(false)
      val rightClosed = new AtomicBoolean(false)

      lazy val left: SplitCloseable = new SplitCloseable(closeable, closeCheckLock, leftClosed, rightClosed)
      lazy val right: SplitCloseable = new SplitCloseable(closeable, closeCheckLock, rightClosed, leftClosed)

      (left, right)
    }
  }

  private class SplitCloseable(underlying: Closeable,
                               private val closeCheckLock: Object,
                               private val isClosed: AtomicBoolean,
                               private val isCompanionClosed: AtomicBoolean) extends Closeable {

    override def close(): Unit = closeCheckLock.synchronized {
      isClosed.set(true)

      if (isClosed.get() && isCompanionClosed.get()) {
        underlying.close()
      }
    }
  }
}