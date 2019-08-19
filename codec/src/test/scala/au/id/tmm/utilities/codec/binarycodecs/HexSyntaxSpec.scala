package au.id.tmm.utilities.codec.binarycodecs

import au.id.tmm.utilities.codec.ScalaVersionDependentBytesRepresentation._
import org.apache.commons.codec.DecoderException
import org.scalatest.FlatSpec

class HexSyntaxSpec extends FlatSpec {

  "the hex string context" should "convert some hex to a byte array" in {
    assert(hex"ADE1A1DE" === ByteArray(0xAD.toByte, 0xE1.toByte, 0xA1.toByte, 0xDE.toByte))
  }

  it should "throw if invalid hex is provided" in {
    val exception = intercept[DecoderException](hex"ZZZZ")

    assert(exception.getClass === classOf[DecoderException])
  }

  "the hex string ops" should "parse a byte array from a hex string" in {
    assert("ADE1A1DE".parseHex === Right(ByteArray(0xAD.toByte, 0xE1.toByte, 0xA1.toByte, 0xDE.toByte)))
  }

  it should "fail to parse an invalid hex string" in {
    assert("ZZZZ".parseHex.left.map(_.getClass) === Left(classOf[DecoderException]))
  }

  it should "parse a byte array from a hex string using parseOrThrow" in {
    assert("ADE1A1DE".parseHex === Right(ByteArray(0xAD.toByte, 0xE1.toByte, 0xA1.toByte, 0xDE.toByte)))
  }

  it should "throw if parsing an invalid hex string with parseOrThrow" in {
    val exception = intercept[DecoderException]("ZZZZ".parseHexUnsafe)

    assert(exception.getClass === classOf[DecoderException])
  }

  "the hex iterable ops" should "encode a ByteArray to hex" in {
    assert(ByteArray(0xAD.toByte, 0xE1.toByte, 0xA1.toByte, 0xDE.toByte).asHexString === "ade1a1de")
  }

  it should "encode a list of bytes to hex" in {
    assert(List(0xAD.toByte, 0xE1.toByte, 0xA1.toByte, 0xDE.toByte).asHexString === "ade1a1de")
  }

  "the hex array ops" should "encode an array to hex" in {
    assert(Array(0xAD.toByte, 0xE1.toByte, 0xA1.toByte, 0xDE.toByte).asHexString === "ade1a1de")
  }

}
