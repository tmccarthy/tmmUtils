package au.id.tmm.utilities.codec.binarycodecs

import org.apache.commons.codec.DecoderException
import org.apache.commons.codec.binary.{BinaryCodec => CommonsBinaryCodec}

import scala.collection.immutable.ArraySeq

object Binary {

  private val validBinaryChars = Set('0', '1')

  def asBinaryString(bytes: Array[Byte]): String    = new String(CommonsBinaryCodec.toAsciiChars(bytes))
  def asBinaryString(bytes: ArraySeq[Byte]): String = asBinaryString(bytes.unsafeArray.asInstanceOf[Array[Byte]])
  def asBinaryString(bytes: Iterable[Byte]): String = asBinaryString(bytes.toArray)

  private def invalidBinaryChar(char: Char): Boolean = !validBinaryChars.contains(char)

  private def decodeToBytes(string: String): Array[Byte] =
    if (string.exists(invalidBinaryChar))
      throw new DecoderException("Contains invalid binary chars")
    else
      CommonsBinaryCodec.fromAscii(string.toCharArray)

  def parseBinaryOrThrow(string: String): ArraySeq.ofByte = new ArraySeq.ofByte(decodeToBytes(string))

  def parseBinary(string: String): Either[DecoderException, ArraySeq.ofByte] =
    try Right(parseBinaryOrThrow(string))
    catch {
      case e: DecoderException => Left(e)
    }

  trait Syntax {

    implicit class BinaryStringContext(private val stringContext: StringContext) {
      def binary(subs: Any*): ArraySeq.ofByte = parseBinaryOrThrow(stringContext.s(subs: _*))
    }

    implicit class BinaryStringOps(private val s: String) {
      def parseBinary: Either[DecoderException, ArraySeq.ofByte] = Binary.parseBinary(s)

      def parseBinaryUnsafe: ArraySeq.ofByte = parseBinaryOrThrow(s)
    }

    implicit class BinaryByteArrayOps(private val bytes: ArraySeq[Byte]) {
      def asBinaryString: String = Binary.asBinaryString(bytes)
    }

    implicit class BinaryIterableOps(private val bytes: Iterable[Byte]) {
      def asBinaryString: String = Binary.asBinaryString(bytes)
    }

    implicit class BinaryPrimitiveByteArrayOps(private val bytes: Array[Byte]) {
      def asBinaryString: String = Binary.asBinaryString(bytes)
    }

  }

}
