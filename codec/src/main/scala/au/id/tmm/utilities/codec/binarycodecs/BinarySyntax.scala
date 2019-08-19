package au.id.tmm.utilities.codec.binarycodecs

import au.id.tmm.utilities.codec.ScalaVersionDependentBytesRepresentation.ByteArray
import org.apache.commons.codec.DecoderException
import org.apache.commons.codec.binary.BinaryCodec

trait BinarySyntax {

  private val validBinaryChars = Set('0', '1')

  def asBinaryString(bytes: Array[Byte]): String    = new String(BinaryCodec.toAsciiChars(bytes))
  def asBinaryString(bytes: ByteArray): String      = asBinaryString(ByteArray.unwrapUnsafe(bytes))
  def asBinaryString(bytes: Iterable[Byte]): String = asBinaryString(bytes.toArray)

  private def invalidBinaryChar(char: Char): Boolean = !validBinaryChars.contains(char)

  private def decodeToBytes(string: String): Array[Byte] =
    if (string.exists(invalidBinaryChar))
      throw new DecoderException("Contains invalid binary chars")
    else
      BinaryCodec.fromAscii(string.toCharArray)

  def parseBinaryOrThrow(string: String): ByteArray = ByteArray.wrapUnsafe(decodeToBytes(string))

  def parseBinary(string: String): Either[DecoderException, ByteArray] =
    try Right(parseBinaryOrThrow(string))
    catch {
      case e: DecoderException => Left(e)
    }

  implicit class BinaryStringContext(private val stringContext: StringContext) {
    def binary(subs: Any*): ByteArray = parseBinaryOrThrow(stringContext.s(subs: _*))
  }

  implicit class BinaryStringOps(private val s: String) {
    def parseBinary: Either[DecoderException, ByteArray] = BinarySyntax.this.parseBinary(s)
    def parseBinaryUnsafe: ByteArray                     = parseBinaryOrThrow(s)
  }

  implicit class BinaryByteArrayOps(private val bytes: ByteArray) {
    def asBinaryString: String = BinarySyntax.this.asBinaryString(bytes)
  }

  implicit class BinaryIterableOps(private val bytes: Iterable[Byte]) {
    def asBinaryString: String = BinarySyntax.this.asBinaryString(bytes)
  }

  implicit class BinaryPrimitiveByteArrayOps(private val bytes: Array[Byte]) {
    def asBinaryString: String = BinarySyntax.this.asBinaryString(bytes)
  }

}
