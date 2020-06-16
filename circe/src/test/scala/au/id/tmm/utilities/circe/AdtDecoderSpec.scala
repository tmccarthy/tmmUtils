package au.id.tmm.utilities.circe

import au.id.tmm.utilities.circe.AdtDecoderSpec.MyAdt
import io.circe.{Decoder, Json}
import org.scalatest.flatspec.AnyFlatSpec

class AdtDecoderSpec extends AnyFlatSpec {

  private implicit val intWrapperDecoder: Decoder[MyAdt.IntWrapper] = Decoder[Int].map(MyAdt.IntWrapper.apply)
  private implicit val stringWrapperDecoder: Decoder[MyAdt.StringWrapper] = Decoder[String].map(MyAdt.StringWrapper.apply)

  private implicit val sut: Decoder[MyAdt] = AdtDecoder.decodes[MyAdt].forSum2[MyAdt.IntWrapper, MyAdt.StringWrapper]

  "the myAdtDecoder" should "work for a wrapped int" in {
    assert(Json.fromInt(1).as[MyAdt] === Right(MyAdt.IntWrapper(1)))
  }

  it should "work for a wrapped string" in {
    assert(Json.fromString("hello").as[MyAdt] === Right(MyAdt.StringWrapper("hello")))
  }

  it should "fail for an array" in {
    assert(Json.arr(Json.fromInt(1)).as[MyAdt].isLeft)
  }

}

object AdtDecoderSpec {
  sealed trait MyAdt

  object MyAdt {
    case class IntWrapper(int: Int) extends MyAdt
    case class StringWrapper(string: String) extends MyAdt
  }
}