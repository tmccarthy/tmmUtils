package au.id.tmm.utilities.codec.binarycodecs

import au.id.tmm.utilities.codec.ScalaVersionDependentBytesRepresentation.ByteArray
import org.apache.commons.codec.DecoderException
import org.apache.commons.codec.binary.Base64

trait Base64Syntax {

  def asBase64String(bytes: Array[Byte]): String    = Base64.encodeBase64String(bytes)
  def asBase64String(bytes: ByteArray): String      = asBase64String(ByteArray.unwrapUnsafe(bytes))
  def asBase64String(bytes: Iterable[Byte]): String = asBase64String(bytes.toArray)

  private def decodeToBytes(string: String): Array[Byte] =
    if (Base64.isBase64(string)) Base64.decodeBase64(string) else throw new DecoderException("Invalid base64")
  def parseBase64OrThrow(string: String): ByteArray = ByteArray.wrapUnsafe(decodeToBytes(string))
  def parseBase64(string: String): Either[DecoderException, ByteArray] =
    try Right(parseBase64OrThrow(string))
    catch {
      case e: DecoderException => Left(e)
    }

  implicit class Base64StringContext(private val stringContext: StringContext) {
    def base64(subs: Any*): ByteArray = parseBase64OrThrow(stringContext.s(subs: _*))
  }

  implicit class Base64StringOps(private val s: String) {
    def parseBase64: Either[DecoderException, ByteArray] = Base64Syntax.this.parseBase64(s)
    def parseBase64Unsafe: ByteArray                     = parseBase64OrThrow(s)
  }

  implicit class Base64ByteArrayOps(private val bytes: ByteArray) {
    def asBase64String: String = Base64Syntax.this.asBase64String(bytes)
  }

  implicit class Base64IterableOps(private val bytes: Iterable[Byte]) {
    def asBase64String: String = Base64Syntax.this.asBase64String(bytes)
  }

  implicit class Base64PrimitiveByteArrayOps(private val bytes: Array[Byte]) {
    def asBase64String: String = Base64Syntax.this.asBase64String(bytes)
  }

}
