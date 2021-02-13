package au.id.tmm.utilities.testing

import au.id.tmm.utilities.testing.MiniFloatSpec.FloatArithmeticOp.{Addition, Division, Multiplication, Subtraction}
import au.id.tmm.utilities.testing.MiniFloatSpec.{FloatArithmeticOp, arbMiniFloat}
import org.scalacheck.{Arbitrary, Gen}
import org.scalactic.source.Position
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

import scala.collection.MapView
import scala.collection.immutable.ArraySeq

class MiniFloatSpec extends AnyFunSuite with ScalaCheckDrivenPropertyChecks {

  // checks on allValues

  test("allValues contains no duplicates") {
    val occurrencesPerFloatValue: MapView[Float, ArraySeq[MiniFloat]] = MiniFloat.allValues
      .groupBy(_.toFloat)
      .view

    val duplicates: ArraySeq[(Float, ArraySeq[MiniFloat])] = occurrencesPerFloatValue.to(ArraySeq).filter(_._2.size > 1)

    assert(duplicates === ArraySeq.empty, "Minifloats with duplicate values")
  }

  test("behaves the same as Float for all operations on some key values") {
    val genKeyMiniFloatValues: Gen[MiniFloat] = Gen.oneOf(
      MiniFloat.NegativeInfinity,
      MiniFloat.NegativeOne,
      MiniFloat.Zero,
      MiniFloat.One,
      MiniFloat.PositiveInfinity,
      MiniFloat.NaN,
    )

    forAll(FloatArithmeticOp.arb.arbitrary, genKeyMiniFloatValues, genKeyMiniFloatValues) {
      (op: FloatArithmeticOp, left: MiniFloat, right: MiniFloat) =>
        assert(op.forMiniFloat(left, right).toFloat === op.forFloat(left.toFloat, right.toFloat))
    }
  }

  // Float conversions

  private def testFloatConversion(float: Float, expected: MiniFloat)(implicit pos: Position): Unit =
    test(s"MiniFloat.fromFloat($float) === $expected") {
      assert(MiniFloat.from(float) === expected)
    }

  testFloatConversion(1f, MiniFloat.One)
  testFloatConversion(0f, MiniFloat.Zero)
  testFloatConversion(-1f, MiniFloat.NegativeOne)
  testFloatConversion(Float.MinPositiveValue, MiniFloat.Zero)
  testFloatConversion(Float.MaxValue, MiniFloat.PositiveInfinity)
  testFloatConversion(Float.MinValue, MiniFloat.NegativeInfinity)
  testFloatConversion(Float.PositiveInfinity, MiniFloat.PositiveInfinity)
  testFloatConversion(Float.NegativeInfinity, MiniFloat.NegativeInfinity)
  testFloatConversion(Float.NaN, MiniFloat.NaN)

  // Special number tests and behaviours

  private def testSpecialNumberExpectations(
    mf: MiniFloat,
    expectIsNaN: Boolean,
    expectIsFinite: Boolean,
  )(implicit pos: Position): Unit = {
    test(s"$mf isNan $expectIsNaN") {
      assert(mf.isNaN === expectIsNaN)
    }

    test(s"$mf isFinite $expectIsFinite") {
      assert(mf.isFinite === expectIsFinite)
    }
  }

  testSpecialNumberExpectations(MiniFloat.NaN, expectIsNaN = true, expectIsFinite = false)
  testSpecialNumberExpectations(MiniFloat.PositiveInfinity, expectIsNaN = false, expectIsFinite = false)
  testSpecialNumberExpectations(MiniFloat.NegativeInfinity, expectIsNaN = false, expectIsFinite = false)
  testSpecialNumberExpectations(MiniFloat.Zero, expectIsNaN = false, expectIsFinite = true)
  testSpecialNumberExpectations(MiniFloat.NegativeInfinity, expectIsNaN = false, expectIsFinite = true)
  testSpecialNumberExpectations(MiniFloat.PositiveInfinity, expectIsNaN = false, expectIsFinite = true)

  test("NaN != all MiniFloat") {
    forAll { mf: MiniFloat =>
      mf != MiniFloat.NaN
    }
  }

  // Negation

  test("negate zero is zero") {
    assert(-MiniFloat.Zero === MiniFloat.Zero)
  }

  test("negate one is one") {
    assert(-MiniFloat.One === MiniFloat.NegativeOne)
  }

  test("negate infinity is negative infinity") {
    assert(-MiniFloat.PositiveInfinity === MiniFloat.NegativeInfinity)
  }

  test("negate infinity is negative infinity") {
    assert(-MiniFloat.PositiveInfinity === MiniFloat.NegativeInfinity)
  }

  test("negate NaN is NaN") {
    assert(-MiniFloat.NaN === MiniFloat.NaN)
  }

  test("negation inverse") {
    forAll { mf: MiniFloat =>
      assert(-(-mf) === mf)
    }
  }

  // Addition

  test("add commutative") {
    forAll { (left: MiniFloat, right: MiniFloat) =>
      assert((left + right) === (right + left))
    }
  }

  test("zero addition identity") {
    forAll { mf: MiniFloat =>
      assert(mf + MiniFloat.Zero === mf)
    }
  }

  test("max plus 1") {
    assert(MiniFloat.MaxValue + MiniFloat.One === MiniFloat.MaxValue)
  }

  test("max plus max") {
    assert(MiniFloat.MaxValue + MiniFloat.MaxValue === MiniFloat.PositiveInfinity)
  }

  test("∞ + ∞") {
    assert(MiniFloat.PositiveInfinity + MiniFloat.PositiveInfinity === MiniFloat.PositiveInfinity)
  }

  test("∞ + 1") {
    assert(MiniFloat.PositiveInfinity + MiniFloat.One === MiniFloat.PositiveInfinity)
  }

  test("∞ + (-∞)") {
    assert(MiniFloat.PositiveInfinity + MiniFloat.One === MiniFloat.NaN)
  }

  test("NaN addition") {
    forAll { mf: MiniFloat =>
      assert(mf + MiniFloat.NaN === MiniFloat.NaN)
    }
  }

  // TODO multiplication tests
  // TODO subtraction tests
  // TODO division tests


}

object MiniFloatSpec {
  implicit val arbMiniFloat: Arbitrary[MiniFloat] = Arbitrary(Gen.oneOf(MiniFloat.allValues))

  private[testing] sealed trait FloatArithmeticOp {
    def forMiniFloat(left: MiniFloat, right: MiniFloat): MiniFloat =
      this match {
        case Addition => left + right
        case Multiplication => left * right
        case Subtraction => left - right
        case Division => left / right
      }

    def forFloat(left: Float, right: Float): Float =
      this match {
        case Addition => left + right
        case Multiplication => left * right
        case Subtraction => left - right
        case Division => left / right
      }
  }

  private[testing] object FloatArithmeticOp {
    case object Addition extends FloatArithmeticOp
    case object Multiplication extends FloatArithmeticOp
    case object Subtraction extends FloatArithmeticOp
    case object Division extends FloatArithmeticOp

    implicit val arb: Arbitrary[FloatArithmeticOp] =
      Arbitrary(Gen.oneOf(Addition, Multiplication, Subtraction, Division))
  }
}