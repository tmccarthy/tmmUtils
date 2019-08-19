package au.id.tmm.utilities.codec.binarycodecs

import au.id.tmm.utilities.codec.ScalaVersionDependentBytesRepresentation._
import org.apache.commons.codec.DecoderException
import org.scalatest.FlatSpec

class HexSyntaxSpec extends FlatSpec {

  private val validHexString = "ADE1A1DE"
  private val bytes          = ByteArray(0xAD.toByte, 0xE1.toByte, 0xA1.toByte, 0xDE.toByte)

  private val invalidHexString = "ZZZZ"

  "the hex string context" should "convert some hex to a byte array" in {
    assert(hex"$validHexString" === bytes)
  }

  it should "throw if invalid hex is provided" in {
    val exception = intercept[DecoderException](hex"$invalidHexString")

    assert(exception.getClass === classOf[DecoderException])
  }

  "the hex string ops" should "parse a byte array from a hex string" in {
    assert(validHexString.parseHex === Right(bytes))
  }

  it should "fail to parse an invalid hex string" in {
    assert(invalidHexString.parseHex.left.map(_.getClass) === Left(classOf[DecoderException]))
  }

  it should "parse a byte array from a hex string using parseOrThrow" in {
    assert(validHexString.parseHex === Right(bytes))
  }

  it should "throw if parsing an invalid hex string with parseOrThrow" in {
    val exception = intercept[DecoderException](invalidHexString.parseHexUnsafe)

    assert(exception.getClass === classOf[DecoderException])
  }

  "the hex iterable ops" should "encode a ByteArray to hex" in {
    assert(bytes.asHexString === validHexString.toLowerCase)
  }

  it should "encode a list of bytes to hex" in {
    assert(bytes.toList.asHexString === validHexString.toLowerCase)
  }

  "the hex array ops" should "encode an array to hex" in {
    assert(ByteArray.unwrapUnsafe(bytes).asHexString === validHexString.toLowerCase)
  }

}
