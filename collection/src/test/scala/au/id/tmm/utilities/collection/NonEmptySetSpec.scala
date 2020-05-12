package au.id.tmm.utilities.collection

import org.scalatest.FlatSpec

class NonEmptySetSpec extends FlatSpec {

  "A non-empty set" can "be constructed with a single element" in {
    assert(NonEmptySet.one(1).toList === List(1))
  }

  it can "be constructed with multiple elements" in {
    assert(NonEmptySet.of(1, 2, 3).toList === List(1, 2, 3))
  }

  it should "be equal to another set with a different implementation but same elements" in {
    assert(NonEmptySet.of(1, 2, 3) === Set(1, 2, 3))
  }

  it can "be constructed from a non-empty set" in {
    assert(NonEmptySet.fromSet(Set(1)) === Some(NonEmptySet.of(1)))
  }

  it can "not be constructed from an empty set" in {
    assert(NonEmptySet.fromSet(Set.empty[Int]) === None)
  }

  it can "be constructed unsafely from a non-empty set" in {
    assert(NonEmptySet.fromSetUnsafe(Set(1)) === NonEmptySet.of(1))
  }

  it can "not be constructed unsafely from an empty set" in {
    intercept[IllegalArgumentException](NonEmptySet.fromSetUnsafe(Set.empty))
  }

  it can "produce subsets" in {
    val set = NonEmptySet.of(1, 2)
    val expectedSubsets: Set[Int] => Boolean = Set(
      Set(),
      Set(1),
      Set(2),
      Set(1, 2),
    )
    assert(set.subsets().toSet === expectedSubsets)
  }

  it should "have a sensible toString" in {
    assert(NonEmptySet.of(1, 2).toString === "NonEmptySet(1, 2)")
  }

}
