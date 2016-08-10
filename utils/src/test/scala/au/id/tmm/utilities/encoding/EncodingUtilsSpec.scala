package au.id.tmm.utilities.encoding

import au.id.tmm.utilities.encoding.EncodingUtils.StringConversions
import au.id.tmm.utilities.testing.ImprovedFlatSpec

class EncodingUtilsSpec extends ImprovedFlatSpec {

  behaviour of "the from Hex method"

  it should "correctly convert from hex" in {
    val expected: Array[Byte] = Array(-29, 127, 3).map(_.toByte)

    assert("e37f03".fromHex === wrapByteArray(expected))
  }

  it should "throw if the string isn't hex" in {
    intercept[IllegalArgumentException] {
      "zzz".fromHex
    }
  }

}
