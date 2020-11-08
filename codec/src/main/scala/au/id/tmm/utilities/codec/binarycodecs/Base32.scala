package au.id.tmm.utilities.codec.binarycodecs

import org.apache.commons.codec.DecoderException
import org.apache.commons.codec.binary.{Base32 => CommonsBase32}

import scala.collection.immutable.ArraySeq

object Base32 {

  private val base32Codec = new CommonsBase32(Int.MaxValue, Array.empty[Byte])

  def asBase32String[B: BytesLike](bytes: B): String = base32Codec.encodeToString(BytesLike[B].unsafeBytes(bytes))

  private def decodeToBytes(string: String): Array[Byte] =
    if (base32Codec.isInAlphabet(string)) {
      base32Codec.decode(string)
    } else {
      throw new DecoderException("Invalid base32")
    }

  def parseBase32OrThrow(string: String): ArraySeq.ofByte = new ArraySeq.ofByte(decodeToBytes(string))

  def parseBase32(string: String): Either[DecoderException, ArraySeq.ofByte] =
    try {
      Right(parseBase32OrThrow(string))
    } catch {
      case e: DecoderException => Left(e)
    }

}
