package au.id.tmm.utilities.testing.syntax

import org.scalatest.FlatSpec

class TestingSyntaxSpec extends FlatSpec {

  "get on Either" should "return the right value" in {
    assert(Right("abc").get === "abc")
  }

  it should "throw an assertion error if the value is a left" in {
    val assertionError = intercept[AssertionError](Left("abc").get)

    assert(assertionError.getMessage === "Expected Right, but was Left(abc)")
  }

  "getLeft on Either" should "return the left value" in {
    assert(Left("abc").leftGet === "abc")
  }

  it should "throw an assertion error if the value is a right" in {
    val assertionError = intercept[AssertionError](Right("abc").leftGet)

    assert(assertionError.getMessage === "Expected Left, but was Right(abc)")
  }

}
