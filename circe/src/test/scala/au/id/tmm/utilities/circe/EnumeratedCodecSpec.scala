package au.id.tmm.utilities.circe

import au.id.tmm.utilities.testing.CoinToss
import io.circe.syntax.EncoderOps
import io.circe.{Codec, Encoder, Json}
import munit.FunSuite

class EnumeratedCodecSpec extends FunSuite {

  private implicit val sut: Codec[CoinToss] = EnumeratedCodec(
    CoinToss.Heads -> "heads",
    CoinToss.Tails -> "tails",
  )

  test("decode successfully") {
    assertEquals(Json.fromString("heads").as[CoinToss], Right(CoinToss.Heads))
  }

  test("fail to decode an invalid value") {
    assertEquals(
      Json.fromString("invalid").as[CoinToss].left.map(_.message),
      Left("Could not construct au.id.tmm.utilities.testing.CoinToss from invalid"),
    )
  }

  test("encode successfully") {
    assertEquals((CoinToss.Heads: CoinToss).asJson, Json.fromString("heads"))
  }

  test("fail to encode") {
    val unsafeEncoder: Encoder[CoinToss] = EnumeratedCodec(
      CoinToss.Heads -> "heads",
    )

    intercept[IllegalStateException](unsafeEncoder.apply(CoinToss.Tails))
  }

  test("not be constructed when there are duplicate keys") {
    intercept[IllegalArgumentException] {
      EnumeratedCodec(
        CoinToss.Heads -> "heads",
        CoinToss.Heads -> "HEADS",
        CoinToss.Tails -> "tails",
      )
    }
  }

  test("not be constructed when there are duplicate values") {
    intercept[IllegalArgumentException] {
      EnumeratedCodec(
        CoinToss.Heads -> "heads",
        CoinToss.Tails -> "heads",
      )
    }
  }

}
