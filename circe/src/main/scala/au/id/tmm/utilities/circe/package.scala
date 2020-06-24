package au.id.tmm.utilities

import io.circe.{Codec, Decoder, Encoder}

package object circe {

  implicit class CodecCompanionOps(codecCompanion: Codec.type) {
    def instance[A : Encoder : Decoder]: Codec[A] = Codec.from(Decoder[A], Encoder[A])
  }

}
