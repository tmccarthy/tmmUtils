package au.id.tmm.utilities.collection

import scala.annotation.tailrec
import scala.collection.mutable

object IteratorUtils {
  implicit class ImprovedIterator[+A](iterator: Iterator[A]) {

    /**
      * Reads either the next `n` elements of the iterator, or to its end, whichever comes first. This method differs
      * from [[scala.collection.Iterator.take]], in that it simply reads the elements from the underlying iterator,
      * leaving it free for continued use after the invocation.
      */
    def readAtMost(n: Int): Vector[A] = {
      val result: mutable.ArrayBuffer[A] = new mutable.ArrayBuffer(n)

      while (result.size < n && iterator.hasNext) {
        result += iterator.next()
      }

      result.toVector
    }

    def readUntil(p: A => Boolean): Vector[A] = {
      val result: mutable.ArrayBuffer[A] = mutable.ArrayBuffer()

      @tailrec
      def readMore(): Unit = {
        if (iterator.hasNext) {
          val element = iterator.next()

          result += element

          if (!p(element)) {
            readMore()
          }
        }
      }

      readMore()

      result.toVector
    }
  }
}
