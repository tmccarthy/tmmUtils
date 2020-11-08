package au.id.tmm.utilities.codec.binarycodecs

import org.apache.commons.codec.DecoderException
import org.apache.commons.codec.binary.{BinaryCodec => CommonsBinaryCodec}

import scala.collection.immutable.ArraySeq

object Binary {

  private val validBinaryChars = Set('0', '1')

  def asBinaryString[B : BytesLike](bytes: B): String = new String(
    CommonsBinaryCodec.toAsciiChars(BytesLike[B].unsafeBytes(bytes)),
  )

  private def invalidBinaryChar(char: Char): Boolean = !validBinaryChars.contains(char)

  private def decodeToBytes(string: String): Array[Byte] =
    if (string.exists(invalidBinaryChar)) {
      throw new DecoderException("Contains invalid binary chars")
    } else {
      CommonsBinaryCodec.fromAscii(string.toCharArray)
    }

  def parseBinaryOrThrow(string: String): ArraySeq.ofByte = new ArraySeq.ofByte(decodeToBytes(string))

  def parseBinary(string: String): Either[DecoderException, ArraySeq.ofByte] =
    try {
      Right(parseBinaryOrThrow(string))
    } catch {
      case e: DecoderException => Left(e)
    }

}
