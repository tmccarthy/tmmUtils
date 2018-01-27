package au.id.tmm.utilities.collection

import au.id.tmm.utilities.testing.ImprovedFlatSpec

import scala.collection.generic.CanBuildFrom
import scala.collection.mutable

class DupelessSeqSpec extends ImprovedFlatSpec {

  private def toListCbf: CanBuildFrom[DupelessSeq[Int], Int, List[Int]] = new CanBuildFrom[DupelessSeq[Int], Int, List[Int]] {
    override def apply(from: DupelessSeq[Int]): mutable.Builder[Int, List[Int]] = List.newBuilder

    override def apply(): mutable.Builder[Int, List[Int]] = List.newBuilder
  }

  "an DupelessSeq" should "retain the order of the earliest element when initialised" in {
    assert(DupelessSeq(1, 2, 3, 1).toList === List(1, 2, 3))
  }

  it should "not contain duplicates" in {
    assert(DupelessSeq(1, 1, 1).count(_ == 1) === 1)
  }

  it should "retain insertion order" in {
    assert(DupelessSeq("the", "quick", "brown", "fox").toList === List("the", "quick", "brown", "fox"))
  }

  it should "correctly implement contains" in {
    val sut = DupelessSeq(1, 2, 3)

    assert(sut contains 1)
    assert(!(sut contains 4))
  }

  it should "correctly implement size" in {
    assert(DupelessSeq(1, 2, 3).size === 3)
  }

  it should "correctly implement size for the empty seq" in {
    assert(DupelessSeq().size === 0)
  }

  it should "append an element correctly" in {
    assert((DupelessSeq(1, 2) :+ 3) === DupelessSeq(1, 2, 3))
  }

  it should "return the same seq when appending a duplicate element" in {
    val originalSeq = DupelessSeq(1, 2)

    assert((originalSeq :+ 1) eq originalSeq)
  }

  it should "append an element correctly when converting to a list" in {
    assert(DupelessSeq(1, 2).:+(2)(toListCbf) === List(1, 2, 2))
  }

  it should "prepend an element correctly" in {
    assert(DupelessSeq(1, 2).+:(3) === DupelessSeq(3, 1, 2))
  }

  it should "return the same seq when prepending a duplicate element" in {
    val originalSeq = DupelessSeq(1, 2)

    assert(originalSeq.+:(2) eq originalSeq)
  }

  it should "prepend an element correctly when converting to a list" in {
    assert(DupelessSeq(1, 2).+:(2)(toListCbf) === List(2, 1, 2))
  }

  it should "remove an element correctly" in {
    assert((DupelessSeq(1, 2, 3) - 3) === DupelessSeq(1, 2))
  }

  it should "return the same seq when removing an element not in the seq" in {
    val originalSeq = DupelessSeq(1, 2)
    assert((originalSeq - 3) eq originalSeq)
  }

  it should "have a sensible toString" in {
    assert(DupelessSeq(1, 2, 3).toString === "DupelessSeq(1, 2, 3)")
  }

  it should "allow random access" in {
    assert(DupelessSeq(1, 2, 3, 4)(3) === 4)
  }

  it should "support splitting at an index" in {
    assert(DupelessSeq(1, 2, 3, 4, 5).splitAt(2) === (DupelessSeq(1, 2), DupelessSeq(3, 4, 5)))
  }

  it should "support updating an element with a new element" in {
    val original = DupelessSeq(1, 2, 3, 4, 5)

    val actual = original.updated(3, 8)

    val expected = DupelessSeq(1, 2, 3, 8, 5)

    assert(actual === expected)
  }

  it should "remove an element when updating it with an element that exists before it in the seq" in {
    val original = DupelessSeq(1, 2, 3, 4, 5)

    val actual = original.updated(3, 1)

    val expected = DupelessSeq(1, 2, 3, 5)

    assert(actual === expected)
  }

  it should "remove the later element when updating it with an element that exists after it in the seq" in {
    val original = DupelessSeq(1, 2, 3, 4, 5)

    val actual = original.updated(1, 3)

    val expected = DupelessSeq(1, 3, 4, 5)

    assert(actual === expected)
  }

  it should "update an element correctly when converting to a list" in {
    assert(DupelessSeq(1, 2).updated(0, 2)(toListCbf) === List(2, 2))
  }

