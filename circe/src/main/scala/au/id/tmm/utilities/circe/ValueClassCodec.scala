package au.id.tmm.utilities.circe

import io.circe.Decoder.{AccumulatingResult, Result}
import io.circe._

object ValueClassCodec {

  def apply[V, A : Encoder : Decoder](wrap: A => V)(unwrap: V => A): Codec[V] =
    new Codec[V] {
      override def apply(v: V): Json = Encoder[A].apply(unwrap(v))
      override def apply(c: HCursor): Result[V] = Decoder[A].apply(c).map(wrap)
      override def decodeAccumulating(c: HCursor): AccumulatingResult[V] = Decoder[A].decodeAccumulating(c).map(wrap)
    }

}
