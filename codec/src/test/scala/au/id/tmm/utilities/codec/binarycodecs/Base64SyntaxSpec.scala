package au.id.tmm.utilities.codec.binarycodecs

import org.apache.commons.codec.DecoderException
import org.scalatest.FlatSpec

import scala.collection.immutable.ArraySeq

class Base64SyntaxSpec extends FlatSpec {

  private val validBase64String = "reGh3g=="
  private val bytes             = ArraySeq[Byte](0xAD.toByte, 0xE1.toByte, 0xA1.toByte, 0xDE.toByte)

  private val invalidBase64String = "ابتث"

  "the base64 string context" should "convert some base64 to a byte array" in {
    assert(base64"$validBase64String" === bytes)
  }

  it should "throw if invalid base64 is provided" in {
    val exception = intercept[DecoderException](base64"$invalidBase64String")

    assert(exception.getClass === classOf[DecoderException])
  }

  "the base64 string ops" should "parse a byte array from a base64 string" in {
    assert(validBase64String.parseBase64 === Right(bytes))
  }

  it should "fail to parse an invalid base64 string" in {
    assert(invalidBase64String.parseBase64.left.map(_.getClass) === Left(classOf[DecoderException]))
  }

  it should "parse a byte array from a base64 string using parseOrThrow" in {
    assert(validBase64String.parseBase64 === Right(bytes))
  }

  it should "throw if parsing an invalid base64 string with parseOrThrow" in {
    val exception = intercept[DecoderException](invalidBase64String.parseBase64Unsafe)

    assert(exception.getClass === classOf[DecoderException])
  }

  "the base64 iterable ops" should "encode a ByteArray to base64" in {
    assert(bytes.asBase64String === validBase64String)
  }

  it should "encode a list of bytes to base64" in {
    assert(bytes.toList.asBase64String === validBase64String)
  }

  "the base64 array ops" should "encode an array to base64" in {
    assert(bytes.unsafeArray.asInstanceOf[Array[Byte]].asBase64String === validBase64String)
  }

}
