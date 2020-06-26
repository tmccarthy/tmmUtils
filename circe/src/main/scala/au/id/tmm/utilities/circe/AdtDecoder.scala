package au.id.tmm.utilities.circe

import cats.syntax.functor.toFunctorOps
import io.circe.Decoder

object AdtDecoder {

  def decodes[A]: PartiallyAppliedAdtDecoder[A] = new PartiallyAppliedAdtDecoder[A]

  final class PartiallyAppliedAdtDecoder[A] private[AdtDecoder] () {

    def forSum1[A1 <: A](implicit decoder1: Decoder[A1]): Decoder[A] =
      decoder1.widen[A]

    def forSum2[A1 <: A, A2 <: A](implicit decoder1: Decoder[A1], decoder2: Decoder[A2]): Decoder[A] =
      decoder1.widen[A] or decoder2.widen[A]

    def forSum3[A1 <: A, A2 <: A, A3 <: A](
      implicit
      decoder1: Decoder[A1],
      decoder2: Decoder[A2],
      decoder3: Decoder[A3],
    ): Decoder[A] =
      decoder1.widen[A] or
        decoder2.widen[A] or
        decoder3.widen[A]

    def forSum4[A1 <: A, A2 <: A, A3 <: A, A4 <: A](
      implicit
      decoder1: Decoder[A1],
      decoder2: Decoder[A2],
      decoder3: Decoder[A3],
      decoder4: Decoder[A4],
    ): Decoder[A] =
      decoder1.widen[A] or
        decoder2.widen[A] or
        decoder3.widen[A] or
        decoder4.widen[A]

    def forSum5[A1 <: A, A2 <: A, A3 <: A, A4 <: A, A5 <: A](
      implicit
      decoder1: Decoder[A1],
      decoder2: Decoder[A2],
      decoder3: Decoder[A3],
      decoder4: Decoder[A4],
      decoder5: Decoder[A5],
    ): Decoder[A] =
      decoder1.widen[A] or
        decoder2.widen[A] or
        decoder3.widen[A] or
        decoder4.widen[A] or
        decoder5.widen[A]

    def forSum6[A1 <: A, A2 <: A, A3 <: A, A4 <: A, A5 <: A, A6 <: A](
      implicit
      decoder1: Decoder[A1],
      decoder2: Decoder[A2],
      decoder3: Decoder[A3],
      decoder4: Decoder[A4],
      decoder5: Decoder[A5],
      decoder6: Decoder[A6],
    ): Decoder[A] =
      decoder1.widen[A] or
        decoder2.widen[A] or
        decoder3.widen[A] or
        decoder4.widen[A] or
        decoder5.widen[A] or
        decoder6.widen[A]

    def forSum7[A1 <: A, A2 <: A, A3 <: A, A4 <: A, A5 <: A, A6 <: A, A7 <: A](
      implicit
      decoder1: Decoder[A1],
      decoder2: Decoder[A2],
      decoder3: Decoder[A3],
      decoder4: Decoder[A4],
      decoder5: Decoder[A5],
      decoder6: Decoder[A6],
      decoder7: Decoder[A7],
    ): Decoder[A] =
      decoder1.widen[A] or
        decoder2.widen[A] or
        decoder3.widen[A] or
        decoder4.widen[A] or
        decoder5.widen[A] or
        decoder6.widen[A] or
        decoder7.widen[A]

    def forSum8[A1 <: A, A2 <: A, A3 <: A, A4 <: A, A5 <: A, A6 <: A, A7 <: A, A8 <: A](
      implicit
      decoder1: Decoder[A1],
      decoder2: Decoder[A2],
      decoder3: Decoder[A3],
      decoder4: Decoder[A4],
      decoder5: Decoder[A5],
      decoder6: Decoder[A6],
      decoder7: Decoder[A7],
      decoder8: Decoder[A8],
    ): Decoder[A] =
      decoder1.widen[A] or
        decoder2.widen[A] or
        decoder3.widen[A] or
        decoder4.widen[A] or
        decoder5.widen[A] or
        decoder6.widen[A] or
        decoder7.widen[A] or
        decoder8.widen[A]

    def forSum9[A1 <: A, A2 <: A, A3 <: A, A4 <: A, A5 <: A, A6 <: A, A7 <: A, A8 <: A, A9 <: A](
      implicit
      decoder1: Decoder[A1],
      decoder2: Decoder[A2],
      decoder3: Decoder[A3],
      decoder4: Decoder[A4],
      decoder5: Decoder[A5],
      decoder6: Decoder[A6],
      decoder7: Decoder[A7],
      decoder8: Decoder[A8],
      decoder9: Decoder[A9],
    ): Decoder[A] =
      decoder1.widen[A] or
        decoder2.widen[A] or
        decoder3.widen[A] or
        decoder4.widen[A] or
        decoder5.widen[A] or
        decoder6.widen[A] or
        decoder7.widen[A] or
        decoder8.widen[A] or
        decoder9.widen[A]

    def forSum10[A1 <: A, A2 <: A, A3 <: A, A4 <: A, A5 <: A, A6 <: A, A7 <: A, A8 <: A, A9 <: A, A10 <: A](
      implicit
      decoder1: Decoder[A1],
      decoder2: Decoder[A2],
      decoder3: Decoder[A3],
      decoder4: Decoder[A4],
      decoder5: Decoder[A5],
      decoder6: Decoder[A6],
      decoder7: Decoder[A7],
      decoder8: Decoder[A8],
      decoder9: Decoder[A9],
      decoder10: Decoder[A10],
    ): Decoder[A] =
      decoder1.widen[A] or
        decoder2.widen[A] or
        decoder3.widen[A] or
        decoder4.widen[A] or
        decoder5.widen[A] or
        decoder6.widen[A] or
        decoder7.widen[A] or
        decoder8.widen[A] or
        decoder9.widen[A] or
        decoder10.widen[A]

  }

}
