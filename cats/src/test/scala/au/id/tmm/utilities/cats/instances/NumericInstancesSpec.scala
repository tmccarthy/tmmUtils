package au.id.tmm.utilities.cats.instances

import au.id.tmm.utilities.cats.instances.NumericInstancesSpec._
import au.id.tmm.utilities.cats.instances.numeric._
import au.id.tmm.utilities.testing.Wrapped
import au.id.tmm.utilities.testing.scalacheck.instances.wrapped._
import cats.kernel.Eq
import cats.laws.discipline.InvariantTests
import cats.syntax.invariant._
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest
import org.scalatest.flatspec.AnyFlatSpec
import org.typelevel.discipline.scalatest.FlatSpecDiscipline

class NumericInstancesSpec extends AnyFlatSpec with FlatSpecDiscipline with scalatest.prop.Configuration {

  "the numeric instance" should "be resolved correctly" in {
    val _: Numeric[Wrapped[Int]] = Numeric[Int].imap(Wrapped.apply)(_.unwrap)
  }

  "the integral instance" should "be resolved correctly" in {
    val _: Integral[Wrapped[Int]] = Integral[Int].imap(Wrapped.apply)(_.unwrap)
  }

  "the fractional instance" should "be resolved correctly" in {
    val _: Fractional[Wrapped[Float]] = Fractional[Float].imap(Wrapped.apply)(_.unwrap)
  }

  checkAll("Numeric invariant", InvariantTests[Numeric].invariant[Wrapped[Int], Boolean, Boolean])
  checkAll("Integral invariant", InvariantTests[Integral].invariant[Wrapped[Int], Boolean, Boolean])
  checkAll("Fractional invariant", InvariantTests[Fractional].invariant[Wrapped[Float], Boolean, Boolean])

}

object NumericInstancesSpec {
  private implicit def arbitraryNumeric[A : Numeric]: Arbitrary[Numeric[Wrapped[A]]] =
    Arbitrary(Gen.const(Numeric[A].imap(Wrapped.apply[A])(_.unwrap)))

  private implicit def numericEq[A]: Eq[Numeric[A]] = Eq.fromUniversalEquals

  private implicit def arbitraryIntegral[A : Integral]: Arbitrary[Integral[Wrapped[A]]] =
    Arbitrary(Gen.const(Integral[A].imap(Wrapped.apply[A])(_.unwrap)))

  private implicit def integralEq[A]: Eq[Integral[A]] = Eq.fromUniversalEquals

  private implicit def arbitraryFractional[A : Fractional]: Arbitrary[Fractional[Wrapped[A]]] =
    Arbitrary(Gen.const(Fractional[A].imap(Wrapped.apply[A])(_.unwrap)))

  private implicit def fractionalEq[A]: Eq[Fractional[A]] = Eq.fromUniversalEquals

}
