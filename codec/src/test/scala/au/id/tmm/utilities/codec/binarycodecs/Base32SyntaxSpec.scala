package au.id.tmm.utilities.codec.binarycodecs

import org.apache.commons.codec.DecoderException
import org.scalatest.flatspec.AnyFlatSpec

import scala.collection.immutable.ArraySeq

class Base32SyntaxSpec extends AnyFlatSpec {

  private val validBase32String = "VXQ2DXQ="
  private val bytes             = ArraySeq[Byte](0xAD.toByte, 0xE1.toByte, 0xA1.toByte, 0xDE.toByte)

  private val invalidBase32String = "ابتث"

  "the base32 string context" should "convert some base32 to a byte array" in {
    assert(base32"$validBase32String" === bytes)
  }

  it should "throw if invalid base32 is provided" in {
    val exception = intercept[DecoderException](base32"$invalidBase32String")

    assert(exception.getClass === classOf[DecoderException])
  }

  "the base32 string ops" should "parse a byte array from a base32 string" in {
    assert(validBase32String.parseBase32 === Right(bytes))
  }

  it should "fail to parse an invalid base32 string" in {
    assert(invalidBase32String.parseBase32.left.map(_.getClass) === Left(classOf[DecoderException]))
  }

  it should "parse a byte array from a base32 string using parseOrThrow" in {
    assert(validBase32String.parseBase32 === Right(bytes))
  }

  it should "throw if parsing an invalid base32 string with parseOrThrow" in {
    val exception = intercept[DecoderException](invalidBase32String.parseBase32Unsafe)

    assert(exception.getClass === classOf[DecoderException])
  }

  "the base32 iterable ops" should "encode a ByteArray to base32" in {
    assert(bytes.asBase32String === validBase32String)
  }

  it should "encode a list of bytes to base32" in {
    assert(bytes.toList.asBase32String === validBase32String)
  }

  "the base32 array ops" should "encode an array to base32" in {
    assert(bytes.unsafeArray.asInstanceOf[Array[Byte]].asBase32String === validBase32String)
  }

}
