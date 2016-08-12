package au.id.tmm.utilities.encoding

import javax.xml.bind.DatatypeConverter

import scala.collection.mutable

object EncodingUtils {
  implicit class StringConversions(string: String) {
    def fromHex: mutable.WrappedArray[Byte] = wrapByteArray(DatatypeConverter.parseHexBinary(string))
  }

  implicit class ArrayConversions(bytes: Array[Byte]) {
    def toHex: String = {
      val hexChars = wrapByteArray(bytes).flatMap(hexCharsOf).array

      new String(hexChars)
    }
  }

  // http://stackoverflow.com/a/21178195/1951001
  private def hexCharsOf(byte: Byte): Array[Char] = Array(
    Character.forDigit((byte >> 4) & 0xF, 16),
    Character.forDigit(byte & 0xF, 16)
  )
}
