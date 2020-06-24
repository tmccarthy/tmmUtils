package au.id.tmm.utilities.circe

import cats.Applicative
import io.circe.syntax.EncoderOps
import io.circe.{Codec, Decoder, Encoder, Json}

object RichMapCodec {

  // TODO could probably find a way to make this more generic (an iterable of tuples)
  // TODO should be an encoder/decoder
  def apply[K : Encoder : Decoder, V : Encoder : Decoder](describeKey: String, describeValue: String): Codec[Map[K, V]] =
    Codec.from(decoder(describeKey, describeValue), encoder(describeKey, describeValue))

  def decoder[K : Decoder, V : Decoder](describeKey: String, describeValue: String): Decoder[Map[K, V]] = {
    implicit val decodeTuples: Decoder[(K, V)] = richTupleDecoder(describeKey, describeValue)

    Decoder.decodeIterable[(K, V), Iterable].map(_.toMap) // TODO is this safe?
  }

  def encoder[K : Encoder, V : Encoder](describeKey: String, describeValue: String): Encoder[Map[K, V]] = {
    implicit val encodeTuples: Encoder[(K, V)] = richTupleEncoder[K, V](describeKey, describeValue)

    Encoder.encodeIterable[(K, V), Iterable].contramap(_.toIterable)
  }

  private def richTupleEncoder[K : Encoder, V : Encoder](describeKey: String, describeValue: String): Encoder[(K, V)] =
    {
      case (k, v) => Json.obj(
        describeKey -> k.asJson,
        describeValue -> v.asJson,
      )
    }

  private def richTupleDecoder[K : Decoder, V : Decoder](describeKey: String, describeValue: String): Decoder[(K, V)] =
    Applicative[Decoder].map2[K, V, (K, V)](c => c.get[K](describeKey), c => c.get[V](describeValue))((k, v) => (k, v))

}
