package au.id.tmm.utilities.encoding

import javax.xml.bind.DatatypeConverter

import scala.collection.mutable

object EncodingUtils {
  implicit class StringConversions(string: String) {
    def fromHex: mutable.WrappedArray[Byte] = wrapByteArray(DatatypeConverter.parseHexBinary(string))
  }
}
