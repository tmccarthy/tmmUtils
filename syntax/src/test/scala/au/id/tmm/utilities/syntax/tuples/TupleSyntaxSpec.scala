package au.id.tmm.utilities.syntax.tuples

import munit.FunSuite

class TupleSyntaxSpec extends FunSuite {

  test("the tuple type syntax should work in a collection") {
    List(
      "hello" -> 1,
      "world" -> 2,
    ): List[String -> Int]
  }

}
