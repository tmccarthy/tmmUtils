package au.id.tmm.utilities.collection.circe

import au.id.tmm.utilities.collection.NonEmptyDupelessSeq
import io.circe.{Decoder, Encoder}

import scala.collection.immutable.ArraySeq

trait NonEmptyDupelessSeqCodecs {

  import ArraySeqCodecs._

  implicit def nonEmptyDupelessSeqEncoder[A: Encoder]: Encoder[NonEmptyDupelessSeq[A]] =
    Encoder[ArraySeq[A]].contramap(_.toArraySeq)

  implicit def nonEmptyDupelessSeqDecoder[A: Decoder]: Decoder[NonEmptyDupelessSeq[A]] =
    Decoder[ArraySeq[A]].emap { array =>
      NonEmptyDupelessSeq.fromIterable(array).toRight("Empty array cannot be decoded to NonEmptyDupelessSeq")
    }

}
