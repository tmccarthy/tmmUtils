package au.id.tmm.utilities.circe

import io.circe.Decoder.Result
import io.circe.{Codec, Decoder, Encoder, HCursor, Json}

import scala.reflect.ClassTag

object EnumeratedCodec {

  def apply[T : ClassTag, A : Encoder : Decoder : ClassTag](mappings: (T, A)*): Codec[T] =
    new Codec[T] {
      private val unwrap: T => A = mappings.toMap // TODO this is unsafe
      private val wrap: Map[A, T] = mappings.map {
        case (t, a) => (a, t)
      }.toMap // TODO this is unsafe

      private val tDecoder: Decoder[T] = Decoder[A].emap { a =>
        wrap.get(a).toRight {
          val targetTypeName = implicitly[ClassTag[T]].runtimeClass.getCanonicalName

          s"Could not construct $targetTypeName from $a"
        }
      }

      override def apply(t: T): Json            = Encoder[A].apply(unwrap(t))
      override def apply(c: HCursor): Result[T] = tDecoder.apply(c)
    }

}
