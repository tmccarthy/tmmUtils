package au.id.tmm.utilities.collection

import au.id.tmm.utilities.testing.ImprovedFlatSpec

class OrderedSetSpec extends ImprovedFlatSpec {

  behaviour of "an Ordered Set"

  it should "retain the order of the earliest element when initialised" in {
    assert(OrderedSet(1, 2, 3, 1).toList === List(1, 2, 3))
  }

  it should "not contain duplicates" in {
    assert(OrderedSet(1, 1, 1).count(_ == 1) === 1)
  }

  it should "retain insertion order" in {
    assert(OrderedSet("the", "quick", "brown", "fox").toList === List("the", "quick", "brown", "fox"))
  }

  it should "correctly implement contains" in {
    val sut = OrderedSet(1, 2, 3)

    assert(sut contains 1)
    assert(!(sut contains 4))
  }

  it should "correctly implement size" in {
    assert(OrderedSet(1, 2, 3).size === 3)
  }

  it should "correctly implement size for the empty set" in {
    assert(OrderedSet().size === 0)
  }

  it should "add an element correctly" in {
    assert((OrderedSet(1, 2) + 3) === OrderedSet(1, 2, 3))
  }

  it should "return the same set when adding an element the set contains" in {
    val originalSet = OrderedSet(1, 2)
    assert((originalSet + 1) eq originalSet)
  }

  it should "remove an element correctly" in {
    assert((OrderedSet(1, 2, 3) - 3) === OrderedSet(1, 2))
  }

  it should "return the same set when removing an element not in the set" in {
    val originalSet = OrderedSet(1, 2)
    assert((originalSet - 3) eq originalSet)
  }

  behaviour of "the empty Ordered Set"

  it should "be a singleton" in {
    assert(OrderedSet() eq OrderedSet())
  }

  it should "be returned by the empty instance method" in {
    assert(OrderedSet() eq OrderedSet().empty)
  }

  behaviour of "the ordered set builder"

  it should "build the empty set" in {
    assert(OrderedSet.newBuilder[Int].result() eq OrderedSet.empty)
  }

  it should "build a set with 2 distinct items" in {
    val builder = OrderedSet.newBuilder[Int]

    builder += 1
    builder += 3

    assert(builder.result() === OrderedSet(1, 3))
  }

  it should "iterate in the order of first insertion" in {
    val builder = OrderedSet.newBuilder[Int]

    builder += 1
    builder += 3
    builder += 1

    assert(builder.result() === OrderedSet(1, 3))
  }

  it can "be cleared" in {
    val builder = OrderedSet.newBuilder[Int]

    builder += 1
    builder += 3
    builder.clear()

    assert(builder.result().isEmpty)
  }

  it can "append multiple elements at once" in {
    val builder = OrderedSet.newBuilder[Int]

    builder ++= Vector(1, 3, 1)

    assert(builder.result() === OrderedSet(1, 3))
  }

  it should "accept a size hint" in {
    val builder = OrderedSet.newBuilder[Int]

    builder.sizeHint(5)

    builder += 1
    builder += 3

    assert(builder.result() === OrderedSet(1, 3))
  }

  behaviour of "the ordered set cbf"

  it should "allow building from a Set" in {
    assert(Set(1, 2, 3, 2).to[OrderedSet] === OrderedSet(1, 2, 3))
  }

  it should "allow building from a List" in {
    assert(List(1, 2, 3, 2).to[OrderedSet] === OrderedSet(1, 2, 3))
  }
}
