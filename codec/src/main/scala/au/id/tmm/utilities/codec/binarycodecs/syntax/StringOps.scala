package au.id.tmm.utilities.codec.binarycodecs.syntax

import au.id.tmm.utilities.codec.binarycodecs._
import org.apache.commons.codec.DecoderException

import scala.collection.immutable.ArraySeq

final class StringOps private(string: String) {

  def parseBase32: Either[DecoderException, ArraySeq.ofByte] = Base32.parseBase32(string)

  def parseBase64: Either[DecoderException, ArraySeq.ofByte] = Base64.parseBase64(string)

  def parseBinary: Either[DecoderException, ArraySeq.ofByte] = Binary.parseBinary(string)

  def parseHex: Either[DecoderException, ArraySeq.ofByte] = Hex.parseHex(string)

  def parseBase32OrThrow: ArraySeq.ofByte = Base32.parseBase32OrThrow(string)

  def parseBase64OrThrow: ArraySeq.ofByte = Base64.parseBase64OrThrow(string)

  def parseBinaryOrThrow: ArraySeq.ofByte = Binary.parseBinaryOrThrow(string)

  def parseHexOrThrow: ArraySeq.ofByte = Hex.parseHexOrThrow(string)

}

object StringOps {

  trait Syntax {
    implicit def toTmmUtilsCodecStringOps(string: String): StringOps = new StringOps(string)
  }

}
