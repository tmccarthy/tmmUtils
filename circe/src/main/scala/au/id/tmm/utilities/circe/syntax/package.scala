package au.id.tmm.utilities.circe

import io.circe.{Codec, Decoder, Encoder}

package object syntax {

  implicit class CodecCompanionOps(codecCompanion: Codec.type) {
    def instance[A : Encoder : Decoder]: Codec[A] = Codec.from(Decoder[A], Encoder[A])
  }

}
