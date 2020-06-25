package au.id.tmm.utilities.circe

import au.id.tmm.utilities.circe.syntax._
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

class KeyValueCodecSpec extends AnyFlatSpec with FlatSpecDiscipline with scalatest.prop.Configuration {

  import KeyValueCodecSpec._

  private implicit val sut: Codec[Map[Set[Fruit], Fruit]] = KeyValueCodec("fruits", "winner")

  "a rich map encoder" should "encode as expected" in {
    val map: Map[Set[Fruit], Fruit] = Map(
      (Set(Fruit.Apple, Fruit.Banana): Set[Fruit])         -> Fruit.Apple,
      (Set(Fruit.Watermelon, Fruit.Pineapple): Set[Fruit]) -> Fruit.Pineapple,
    )

    val expectedJson = Json.arr(
      Json.obj(
        "fruits" := Set("Apple", "Banana"),
        "winner" := "Apple",
      ),
      Json.obj(
        "fruits" := Set("Watermelon", "Pineapple"),
        "winner" := "Pineapple",
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
        "fruits" := Set("Watermelon", "Pineapple"),
        "winner" := "Pineapple",
      ),
    )

    val expectedMap: Map[Set[Fruit], Fruit] = Map(
      (Set(Fruit.Apple, Fruit.Banana): Set[Fruit])         -> Fruit.Apple,
      (Set(Fruit.Watermelon, Fruit.Pineapple): Set[Fruit]) -> Fruit.Pineapple,
    )

    assert(json.as[Map[Set[Fruit], Fruit]] === Right(expectedMap))
  }

  it should "fail to decode where there are duplicate keys" in {
    val json = Json.arr(
      Json.obj(
        "fruits" := Set("Apple", "Banana"),
        "winner" := "Apple",
      ),
      Json.obj(
        "fruits" := Set("Apple", "Banana"),
        "winner" := "Pineapple",
      ),
    )

    assert(json.as[Map[Set[Fruit], Fruit]].left.map(_.message) === Left("Duplicate key Set(Apple, Banana)"))
  }

  checkAll("RichMapCodec[Map[Set[Fruit], Fruit]", CodecTests[Map[Set[Fruit], Fruit]].codec)

}

object KeyValueCodecSpec {

  implicit val fruitCodec: Codec[Fruit] =
    Codec.instance[String].iemap[Fruit](s => Fruit.ALL.find(_.name == s).toRight(s))(_.name)

}
