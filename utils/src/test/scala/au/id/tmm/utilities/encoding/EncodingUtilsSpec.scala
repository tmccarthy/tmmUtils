package au.id.tmm.utilities.encoding

import au.id.tmm.utilities.encoding.EncodingUtils.{ArrayConversions, StringConversions}
import au.id.tmm.utilities.testing.ImprovedFlatSpec

class EncodingUtilsSpec extends ImprovedFlatSpec {

  behaviour of "the from hex method"

  it should "correctly convert from hex" in {
    val expected: Array[Byte] = Array(-29, 127, 3).map(_.toByte)

    assert("e37f03".fromHex === wrapByteArray(expected))
  }

  it should "throw if the string isn't hex" in {
    intercept[IllegalArgumentException] {
      "zzz".fromHex
    }
  }

  behaviour of "the to hex method"

  it should "correctly convert to hex" in {
    val bytes: Array[Byte] = Array(-29, 127, 3).map(_.toByte)

    assert(bytes.toHex === "e37f03")
  }

  behaviour of "the toBase64 method"

  it should "correctly convert to base64" in {
    val bytes: Array[Byte] = Array(-29, 127, 3).map(_.toByte)

    assert(bytes.toBase64 === "438D")
  }
}