  it can "pad the end of the seq with an element that is not in the seq" in {
    assert(DupelessSeq(1, 2, 3, 4, 5).padTo(99999, 6) === DupelessSeq(1, 2, 3, 4, 5, 6))
  }

  it should "return the same seq if padded with an element already in the seq" in {
    val original = DupelessSeq(1, 2, 3, 4, 5)

    assert(original.padTo(99999, 2) eq original)
  }

  it should "pad the end correctly when converting to a list" in {
    assert(DupelessSeq(1, 2).padTo(4, 2)(toListCbf) === List(1, 2, 2, 2))
  }

  it should "support sorting" in {
    assert(DupelessSeq(1, 5, 3, 2, 4).sorted === DupelessSeq(1, 2, 3, 4, 5))
  }

  it can "be reversed" in {
    assert(DupelessSeq(1, 2, 3, 4).reverse === DupelessSeq(4, 3, 2, 1))
  }

  it can "be iterated over" in {
    assert(DupelessSeq(1, 2, 3, 4).iterator.toList === List(1, 2, 3, 4))
  }

  it can "be iterated over in reverse" in {
    assert(DupelessSeq(1, 2, 3, 4).reverseIterator.toList === List(4, 3, 2, 1))
  }

  it should "return itself when asked for distinct elements" in {
    val originalSeq = DupelessSeq(1, 2, 3, 4)

    assert(originalSeq.distinct eq originalSeq)
  }

  it can "be converted to a Set" in {
    assert(DupelessSeq(1, 2, 3).toSet === Set(1, 2, 3))
  }

  it can "be converted to a Set of a higher type" in {
    val asSet = DupelessSeq(1, 2, 3).toSet[Any]

    assert(asSet.contains("hello world") === false)
  }

  it can "be converted to a Vector" in {
    assert(DupelessSeq(1, 2, 3).toVector === Vector(1, 2, 3))
  }

  it should "be equal to a list with the same elements" in {
    assert(DupelessSeq(1, 2, 3) === List(1, 2, 3))
  }

  it should "not be equal to a list with duplicated elements" in {
    assert(DupelessSeq(1, 2, 3) !== List(1, 2, 3, 1))
  }

  it should "not be equal to a set with the same elements" in {
    assert(DupelessSeq(1, 2, 3) !== Set(1, 2, 3))
  }

  "the empty DupelessSeq" should "be a singleton" in {
    assert(DupelessSeq() eq DupelessSeq())
  }

  "the DupelessSeq builder" should "build the empty seq" in {
    assert(DupelessSeq.newBuilder[Int].result() eq DupelessSeq.empty)
  }

  it should "build a seq with 2 distinct items" in {
    val builder = DupelessSeq.newBuilder[Int]

    builder += 1
    builder += 3

    assert(builder.result() === DupelessSeq(1, 3))
  }

  it should "iterate in the order of first insertion" in {
    val builder = DupelessSeq.newBuilder[Int]

    builder += 1
    builder += 3
    builder += 1

    assert(builder.result() === DupelessSeq(1, 3))
  }

  it can "be cleared" in {
    val builder = DupelessSeq.newBuilder[Int]

    builder += 1
    builder += 3
    builder.clear()

    assert(builder.result().isEmpty)
  }

  it can "append multiple elements at once" in {
    val builder = DupelessSeq.newBuilder[Int]

    builder ++= Vector(1, 3, 1)

    assert(builder.result() === DupelessSeq(1, 3))
  }

  it should "accept a size hint" in {
    val builder = DupelessSeq.newBuilder[Int]

    builder.sizeHint(5)

    builder += 1
    builder += 3

    assert(builder.result() === DupelessSeq(1, 3))
  }

  "the DupelessSeq cbf" should "allow building from a Set" in {
    assert(Set(1, 2, 3, 2).to[DupelessSeq] === DupelessSeq(1, 2, 3))
  }

  it should "allow building from a List" in {
    assert(List(1, 2, 3, 2).to[DupelessSeq] === DupelessSeq(1, 2, 3))
  }

  it should "allow building a new builder from another DupelessSeq" in {
    val other = DupelessSeq(1, 2, 3, 2)

    val builder = DupelessSeq.cbf[Int](other)

    builder += 1
    builder += 2
    builder += 3

    assert(builder.result() === other)
  }
}
