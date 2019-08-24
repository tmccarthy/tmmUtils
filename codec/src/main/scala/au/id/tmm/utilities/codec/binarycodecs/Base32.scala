package au.id.tmm.utilities.codec.binarycodecs

import au.id.tmm.utilities.codec.ScalaVersionDependentBytesRepresentation.ByteArray
import org.apache.commons.codec.DecoderException
import org.apache.commons.codec.binary.{Base32 => CommonsBase32}

object Base32 {

  private val base32Codec = new CommonsBase32(Int.MaxValue, Array.empty[Byte])

  def asBase32String(bytes: Array[Byte]): String    = base32Codec.encodeToString(bytes)
  def asBase32String(bytes: ByteArray): String      = asBase32String(ByteArray.unwrapUnsafe(bytes))
  def asBase32String(bytes: Iterable[Byte]): String = asBase32String(bytes.toArray)

  private def decodeToBytes(string: String): Array[Byte] =
    if (base32Codec.isInAlphabet(string))
      base32Codec.decode(string)
    else
      throw new DecoderException("Invalid base32")

  def parseBase32OrThrow(string: String): ByteArray = ByteArray.wrapUnsafe(decodeToBytes(string))

  def parseBase32(string: String): Either[DecoderException, ByteArray] =
    try Right(parseBase32OrThrow(string))
    catch {
      case e: DecoderException => Left(e)
    }

  trait Syntax {

    implicit class Base32StringContext(private val stringContext: StringContext) {
      def base32(subs: Any*): ByteArray = parseBase32OrThrow(stringContext.s(subs: _*))
    }

    implicit class Base32StringOps(private val s: String) {
      def parseBase32: Either[DecoderException, ByteArray] = Base32.parseBase32(s)

      def parseBase32Unsafe: ByteArray = parseBase32OrThrow(s)
    }

    implicit class Base32ByteArrayOps(private val bytes: ByteArray) {
      def asBase32String: String = Base32.asBase32String(bytes)
    }

    implicit class Base32IterableOps(private val bytes: Iterable[Byte]) {
      def asBase32String: String = Base32.asBase32String(bytes)
    }

    implicit class Base32PrimitiveByteArrayOps(private val bytes: Array[Byte]) {
      def asBase32String: String = Base32.asBase32String(bytes)
    }

  }

}
