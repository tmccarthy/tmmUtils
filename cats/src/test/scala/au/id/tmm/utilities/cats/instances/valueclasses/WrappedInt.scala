package au.id.tmm.utilities.cats.instances.valueclasses

import cats.kernel.Eq
import org.scalacheck.{Arbitrary, Cogen}

final case class WrappedInt(asInt: Int) extends AnyVal

object WrappedInt {
  implicit val arbitrary: Arbitrary[WrappedInt] = Arbitrary {
    Arbitrary.arbitrary[Int].map(WrappedInt.apply)
  }

  implicit val cogen: Cogen[WrappedInt] = Cogen[Int].contramap(_.asInt)

  implicit val eq: Eq[WrappedInt] = Eq.fromUniversalEquals
}
