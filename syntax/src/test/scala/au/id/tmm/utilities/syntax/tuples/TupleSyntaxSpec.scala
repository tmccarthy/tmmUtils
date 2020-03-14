package au.id.tmm.utilities.syntax.tuples

import org.scalatest.FlatSpec

class TupleSyntaxSpec extends FlatSpec {

  "the tuple type syntax" should "work in a collection" in {
    List(
      "hello" -> 1,
      "world" -> 2,
    ): List[String -> Int]
  }

}
