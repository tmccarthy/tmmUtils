package au.id.tmm.utilities.collection.syntax

import au.id.tmm.utilities.testing.syntax._
import org.scalatest.FlatSpec

class IterableOpsSpec extends FlatSpec {

  "at most one" should "return None for an empty iterable" in {
    assert(List.empty[Int].atMostOne.get === None)
  }

  it should "return Some for a single element list" in {
    assert(List(1).atMostOne.get === Some(1))
  }

  it should "return an error for a 2 element list" in {
    assert(List(1, 2).atMostOne.isLeft)
  }

  it should "return an error for an infinitely sized list" in {
    assert(LazyList.continually(0).atMostOne.isLeft)
  }

  "only element" should "return an error for an empty iterable" in {
    assert(List.empty[Int].onlyElementOrException.isLeft)
  }

  it should "return the element for a 1 element iterable" in {
    assert(List(1).onlyElementOrException.get === 1)
  }

  it should "return an error for a 2 element list" in {
    assert(List(1, 2).onlyElementOrException.isLeft)
  }

  it should "return an error for an infinitely sized list" in {
    assert(LazyList.continually(0).onlyElementOrException.isLeft)
  }

}
