package au.id.tmm.utilities.circe

import au.id.tmm.utilities.testing.Fruit
import au.id.tmm.utilities.testing.cats.instances.fruit._
import au.id.tmm.utilities.testing.scalacheck.instances.fruit._
import cats.instances.map._
import io.circe.syntax._
import io.circe.testing.CodecTests
import io.circe.testing.instances._
import io.circe.{Codec, Json}
import org.scalatest
import org.scalatest.flatspec.AnyFlatSpec
import org.typelevel.discipline.scalatest.FlatSpecDiscipline

class RichMapCodecSpec extends AnyFlatSpec with FlatSpecDiscipline with scalatest.prop.Configuration {

  import RichMapCodecSpec._

  private implicit val sut: Codec[Map[Set[Fruit], Fruit]] = RichMapCodec("fruits", "winner")

  "a rich map encoder" should "encode as expected" in {
    val map: Map[Set[Fruit], Fruit] = Map(
      (Set(Fruit.Apple, Fruit.Banana): Set[Fruit])         -> Fruit.Apple,
      (Set(Fruit.Watermelon, Fruit.Raspberry): Set[Fruit]) -> Fruit.Raspberry,
    )

    val expectedJson = Json.arr(
      Json.obj(
        "fruits" := Set("Apple", "Banana"),
        "winner" := "Apple",
      ),
      Json.obj(
        "fruits" := Set("Watermelon", "Raspberry"),
        "winner" := "Raspberry",
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
        "winner" := "Raspberry",
      ),
    )

    val expectedMap: Map[Set[Fruit], Fruit] = Map(
      (Set(Fruit.Apple, Fruit.Banana): Set[Fruit])         -> Fruit.Apple,
      (Set(Fruit.Watermelon, Fruit.Raspberry): Set[Fruit]) -> Fruit.Raspberry,
    )

    assert(json.as[Map[Set[Fruit], Fruit]] === Right(expectedMap))
  }

  checkAll("RichMapCodec[Map[Set[Fruit], Fruit]", CodecTests[Map[Set[Fruit], Fruit]].codec)

}

object RichMapCodecSpec {

  implicit val fruitCodec: Codec[Fruit] =
    Codec.instance[String].iemap[Fruit](s => Fruit.ALL.find(_.name == s).toRight(s))(_.name)

}
