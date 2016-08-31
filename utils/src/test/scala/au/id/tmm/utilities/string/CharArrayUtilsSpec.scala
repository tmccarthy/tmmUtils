package au.id.tmm.utilities.string

import au.id.tmm.utilities.string.CharArrayUtils.ImprovedCharArray
import au.id.tmm.utilities.testing.ImprovedFlatSpec

class CharArrayUtilsSpec extends ImprovedFlatSpec {

  "zeroOut" should "replace the contents of the array with null characters" in {
    val charArray = "helloWorld".toCharArray

    charArray.zeroOut()

    assert(charArray.toList === Vector.fill(charArray.length)('\0'))
  }

}
