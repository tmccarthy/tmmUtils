package au.id.tmm.utilities.codec

import scala.collection.immutable.ArraySeq

object ScalaVersionDependentBytesRepresentation {

  type ByteArray = ArraySeq[Byte]

  object ByteArray {
    def apply(bytes: Byte*): ByteArray                          = ArraySeq(bytes: _*)
    @inline def wrapUnsafe(byteArray: Array[Byte]): ByteArray   = ArraySeq.unsafeWrapArray(byteArray)
    @inline def unwrapUnsafe(byteArray: ByteArray): Array[Byte] = byteArray.unsafeArray.asInstanceOf[Array[Byte]]
  }

}
