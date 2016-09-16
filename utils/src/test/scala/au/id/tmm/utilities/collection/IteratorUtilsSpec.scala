package au.id.tmm.utilities.collection

import au.id.tmm.utilities.collection.IteratorUtils.ImprovedIterator
import au.id.tmm.utilities.testing.ImprovedFlatSpec

class IteratorUtilsSpec extends ImprovedFlatSpec {

  val data = List("the", "quick", "brown", "fox")
  val iterator = data.iterator

  "readAtMost" should "read the first n elements of the iterator" in {
    assert(List("the", "quick") === iterator.readAtMost(2))
  }

  it should "leave the underlying iterator iterating through the subsequent elements" in {
    iterator.readAtMost(2)

    assert(List("brown", "fox") === iterator.toList)
  }

  it should "read only as many elements remain in the iterator" in {
    assert(data === iterator.readAtMost(5))
  }

  "readUntil" should "read until encountering an element matching the condition" in {
    assert(Vector("the", "quick", "brown") === iterator.readUntil(_.startsWith("b")))
  }

  it should "leave the underlying iterator iterating through the subsequent elements" in {
    iterator.readUntil(_.startsWith("b"))

    assert(List("fox") === iterator.toList)
  }

  it should "read only as many elements remain in the iterator" in {
    assert(data === iterator.readUntil(_ => false))
  }

}
