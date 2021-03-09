package au.id.tmm.utilities.testing.syntax

import munit.FunSuite

class TestingSyntaxSpec extends FunSuite {

  test("get on Either should return the right value") {
    assertEquals(Right("abc").get, "abc")
  }

  test("get on Either should throw an assertion error if the value is a left") {
    val assertionError = intercept[AssertionError](Left("abc").get)

    assertEquals(assertionError.getMessage, "Expected Right, but was Left(abc)")
  }

  test("get on Either should throw an assertion error with the left value as the cause if the left is throwable") {
    val cause = new Exception()

    val assertionError = intercept[AssertionError](Left(cause).get)

    assertEquals(assertionError.getCause, cause)
  }

  test("getLeft on Either should return the left value") {
    assertEquals(Left("abc").leftGet, "abc")
  }

  test("getLeft on Either should throw an assertion error if the value is a right") {
    val assertionError = intercept[AssertionError](Right("abc").leftGet)

    assertEquals(assertionError.getMessage, "Expected Left, but was Right(abc)")
  }

}
