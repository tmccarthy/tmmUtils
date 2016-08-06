package au.id.tmm.utilities.collection

import au.id.tmm.utilities.collection.CollectionUtils.Crossable
import au.id.tmm.utilities.collection.CollectionUtils.Sortable
import au.id.tmm.utilities.testing.ImprovedFlatSpec

import scala.collection.SortedSet

class CollectionUtilsSpec extends ImprovedFlatSpec {

  behaviour of "cross"

  it should "correctly cross two empty lists" in {
    assert((Nil cross Nil) === Nil)
  }

  it should "correctly cross [1, 2] and [3, 4]" in {
    assert((List(1, 2) cross List(3, 4)) === List((1, 3), (1, 4), (2, 3), (2, 4)))
  }

  behaviour of "toSortedSet"

  it should "correctly construct a sorted set from a list of comparables" in {
    assert(List("a", "a", "b", "c").toSortedSet === SortedSet("a", "b", "c"))
  }

  it should "correctly construct a sorted set from a list of Ints" in {
    assert(List(1, 1, 2, 3).toSortedSet === SortedSet(1, 2, 3))
  }

  it should "correctly construct a sorted set with an implicit custom Ordering" in {
    implicit val customOrdering = Ordering.Int.reverse

    assert(List(1, 1, 2, 3).toSortedSet.toList === List(3, 2, 1))
  }

  it should "correctly construct a sorted set from a list of Ordered" in {
    val first = TestOrdered("first")
    val second = TestOrdered("second")
    val third = TestOrdered("third")

    assert(List(first, second, third).toSortedSet === SortedSet(first, second, third))
  }
}

private final case class TestOrdered private (name: String) extends Ordered[TestOrdered] {
  val validNames = Set("first", "second", "third")

  require(validNames contains name)

  lazy val actualVal = name match {
    case "first" => 1
    case "second" => 2
    case "third" => 3
  }

  override def compare(that: TestOrdered): Int = Integer.compare(this.actualVal, that.actualVal)
}