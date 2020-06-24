package au.id.tmm.utilities.testing.syntax

import org.scalatest.flatspec.AnyFlatSpec

class TestingSyntaxSpec extends AnyFlatSpec {

  "get on Either" should "return the right value" in {
    assert(Right("abc").get === "abc")
  }

  it should "throw an assertion error if the value is a left" in {
    val assertionError = intercept[AssertionError](Left("abc").get)

    assert(assertionError.getMessage === "Expected Right, but was Left(abc)")
  }

  it should "throw an assertion error with the left value as the cause if the left is throwable" in {
    val cause = new Exception()

    val assertionError = intercept[AssertionError](Left(cause).get)

    assert(assertionError.getCause === cause)
  }

  "getLeft on Either" should "return the left value" in {
    assert(Left("abc").leftGet === "abc")
  }

  it should "throw an assertion error if the value is a right" in {
    val assertionError = intercept[AssertionError](Right("abc").leftGet)

    assert(assertionError.getMessage === "Expected Left, but was Right(abc)")
  }

}
