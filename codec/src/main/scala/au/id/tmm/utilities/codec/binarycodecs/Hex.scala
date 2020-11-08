package au.id.tmm.utilities.codec.binarycodecs

import org.apache.commons.codec.DecoderException
import org.apache.commons.codec.binary.{Hex => CommonsHex}

import scala.collection.immutable.ArraySeq

object Hex {

  def asHexString[B: BytesLike](bytes: B): String = CommonsHex.encodeHexString(BytesLike[B].unsafeBytes(bytes))

  private def decodeToBytes(string: String): Array[Byte] = CommonsHex.decodeHex(string)

  def parseHexOrThrow(string: String): ArraySeq.ofByte = new ArraySeq.ofByte(decodeToBytes(string))

  def parseHex(string: String): Either[DecoderException, ArraySeq.ofByte] =
    try {
      Right(parseHexOrThrow(string))
    } catch {
      case e: DecoderException => Left(e)
    }

}
