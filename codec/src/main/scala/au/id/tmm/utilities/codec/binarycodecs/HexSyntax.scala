package au.id.tmm.utilities.codec.binarycodecs

import au.id.tmm.utilities.codec.ScalaVersionDependentBytesRepresentation.ByteArray
import org.apache.commons.codec.DecoderException
import org.apache.commons.codec.binary.Hex

trait HexSyntax {

  def asHexString(bytes: Array[Byte]): String    = Hex.encodeHexString(bytes)
  def asHexString(bytes: ByteArray): String      = asHexString(ByteArray.unwrapUnsafe(bytes))
  def asHexString(bytes: Iterable[Byte]): String = asHexString(bytes.toArray)

  private def decodeToBytes(string: String): Array[Byte] = Hex.decodeHex(string)
  def parseHexOrThrow(string: String): ByteArray         = ByteArray.wrapUnsafe(decodeToBytes(string))
  def parseHex(string: String): Either[DecoderException, ByteArray] =
    try Right(parseHexOrThrow(string))
    catch {
      case e: DecoderException => Left(e)
    }

  implicit class HexStringContext(private val stringContext: StringContext) {
    def hex(subs: Any*): ByteArray = parseHexOrThrow(stringContext.s(subs: _*))
  }

  implicit class HexStringOps(private val s: String) {
    def parseHex: Either[DecoderException, ByteArray] = HexSyntax.this.parseHex(s)
    def parseHexUnsafe: ByteArray                     = parseHexOrThrow(s)
  }

  implicit class HexByteArrayOps(private val bytes: ByteArray) {
    def asHexString: String = HexSyntax.this.asHexString(bytes)
  }

  implicit class HexIterableOps(private val bytes: Iterable[Byte]) {
    def asHexString: String = HexSyntax.this.asHexString(bytes)
  }

  implicit class HexPrimitiveByteArrayOps(private val bytes: Array[Byte]) {
    def asHexString: String = HexSyntax.this.asHexString(bytes)
  }

}
