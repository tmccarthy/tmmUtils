package au.id.tmm.utilities.cats.instances.valueclasses

import cats.kernel.Eq
import org.scalacheck.Arbitrary

final case class WrappedList[A](list: List[A])

object WrappedList {
  implicit def arbitrary[A : Arbitrary]: Arbitrary[WrappedList[A]] =
    Arbitrary(Arbitrary.arbitrary[List[A]].map(WrappedList.apply))

  implicit def eq[A : Eq]: Eq[WrappedList[A]] = (x, y) => Eq[List[A]].eqv(x.list, y.list)
}
