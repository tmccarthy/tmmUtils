package au.id.tmm.utilities.cats.syntax

import au.id.tmm.utilities.cats.syntax.vector._
import cats.data.NonEmptyVector
import org.scalatest.flatspec.AnyFlatSpec

class VectorSyntaxSpec extends AnyFlatSpec {

  "groupByNevUniversalEquals" should "group into a nel using universal equals" in {
    val vector = Vector("apple", "akubra", "banana")

    val actual = vector.groupByNevUniversalEquals(_.head)

    val expected = Map(
      'a' -> NonEmptyVector.of("apple", "akubra"),
      'b' -> NonEmptyVector.of("banana"),
    )

    assert(expected === actual)
  }

  "groupMapNevUniversalEquals" should "group into a nel using universal equals and then map" in {
    val vector = Vector("apple", "akubra", "banana")

    val actual = vector.groupMapNevUniversalEquals(_.head)(_.toUpperCase)

    val expected = Map(
      'a' -> NonEmptyVector.of("APPLE", "AKUBRA"),
      'b' -> NonEmptyVector.of("BANANA"),
    )

    assert(expected === actual)
  }

}
