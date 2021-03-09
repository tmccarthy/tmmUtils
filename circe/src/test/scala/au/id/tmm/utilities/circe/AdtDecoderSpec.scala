package au.id.tmm.utilities.circe

import au.id.tmm.utilities.circe.AdtDecoderSpec.MyAdt
import io.circe.{Decoder, Json}
import munit.FunSuite

class AdtDecoderSpec extends FunSuite {

  private implicit val intWrapperDecoder: Decoder[MyAdt.IntWrapper] = Decoder[Int].map(MyAdt.IntWrapper.apply)
  private implicit val stringWrapperDecoder: Decoder[MyAdt.StringWrapper] =
    Decoder[String].map(MyAdt.StringWrapper.apply)

  private implicit val sut: Decoder[MyAdt] = AdtDecoder.decodes[MyAdt].forSum2[MyAdt.IntWrapper, MyAdt.StringWrapper]

  test("work for a wrapped int") {
    assertEquals(Json.fromInt(1).as[MyAdt], Right(MyAdt.IntWrapper(1)))
  }

  test("work for a wrapped string") {
    assertEquals(Json.fromString("hello").as[MyAdt], Right(MyAdt.StringWrapper("hello")))
  }

  test("fail for an array") {
    assert(Json.arr(Json.fromInt(1)).as[MyAdt].isLeft)
  }

}

object AdtDecoderSpec {
  sealed trait MyAdt

  object MyAdt {
    case class IntWrapper(int: Int)          extends MyAdt
    case class StringWrapper(string: String) extends MyAdt
  }
}
