package au.id.tmm.utilities.codec.binarycodecs.syntax

import au.id.tmm.utilities.codec.binarycodecs._

import scala.collection.immutable.ArraySeq

final class BinaryCodecsStringContext private (stringContext: StringContext) {

  def base32(subs: Any*): ArraySeq.ofByte = Base32.parseBase32OrThrow(stringContext.s(subs: _*))

  def base64(subs: Any*): ArraySeq.ofByte = Base64.parseBase64OrThrow(stringContext.s(subs: _*))

  def binary(subs: Any*): ArraySeq.ofByte = Binary.parseBinaryOrThrow(stringContext.s(subs: _*))

  def hex(subs: Any*): ArraySeq.ofByte = Hex.parseHexOrThrow(stringContext.s(subs: _*))

}

object BinaryCodecsStringContext {

  trait Syntax {
    implicit def toTmmUtilsCodecBinaryCodecsStringContext(stringContext: StringContext): BinaryCodecsStringContext =
      new BinaryCodecsStringContext(stringContext)
  }

}
