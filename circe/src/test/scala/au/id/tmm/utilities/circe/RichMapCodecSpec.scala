package au.id.tmm.utilities.circe

import au.id.tmm.utilities.testing.Fruit
import cats.kernel.Eq
import io.circe.syntax._
import io.circe.testing.CodecTests
import io.circe.{Codec, Json}
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest
import org.scalatest.flatspec.AnyFlatSpec
import org.typelevel.discipline.scalatest.FlatSpecDiscipline
import io.circe.testing.instances._
import cats.instances.map._

class RichMapCodecSpec extends AnyFlatSpec with FlatSpecDiscipline with scalatest.prop.Configuration {

  import RichMapCodecSpec._

  private implicit val sut: Codec[Map[Set[Fruit], Fruit]] = RichMapCodec("fruits", "winner")

  "a rich map encoder" should "encode as expected" in {
    val map: Map[Set[Fruit], Fruit] = Map(
      (Set(Fruit.Apple, Fruit.Banana): Set[Fruit]) -> Fruit.Apple,
      (Set(Fruit.Watermelon, Fruit.Raspberry): Set[Fruit]) -> Fruit.Raspberry,
    )

    val expectedJson = Json.arr(
      Json.obj(
        "fruits" := Set("Apple", "Banana"),
        "winner" := "Apple",
      ),
      Json.obj(
        "fruits" := Set("Watermelon", "Raspberry"),
        "winner" :=  "Raspberry",
      ),
    )

    assert(map.asJson === expectedJson)
  }

  it should "decode as expected" in {
    val json = Json.arr(
      Json.obj(
        "fruits" := Set("Apple", "Banana"),
        "winner" := "Apple",
      ),
      Json.obj(
        "fruits" := Set("Watermelon", "Raspberry"),
        "winner" :=  "Raspberry",
      ),
    )

    val expectedMap: Map[Set[Fruit], Fruit] = Map(
      (Set(Fruit.Apple, Fruit.Banana): Set[Fruit]) -> Fruit.Apple,
      (Set(Fruit.Watermelon, Fruit.Raspberry): Set[Fruit]) -> Fruit.Raspberry,
    )

    assert(json.as[Map[Set[Fruit], Fruit]] === Right(expectedMap))
  }

  checkAll("RichMapCodec[Map[Set[Fruit], Fruit]", CodecTests[Map[Set[Fruit], Fruit]].codec)

}

object RichMapCodecSpec {

  implicit val fruitCodec: Codec[Fruit] =
    Codec.instance[String].iemap[Fruit](s => Fruit.ALL.find(_.toString == s).toRight(s))(_.toString)

  // TODO this should be in a project somewhere
  implicit val arbitraryFruit: Arbitrary[Fruit] =
    Arbitrary(Gen.oneOf(Fruit.ALL))

  implicit val eqFruit: Eq[Fruit] = Eq.fromUniversalEquals

}