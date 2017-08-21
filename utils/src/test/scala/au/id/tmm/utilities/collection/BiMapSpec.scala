package au.id.tmm.utilities.collection

import au.id.tmm.utilities.testing.ImprovedFlatSpec

class BiMapSpec extends ImprovedFlatSpec {

  private val sut = BiMap[String, Int](
    "apple" -> 5,
    "pear" -> 4,
    "carrot" -> 6,
  )

  "a bimap" should "support lookup" in {
    assert(sut("apple") === 5)
  }

  it should "support iteration in insertion order" in {
    val actualOrder = sut.toVector

    val expectedOrder = Vector(
      "apple" -> 5,
      "pear" -> 4,
      "carrot" -> 6,
    )

    assert(expectedOrder === actualOrder)
  }

  it should "support inversion" in {
    val actualInverse = sut.inverse

    val expectedInverse = BiMap(
      5 -> "apple",
      4 -> "pear",
      6 -> "carrot",
    )

    assert(expectedInverse === actualInverse)
  }

  it should "be equal to a bimap with the same keys and values" in {
    val left = BiMap[String, Int](
      "apple" -> 5,
      "pear" -> 4,
      "carrot" -> 6,
    )

    val right = BiMap[String, Int](
      "apple" -> 5,
      "pear" -> 4,
      "carrot" -> 6,
    )

    assert(left === right)
  }

  it should "indicate when its partial function is defined for a key" in {
    assert(sut.isDefinedAt("carrot"))
  }

  it should "indicate when its partial function is not defined for a key" in {
    assert(!sut.isDefinedAt("banana"))
  }

  it should "support adding an element" in {
    val actual = sut + ("banana" -> 7)

    val expected = BiMap(
      "apple" -> 5,
      "pear" -> 4,
      "carrot" -> 6,
      "banana" -> 7,
    )

    assert(expected === actual)
  }

  it should "support removing an element" in {
    val actual = sut - "pear"

    val expected = BiMap(
      "apple" -> 5,
      "carrot" -> 6,
    )

    assert(expected === actual)
  }

  it should "provide an empty instance" in {
    assert(sut.empty eq BiMap.empty)
  }

  it should "provide a builder" in {
    val builder = BiMap.newBuilder()

    assert(builder.result() === BiMap.empty)
  }

  it should "provide a CanBuildFrom" in {
    val canBuildFrom = BiMap.canBuildFrom

    assert(canBuildFrom.apply().result() === BiMap.empty)
  }

  it should "reject duplicate keys" in {
    intercept[IllegalArgumentException] {
      BiMap(
        "apple" -> 1,
        "apple" -> 2,
      )
    }
  }

  it should "reject duplicate values" in {
    intercept[IllegalArgumentException] {
      BiMap(
        "apple" -> 1,
        "pear" -> 1,
      )
    }
  }

  "a bimap builder" can "be cleared" in {
    val builder = BiMap.newBuilder[String, Int]()

    builder.+=(("hello", 5))

    builder.clear()

    assert(builder.result() === BiMap.empty)
  }

}
