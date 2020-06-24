package au.id.tmm.utilities.codec.binarycodecs

import org.apache.commons.codec.DecoderException
import org.scalatest.flatspec.AnyFlatSpec

import scala.collection.immutable.ArraySeq

class BinarySyntaxSpec extends AnyFlatSpec {

  private val validBinaryString = "11011110101000011110000110101101"
  private val bytes             = ArraySeq[Byte](0xad.toByte, 0xe1.toByte, 0xa1.toByte, 0xde.toByte)

  private val invalidBinaryString = "ZZZZ"

  "the binary string context" should "convert some binary to a byte array" in {
    assert(binary"$validBinaryString" === bytes)
  }

  it should "throw if invalid binary is provided" in {
    val exception = intercept[DecoderException](binary"$invalidBinaryString")

    assert(exception.getClass === classOf[DecoderException])
  }

  "the binary string ops" should "parse a byte array from a binary string" in {
    assert(validBinaryString.parseBinary === Right(bytes))
  }

  it should "fail to parse an invalid binary string" in {
    assert(invalidBinaryString.parseBinary.left.map(_.getClass) === Left(classOf[DecoderException]))
  }

  it should "parse a byte array from a binary string using parseOrThrow" in {
    assert(validBinaryString.parseBinary === Right(bytes))
  }

  it should "throw if parsing an invalid binary string with parseOrThrow" in {
    val exception = intercept[DecoderException](invalidBinaryString.parseBinaryUnsafe)

    assert(exception.getClass === classOf[DecoderException])
  }

  "the binary iterable ops" should "encode a ByteArray to binary" in {
    assert(bytes.asBinaryString === validBinaryString.toLowerCase)
  }

  it should "encode a list of bytes to binary" in {
    assert(bytes.toList.asBinaryString === validBinaryString.toLowerCase)
  }

  "the binary array ops" should "encode an array to binary" in {
    assert(bytes.unsafeArray.asInstanceOf[Array[Byte]].asBinaryString === validBinaryString.toLowerCase)
  }

}
