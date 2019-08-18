package au.id.tmm.utilities.encoding

import java.util.Base64

import javax.xml.bind.DatatypeConverter

import scala.collection.mutable

object EncodingUtils {
  implicit class StringConversions(string: String) {

    /**
      * Parses this string as a hex string, returning the decoded bytes.
      */
    def fromHex: mutable.WrappedArray[Byte] = wrapByteArray(DatatypeConverter.parseHexBinary(string))
  }

  implicit class ArrayConversions(bytes: Array[Byte]) {

    /**
      * Encodes this array of bytes as a hex string.
      */
    def toHex: String = {
      val hexChars = wrapByteArray(bytes).flatMap(hexCharsOf).array

      new String(hexChars)
    }

    /**
      * Encodes this array of bytes in base 64.
      */
    def toBase64: String = Base64.getEncoder.encodeToString(bytes)
  }

  // http://stackoverflow.com/a/21178195/1951001
  private def hexCharsOf(byte: Byte): Array[Char] = Array(
    Character.forDigit((byte >> 4) & 0xF, 16),
    Character.forDigit(byte & 0xF, 16)
  )
}
