package au.id.tmm.utilities.errors

import munit.FunSuite

import scala.collection.immutable.ArraySeq

class StructuredExceptionSpec extends FunSuite {

  test("it can be created without a cause") {
    val actualException = StructuredException(
      name = "genericException",
      "field1" -> "value1",
      "field2" -> 2,
    )

    val expectedException = StructuredException(
      name = "genericException",
      fields = ArraySeq(
        "field1" -> "value1",
        "field2" -> "2",
      ),
      cause = None,
    )

    assertEquals(actualException, expectedException)
  }

  test("it can be copied to add a cause") {
    val cause = GenericException("cause")

    val actualException = StructuredException(
      name = "genericException",
      "field1" -> "value1",
      "field2" -> 2,
    ).withCause(cause)

    val expectedException = StructuredException(
      name = "genericException",
      fields = ArraySeq(
        "field1" -> "value1",
        "field2" -> "2",
      ),
      cause = Some(cause),
    )

    assertEquals(actualException, expectedException)
  }

  test("it should produce a sensible message") {
    val exception = StructuredException(
      name = "EXCEPTION",
      "field1" -> "value1",
      "field2" -> 2,
    )

    val expectedMessage =
      """EXCEPTION
        |			field1=value1
        |			field2=2""".stripMargin

    assertEquals(exception.getMessage, expectedMessage)
  }

  test("it should produce a sensible message when one value is an iterable") {
    val exception = StructuredException(
      name = "EXCEPTION",
      "field1" -> "value1",
      "field2" -> List(1, 2, 3),
    )

    val expectedMessage =
      """EXCEPTION
        |			field1=value1
        |			field2=List(
        |				1,
        |				2,
        |				3,
        |			)""".stripMargin

    assertEquals(exception.getMessage, expectedMessage)
  }

  test("it should produce a sensible message when one value is an empty iterable") {
    val exception = StructuredException(
      name = "EXCEPTION",
      "field1" -> "value1",
      "field2" -> List(),
    )

    val expectedMessage =
      """EXCEPTION
        |			field1=value1
        |			field2=List()""".stripMargin

    assertEquals(exception.getMessage, expectedMessage)
  }

  test("it should produce a sensible message when there are no fields") {
    val exception = StructuredException(
      name = "EXCEPTION",
    )

    val expectedMessage = "EXCEPTION"

    assertEquals(exception.getMessage, expectedMessage)
  }

}
