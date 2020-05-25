package au.id.tmm.utilities.errors

import org.scalatest.FlatSpec

import scala.collection.immutable.ArraySeq

class StructuredExceptionSpec extends FlatSpec {

  "A structured exception" can "be created without a cause" in {
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

    assert(actualException === expectedException)
  }

  it can "be copied to add a cause" in {
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

    assert(actualException === expectedException)
  }

  it should "produce a sensible message" in {
    val exception = StructuredException(
      name = "EXCEPTION",
      "field1" -> "value1",
      "field2" -> 2,
    )

    val expectedMessage = "EXCEPTION\n\t\t\tfield1=value1\n\t\t\tfield2=2"

    assert(exception.getMessage === expectedMessage)
  }

  it should "produce a sensible message when one value is an iterable" in {
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

    assert(exception.getMessage === expectedMessage)
  }

  it should "produce a sensible message when there are no fields" in {
    val exception = StructuredException(
      name = "EXCEPTION",
    )

    val expectedMessage = "EXCEPTION"

    assert(exception.getMessage === expectedMessage)
  }

}
