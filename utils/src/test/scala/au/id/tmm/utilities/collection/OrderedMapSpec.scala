package au.id.tmm.utilities.collection

import au.id.tmm.utilities.testing.ImprovedFlatSpec

class OrderedMapSpec extends ImprovedFlatSpec {

  private val testingMap = OrderedMap(
    "the" -> 3,
    "quick" -> 5,
    "fox" -> 3,
    "jumped" -> 6,
    "over" -> 4,
    "lazy" -> 4,
    "dogs" -> 4
  )

  "the iteration order" should "be the insertion order" in {

    val actualIterationOrder = testingMap.toList

    val expectedIterationOrder = List(
      "the" -> 3,
      "quick" -> 5,
      "fox" -> 3,
      "jumped" -> 6,
      "over" -> 4,
      "lazy" -> 4,
      "dogs" -> 4
    )

    assert(expectedIterationOrder === actualIterationOrder)
  }

  "lookup" should "return the value for that key" in {
    assert(testingMap("over") === 4)
  }

  "removal of an element" should "remove that element from the map" in {
    val actualMapAfterRemoval = testingMap - "lazy"

    val expectedMapAfterRemoval = OrderedMap(
      "the" -> 3,
      "quick" -> 5,
      "fox" -> 3,
      "jumped" -> 6,
      "over" -> 4,
      "dogs" -> 4
    )

    assert(expectedMapAfterRemoval === actualMapAfterRemoval)
  }

  "the empty map" should "be empty" in {
    val empty = testingMap.empty

    assert(empty.size === 0)
  }
}
