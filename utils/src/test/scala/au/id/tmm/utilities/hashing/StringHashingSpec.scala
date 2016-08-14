package au.id.tmm.utilities.hashing

import au.id.tmm.utilities.hashing.StringHashing.StringHashingImplicits
import au.id.tmm.utilities.testing.ImprovedFlatSpec

class StringHashingSpec extends ImprovedFlatSpec {

  behaviour of "the sha256 string hash"

  it should "correctly hash a string" in {
    val expectedDigest = Digest("04713509cf7c1f77719ebcc831a6b9bc6214bc6d28e2e61a5ddd00384d7cae5d")
    val actualDigest = "the quick brown fox jumped over the lazy dogs".sha256checksum

    assert (expectedDigest === actualDigest)
  }
}
