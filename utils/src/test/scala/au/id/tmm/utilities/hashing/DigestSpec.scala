package au.id.tmm.utilities.hashing

import au.id.tmm.utilities.encoding.EncodingUtils.StringConversions
import au.id.tmm.utilities.testing.ImprovedFlatSpec

class DigestSpec extends ImprovedFlatSpec {

  val testDigestHex = "e37f03dc379fe6baa26298120b0f0a32b8bd366e510ce08c9fa350bf97e99e4f"
  val testDigestBytes = testDigestHex.fromHex

  val testDigest = Digest(testDigestHex)

  "construction from an array" should "return a digest equal to one from an equivalent hex string" in {
    assert(Digest(testDigestHex) === Digest(testDigestBytes.array))
  }

  "construction from a wrapped array" should "return a digest equal to one from an equivalent hex string" in {
    assert(Digest(testDigestHex) === Digest(testDigestBytes))
  }

  behaviour of "asArray"

  it should "return an array with the correct values" in {
    assert(wrapByteArray(testDigest.asArray()) === testDigestBytes)
  }

  it should "return a new array on each call" in {
    val firstArray = testDigest.asArray()
    val secondArray = testDigest.asArray()

    assert(firstArray ne secondArray)
  }

  behaviour of "asWrappedArray"

  it should "return a wrapped array with the correct values" in {
    assert(testDigest.asWrappedArray === testDigestBytes)
  }

  it should "return a new wrapped array on each call" in {
    val firstArray = testDigest.asWrappedArray()
    val secondArray = testDigest.asWrappedArray()

    assert(firstArray ne secondArray)
  }

  "asHexString" should "return the hex representation of the digest" in {
    assert(testDigest.asHexString === "e37f03dc379fe6baa26298120b0f0a32b8bd366e510ce08c9fa350bf97e99e4f")
  }

  "toString" should "return the hex representation of the digest and look like a case class toString" in {
    assert(testDigest.toString === s"Digest(${testDigest.asHexString})")
  }

  "length" should "return the number of bytes" in {
    assert(testDigest.length === 32)
  }

  "hashCode" should "return a hashcode" in {
    assert(testDigest.hashCode === -950740302)
  }
}
