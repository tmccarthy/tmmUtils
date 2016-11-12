package au.id.tmm.utilities.collection

import java.io.Closeable

import au.id.tmm.utilities.collection.CloseableIterator.{IterableConstruction, IteratorConstruction}
import au.id.tmm.utilities.testing.ImprovedFlatSpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class CloseableIteratorSpec extends ImprovedFlatSpec {

  private val testCloseable = new Closeable {
    var isClosed = false

    override def close(): Unit = isClosed = true
  }

  private lazy val testDuckCloseable = new Object {
    var isClosed = false

    def close(): Unit = isClosed = true
  }

  private val data = Range(0, 5).toList

  private val underlyingIterator = data.toIterator

  private val sut = CloseableIterator(underlyingIterator, testCloseable)

  behaviour of "the duck typing constructor"

  it should "allow the closing of the underlying resource" in {
    val closeableIterator = CloseableIterator(underlyingIterator, testDuckCloseable)

    closeableIterator.close()

    assert(testDuckCloseable.isClosed)
  }

  behaviour of "the toCloseableIterator utility"

  it should "construct a CloseableIterator from a List" in {
    val closeableIterator = data.toCloseableIterator

    assert(closeableIterator.toList === data)
  }

  it should "construct a CloseableIterator from an Iterator" in {
    val closeableIterator = data.toIterator.toCloseableIterator

    assert(closeableIterator.toList === data)
  }

  it should "make a CloseableIterator that does nothing on a call to close" in {
    val closeableIterator = data.toCloseableIterator

    closeableIterator.close()
  }

  behaviour of "the toCloseableIteratorWith utility"

  it should "construct a CloseableIterator from a List" in {
    val closeableIterator = data.toCloseableIteratorWith(testCloseable)

    assert(closeableIterator.toList === data)
  }

  it should "close the underlying resource correctly when constructed from a List" in {
    val closeableIterator = data.toCloseableIteratorWith(testCloseable)

    closeableIterator.close()

    assert(testCloseable.isClosed)
  }

  it should "close the underlying (duck typed) resource correctly when constructed from a List" in {
    val closeableIterator = data.toCloseableIteratorWith(testDuckCloseable)

    closeableIterator.close()

    assert(testDuckCloseable.isClosed)
  }

  it should "construct a CloseableIterator from an Iterator" in {
    val closeableIterator = data.toIterator.toCloseableIteratorWith(testCloseable)

    assert(closeableIterator.toList === data)
  }

  it should "close the underlying resource correctly when constructed from an Iterator" in {
    val closeableIterator = data.toIterator.toCloseableIteratorWith(testCloseable)

    closeableIterator.close()

    assert(testCloseable.isClosed)
  }

  it should "close the underlying (duck typed) resource correctly when constructed from an Iterator" in {
    val closeableIterator = data.toIterator.toCloseableIteratorWith(testDuckCloseable)

    closeableIterator.close()

    assert(testDuckCloseable.isClosed)
  }

  behaviour of "the CanBuildFrom"

  it should "build a CloseableIterator from scratch" in {
    val builder = CloseableIterator.CloseableIteratorCanBuildFrom[String]()

    val elements = List("hello", "world")

    builder ++= elements

    val closeableIterator = builder.result()

    assert(closeableIterator.toList === elements)
  }

  it should "build a CloseableIterator from an existing one" in {
    val builder = CloseableIterator.CloseableIteratorCanBuildFrom(data)

    builder += (5, 6)

    val closeableIterator = builder.result()

    assert(closeableIterator.toList === List(0, 1, 2, 3, 4, 5, 6))
  }

  it should "build a CloseableIterator from scratch using the CloseableIterator CanBuildFrom" in {
    val builder = CloseableIterator.CloseableIteratorCanBuildFromOther[String]()

    val elements = List("hello", "world")

    builder ++= elements

    val closeableIterator = builder.result()

    assert(closeableIterator.toList === elements)
  }

  it should "build a Future CloseableIterator from a CloseableIterator of Futures with Future.sequence" in {
    val closeableIteratorOfFutures = Iterator(
      Future(1),
      Future(2),
      Future(3)
    ).toCloseableIteratorWith(testCloseable)

    val futureOfResults = Future.sequence(closeableIteratorOfFutures)

    Await.result(futureOfResults, Duration.Inf).close()

    assert(testCloseable.isClosed)
  }

  it should "return a builder that can be cleared" in {
    val builder = CloseableIterator.CloseableIteratorCanBuildFrom[String]()

    builder ++= List("hello", "world")

    builder.clear()

    val closeableIterator = builder.result()

    assert(closeableIterator.toList === Nil)
  }

  behaviour of "the close method"

  it should "call close on the underlying resource" in {
    sut.close()

    assert(testCloseable.isClosed === true)
  }

  "seq" should "produce a seq from the underlying iterator" in {
    assert(sut.seq.toList === data)
  }

  testSimpleMappingMethod("take", _.take(2), List(0, 1))

  testSimpleMappingMethod("drop", _.drop(2), List(2, 3, 4))

  testSimpleMappingMethod("slice", _.slice(1, 3), List(1, 2))

  testSimpleMappingMethod("map", _.map(_ + 10), List(10, 11, 12, 13, 14))

  testSimpleMappingMethod("++", _ ++ List(5, 6), List(0, 1, 2, 3, 4, 5, 6))

  testSimpleMappingMethod("flatMap", _.flatMap(List(_, 8)), List(0, 8, 1, 8, 2, 8, 3, 8, 4, 8))

  testSimpleMappingMethod("filter", _.filter(_ > 2), List(3, 4))

  testSimpleMappingMethod("withFilter", _.withFilter(_ > 2), List(3, 4))

  testSimpleMappingMethod("filterNot", _.filterNot(_ > 2), List(0, 1, 2))

  testSimpleMappingMethod("collect", _.collect{case x: Int if x > 2 => x + 2}, List(5, 6))

  testSimpleMappingMethod("scanLeft", _.scanLeft(0)(_ + _), List(0, 0, 1, 3, 6, 10))

  testSimpleMappingMethod("scanRight", _.scanRight(0)(_ + _), List(10, 10, 9, 7, 4, 0))

  testSimpleMappingMethod("takeWhile", _.takeWhile(_ < 2), List(0, 1))

  testSimpleMappingMethod("dropWhile", _.dropWhile(_ < 2), List(2, 3, 4))

  testSimpleMappingMethod("padTo", _.padTo(8, -1), List(0, 1, 2, 3, 4, -1, -1, -1))

  testSimpleMappingMethod("patch", _.patch(1, List(-1, -2, -3).toIterator, 2), List(0, -1, -2, -3, 3, 4))

  testSimpleMappingMethod("toIterator", _.toIterator, data)

  testMappingMethod[(Int, String)]("zip", _.zip(List("a", "b", "c").toIterator), List((0, "a"), (1, "b"), (2, "c")))

  testMappingMethod[(Int, Int)]("zipWithIndex", _.zipWithIndex, List((0, 0), (1, 1), (2, 2), (3, 3), (4, 4)))

  testMappingMethod[(Int, String)]("zipAll", _.zipAll(List("a", "b", "c").toIterator, -1, "z"), List((0, "a"), (1, "b"), (2, "c"), (3, "z"), (4, "z")))

  behaviour of "partition"

  it should "return closeable iterators" in {
    val (evens, odds) = sut.partition(_ % 2 == 0)

    assert(evens.isInstanceOf[CloseableIterator[Int]])
    assert(odds.isInstanceOf[CloseableIterator[Int]])
  }

  it should "work like the super implementation" in {
    val (evens, odds) = sut.partition(_ % 2 == 0)

    assert(evens.toList === List(0, 2, 4))
    assert(odds.toList === List(1, 3))
  }

  behaviour of "span"

  it should "return closeable iterators" in {
    val (evensPrefix, rest) = sut.span(_ % 2 == 0)

    assert(evensPrefix.isInstanceOf[CloseableIterator[Int]])
    assert(rest.isInstanceOf[CloseableIterator[Int]])
  }

  it should "work like the super implementation" in {
    val (evensPrefix, rest) = sut.span(_ % 2 == 0)

    assert(evensPrefix.toList === List(0))
    assert(rest.toList === List(1, 2, 3, 4))
  }

  behaviour of "duplicate"

  it should "return closeable iterators" in {
    val (left, right) = sut.duplicate

    assert(left.isInstanceOf[CloseableIterator[Int]])
    assert(right.isInstanceOf[CloseableIterator[Int]])
  }

  it should "work like the super implementation" in {
    val (left, right) = sut.duplicate

    assert(left.toList === data)
    assert(right.toList === data)
  }

  behaviour of "grouped"

  it should "return closeable grouped iterators" in {
    val grouped = sut.grouped(2)

    assert(grouped.isInstanceOf[sut.CloseableGroupedIterator[Int]])
  }

  it should "return an iterator that can be mapped like any closeable iterator" in {
    val grouped = sut.grouped(2)

    assert(grouped.map(_ :+ 3).toList === List(List(0, 1, 3), List(2, 3, 3), List(4, 3)))
  }

  it should "return an iterator that can be duplicated like any closeable iterator" in {
    val grouped = sut.grouped(2)

    val (left, right) = grouped.duplicate

    val expected = List(List(0, 1), List(2, 3), List(4))

    assert(left.toList === expected)
    assert(right.toList === expected)
  }

  it should "return an iterator that closes the underlying iterator on close" in {
    val grouped = sut.grouped(2)

    grouped.close()

    assert(testCloseable.isClosed)
  }

  it should "work like the super implementation" in {
    val grouped = sut.grouped(2)

    assert(grouped.toList === List(List(0, 1), List(2, 3), List(4)))
  }

  behaviour of "sliding"

  it should "return closeable grouped iterators" in {
    val sliding = sut.sliding(2, 1)

    assert(sliding.isInstanceOf[sut.CloseableGroupedIterator[Int]])
  }

  it should "work like the super implementation" in {
    val sliding = sut.sliding(2, 1)

    assert(sliding.toList === List(List(0, 1), List(1, 2), List(2, 3), List(3, 4)))
  }

  "buffered" should "be unsupported" in {
    intercept[UnsupportedOperationException] {
      sut.buffered
    }
  }

  "toVector" should "close the iterator" in {
    sut.toVector

    assert(testCloseable.isClosed)
  }

  def testMappingMethod[A](methodName: String, testInvocation: (CloseableIterator[Int]) => CloseableIterator[A], expected: => List[A]): Unit = {
    behaviour of methodName

    it should "return a closeable iterator" in {
      testInvocation(sut).isInstanceOf[CloseableIterator[Int]]
    }

    it should "work like the super implementation" in {
      assert(testInvocation(sut).toList === expected)
    }
  }

  def testSimpleMappingMethod(methodName: String, testInvocation: (CloseableIterator[Int] => CloseableIterator[Int]), expected: => List[Int]): Unit =
    testMappingMethod[Int](methodName, testInvocation, expected)
}
