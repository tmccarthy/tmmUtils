package au.id.tmm.utilities.circe

import io.circe.Decoder.Result
import io.circe._

import scala.collection.mutable
import scala.reflect.ClassTag

object EnumeratedCodec {

  /**
    * Produces a codec which is a mapping between an enumerated set of values of type `T` and a type `A` for which
    * a codec can be derived.
    *
    * Note that this is an inherently unsafe codec. This constructor will throw if there are duplicate values of type `T`
    * or type `A`. More significantly, it will throw while encoding a value of type `T` for which no mapping is defined.
    * `Encoder` should generally not throw, so use this with caution!
    *
    * @throws java.lang.IllegalArgumentException if there are duplicate mapping keys or values
    */
  def apply[T : ClassTag, A : Encoder : Decoder : ClassTag](
    mappings: (T, A)*,
  ): Codec[T] = {
    val encodeLookup: mutable.Map[T, A] = mutable.Map()
    val decodeLookup: mutable.Map[A, T] = mutable.Map()

    mappings.foreach {
      case (t, a) => {
        if (encodeLookup.contains(t)) {
          throw new IllegalArgumentException(s"Duplicate enumerated value $t")
        } else if (decodeLookup.contains(a)) {
          throw new IllegalArgumentException(s"Duplicate encoded value $a")
        } else {
          encodeLookup.addOne(t -> a)
          decodeLookup.addOne(a -> t)
        }
      }
    }

    new Codec[T] {
      private val tDecoder: Decoder[T] = Decoder[A].emap { a =>
        decodeLookup.get(a).toRight {
          val targetTypeName = implicitly[ClassTag[T]].runtimeClass.getCanonicalName

          s"Could not construct $targetTypeName from $a"
        }
      }

      override def apply(t: T): Json =
        Encoder[A].apply(
          encodeLookup
            .getOrElse(t, throw new IllegalStateException(s"Enumerated codec does not have an encoding for value $t")),
        )

      override def apply(c: HCursor): Result[T] = tDecoder.apply(c)
    }
  }

}
