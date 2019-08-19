package au.id.tmm.utilities.codec

import scala.collection.{GenIterable, immutable}

object ScalaVersionDependentBytesRepresentation {

  final class ByteArray private (private val underlying: Array[Byte]) extends immutable.IndexedSeq[Byte] {
    override def iterator: Iterator[Byte] = underlying.iterator

    override def apply(idx: Int): Byte = underlying.apply(idx)

    override def length: Int = underlying.length

    override def sameElements[B >: Byte](o: GenIterable[B]): Boolean = o match {
      case that: ByteArray => this.underlying.sameElements(that.underlying)
      case _               => super.sameElements(o)
    }

    override def equals(other: Any): Boolean = other match {
      case that: ByteArray => underlying.sameElements(that.underlying)
      case _               => false
    }

    override def hashCode(): Int = java.util.Arrays.hashCode(underlying)
  }

  object ByteArray {
    def apply(bytes: Byte*): ByteArray                          = new ByteArray(bytes.toArray[Byte])
    @inline def wrapUnsafe(byteArray: Array[Byte]): ByteArray   = new ByteArray(byteArray)
    @inline def unwrapUnsafe(byteArray: ByteArray): Array[Byte] = byteArray.underlying
  }

}
