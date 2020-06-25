package au.id.tmm.utilities.circe

import au.id.tmm.utilities.testing.CoinToss
import io.circe.syntax.EncoderOps
import io.circe.{Codec, Encoder, Json}
import org.scalatest.flatspec.AnyFlatSpec

class EnumeratedCodecSpec extends AnyFlatSpec {

  private implicit val sut: Codec[CoinToss] = EnumeratedCodec(
    CoinToss.Heads -> "heads",
    CoinToss.Tails -> "tails",
  )

  "an enumerated codec" can "decode successfully" in {
    assert(Json.fromString("heads").as[CoinToss] === Right(CoinToss.Heads))
  }

  it can "fail to decode an invalid value" in {
    assert(
      Json.fromString("invalid").as[CoinToss].left.map(_.message) === Left(
        "Could not construct au.id.tmm.utilities.testing.CoinToss from invalid",
      ),
    )
  }

  it can "encode successfully" in {
    assert((CoinToss.Heads: CoinToss).asJson === Json.fromString("heads"))
  }

  it can "fail to encode" in {
    val unsafeEncoder: Encoder[CoinToss] = EnumeratedCodec(
      CoinToss.Heads -> "heads",
    )

    intercept[IllegalStateException](unsafeEncoder.apply(CoinToss.Tails))
  }

  it can "not be constructed when there are duplicate keys" in {
    intercept[IllegalArgumentException] {
      EnumeratedCodec(
        CoinToss.Heads -> "heads",
        CoinToss.Heads -> "HEADS",
        CoinToss.Tails -> "tails",
      )
    }
  }

  it can "not be constructed when there are duplicate values" in {
    intercept[IllegalArgumentException] {
      EnumeratedCodec(
        CoinToss.Heads -> "heads",
        CoinToss.Tails -> "heads",
      )
    }
  }

}
