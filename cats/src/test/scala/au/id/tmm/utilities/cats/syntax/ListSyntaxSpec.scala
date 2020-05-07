package au.id.tmm.utilities.cats.syntax

import au.id.tmm.utilities.cats.syntax.list._
import cats.data.NonEmptyList
import org.scalatest.flatspec.AnyFlatSpec

class ListSyntaxSpec extends AnyFlatSpec {

  "groupByNelUniversalEquals" should "group into a nel using universal equals" in {
    val list = List("apple", "akubra", "banana")

    val actual = list.groupByNelUniversalEquals(_.head)

    val expected = Map(
      'a' -> NonEmptyList.of("apple", "akubra"),
      'b' -> NonEmptyList.of("banana"),
    )

    assert(expected === actual)
  }

  "groupMapNelUniversalEquals" should "group into a nel using universal equals and then map" in {
    val list = List("apple", "akubra", "banana")

    val actual = list.groupMapNelUniversalEquals(_.head)(_.toUpperCase)

    val expected = Map(
      'a' -> NonEmptyList.of("APPLE", "AKUBRA"),
      'b' -> NonEmptyList.of("BANANA"),
    )

    assert(expected === actual)
  }

}
