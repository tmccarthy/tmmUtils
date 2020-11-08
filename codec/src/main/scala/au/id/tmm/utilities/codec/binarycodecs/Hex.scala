package au.id.tmm.utilities.codec.binarycodecs

import org.apache.commons.codec.DecoderException
import org.apache.commons.codec.binary.{Hex => CommonsHex}

import scala.collection.immutable.ArraySeq

object Hex {

  def asHexString(bytes: Array[Byte]): String    = CommonsHex.encodeHexString(bytes)
  def asHexString(bytes: ArraySeq[Byte]): String = asHexString(bytes.unsafeArray.asInstanceOf[Array[Byte]])
  def asHexString(bytes: Iterable[Byte]): String = asHexString(bytes.toArray)

  private def decodeToBytes(string: String): Array[Byte] = CommonsHex.decodeHex(string)
  def parseHexOrThrow(string: String): ArraySeq.ofByte   = new ArraySeq.ofByte(decodeToBytes(string))
  def parseHex(string: String): Either[DecoderException, ArraySeq.ofByte] =
    try Right(parseHexOrThrow(string))
    catch {
      case e: DecoderException => Left(e)
    }

  trait Syntax {

    implicit class HexStringContext(private val stringContext: StringContext) {
      def hex(subs: Any*): ArraySeq.ofByte = parseHexOrThrow(stringContext.s(subs: _*))
    }

    implicit class HexStringOps(private val s: String) {
      def parseHex: Either[DecoderException, ArraySeq.ofByte] = Hex.this.parseHex(s)

      def parseHexUnsafe: ArraySeq.ofByte = parseHexOrThrow(s)
    }

    implicit class HexByteArrayOps(private val bytes: ArraySeq[Byte]) {
      def asHexString: String = Hex.this.asHexString(bytes)
    }

    implicit class HexIterableOps(private val bytes: Iterable[Byte]) {
      def asHexString: String = Hex.this.asHexString(bytes)
    }

    implicit class HexPrimitiveByteArrayOps(private val bytes: Array[Byte]) {
      def asHexString: String = Hex.this.asHexString(bytes)
    }

  }

}
