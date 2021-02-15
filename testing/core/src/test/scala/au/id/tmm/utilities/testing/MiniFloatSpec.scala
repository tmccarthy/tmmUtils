package au.id.tmm.utilities.testing

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

  private def testAllValuesContains(value: MiniFloat)(implicit pos: Position): Unit =
    test(s"allValues contains $value") {
      MiniFloat.allValues.contains(value)
    }

  testAllValuesContains(MiniFloat.Zero)
  testAllValuesContains(MiniFloat.NegativeOne)
  testAllValuesContains(MiniFloat.One)
  testAllValuesContains(MiniFloat.MaxValue)
  testAllValuesContains(MiniFloat.MinValue)
  testAllValuesContains(MiniFloat.MinPositiveValue)
  testAllValuesContains(MiniFloat.PositiveInfinity)
  testAllValuesContains(MiniFloat.NegativeInfinity)

  test("allValues contains NaN") {
    assert(MiniFloat.allValues.count(_.isNaN) === 1, "has exactly one NaN value in allValues")
  }

  test("allValues has max") {
    val maxInAllValues = MiniFloat.allValues.filter(_.isFinite).maxBy(_.toFloat)

    assert(maxInAllValues === MiniFloat.MaxValue)
  }

  test("allValues has min") {
    val minInAllValues = MiniFloat.allValues.filter(_.isFinite).minBy(_.toFloat)

    assert(minInAllValues === MiniFloat.MinValue)
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
        val expectedAsFloat: Float = op.forFloat(left.toFloat, right.toFloat)

        if (expectedAsFloat.isNaN) {
          assert(op.forMiniFloat(left, right).isNaN)
        } else {
          assert(op.forMiniFloat(left, right).toFloat === expectedAsFloat)
        }
    }
  }

  // Float conversions

  private def testFromFloat(float: Float, expected: MiniFloat)(implicit pos: Position): Unit =
    test(s"MiniFloat.fromFloat($float) === $expected") {
      assert(MiniFloat.from(float) === expected)
    }

  testFromFloat(1f, MiniFloat.One)
  testFromFloat(0f, MiniFloat.Zero)
  testFromFloat(-1f, MiniFloat.NegativeOne)

  testFromFloat(Float.MinPositiveValue, MiniFloat.Zero)
  testFromFloat(Float.MaxValue, MiniFloat.PositiveInfinity)
  testFromFloat(Float.MinValue, MiniFloat.NegativeInfinity)

  testFromFloat(Float.PositiveInfinity, MiniFloat.PositiveInfinity)
  testFromFloat(Float.NegativeInfinity, MiniFloat.NegativeInfinity)

  test("MiniFloat.fromFloat(NaN) isNaN") {
    assert(MiniFloat.from(Float.NaN).isNaN)
  }

  private def testFloatNarrowing(float: Float, expected: Float)(implicit pos: Position): Unit =
    test(s"narrows $float to $expected") {
      assert(MiniFloat.from(float).toFloat === expected)
    }

  testFloatNarrowing(0f, 0f)
  testFloatNarrowing(0.5f, 0.5f)
  testFloatNarrowing(1f, 1f)
  testFloatNarrowing(2f, 2f)
  testFloatNarrowing(3f, 2f)
  testFloatNarrowing(4f, 4f)
  testFloatNarrowing(5f, 4f)
  testFloatNarrowing(6f, 4f)
  testFloatNarrowing(7f, 8f)
  testFloatNarrowing(8f, 8f)

  // TODO document decision to not include -0
  test("MiniFloat.fromFloat(-0) is not negative") {
    assert(!(MiniFloat.from(-0).toFloat < 0))
  }

  test(s"fromDouble consistent with fromFloat") {
    forAll { (n: Double) =>
      if (n.isNaN) {
        assert(MiniFloat.from(n).isNaN)
      } else {
        assert(MiniFloat.from(n) === MiniFloat.from(n.toFloat), n)
      }
    }
  }

  test(s"toDouble consistent with toFloat") {
    forAll { (mf: MiniFloat) =>
      val fromToDouble = mf.toDouble.toFloat
      val fromToFloat  = mf.toFloat

      if (fromToDouble.isNaN) {
        assert(fromToFloat.isNaN)
      } else {
        assert(fromToDouble === fromToFloat, mf)
      }
    }
  }

  // Special number tests and behaviours

  private def testSpecialNumberExpectations(
    mf: MiniFloat,
    expectIsNaN: Boolean,
    expectIsFinite: Boolean,
  )(implicit
    pos: Position,
  ): Unit = {
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
  testSpecialNumberExpectations(MiniFloat.MaxValue, expectIsNaN = false, expectIsFinite = true)
  testSpecialNumberExpectations(MiniFloat.MinValue, expectIsNaN = false, expectIsFinite = true)
  testSpecialNumberExpectations(MiniFloat.MinPositiveValue, expectIsNaN = false, expectIsFinite = true)

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

  test("negate ∞ is -∞") {
    assert(-MiniFloat.PositiveInfinity === MiniFloat.NegativeInfinity)
  }

  test("negate -∞ is ∞") {
    assert(-MiniFloat.NegativeInfinity === MiniFloat.PositiveInfinity)
  }

  test("negate NaN is NaN") {
    assert((-MiniFloat.NaN).isNaN)
  }

  test("negation inverse") {
    forAll { mf: MiniFloat =>
      assert((-(-mf) === mf) || mf.isNaN)
    }
  }

  // Addition

  test("add commutative") {
    forAll { (left: MiniFloat, right: MiniFloat) =>
      val forward  = left + right
      val backward = right + left

      if (forward.isNaN) {
        assert(backward.isNaN)
      } else {
        assert(forward === backward)
      }
    }
  }

  test("zero addition identity") {
    forAll { mf: MiniFloat =>
      if (mf.isNaN) {
        assert((mf + MiniFloat.Zero).isNaN)
      } else {
        assert(mf + MiniFloat.Zero === mf)
      }
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
    assert((MiniFloat.PositiveInfinity + MiniFloat.NegativeInfinity).isNaN)
  }

  test("NaN addition") {
    forAll { mf: MiniFloat =>
      assert((mf + MiniFloat.NaN).isNaN)
    }
  }

  // Subtraction

  test("subtract consistent with addition") {
    forAll { (left: MiniFloat, right: MiniFloat) =>
      assert(left.isNaN || right.isNaN || (left + (-right) === (left - right)))
    }
  }

  test("NaN subtraction") {
    forAll { mf: MiniFloat =>
      assert((mf - MiniFloat.NaN).isNaN)
    }
  }

  // Multiplication

  test("multiplication commutative") {
    forAll { (left: MiniFloat, right: MiniFloat) =>
      val forward  = left * right
      val backward = right * left

      if (forward.isNaN) {
        assert(backward.isNaN)
      } else {
        assert(forward === backward)
      }
    }
  }

  test("one multiplicative identity") {
    forAll { mf: MiniFloat =>
      if (mf.isNaN) {
        assert((mf * MiniFloat.One).isNaN)
      } else {
        assert(mf * MiniFloat.One === mf)
      }
    }
  }

  test("max * 1") {
    assert(MiniFloat.MaxValue * MiniFloat.One === MiniFloat.MaxValue)
  }

  test("max * max") {
    assert(MiniFloat.MaxValue * MiniFloat.MaxValue === MiniFloat.PositiveInfinity)
  }

  test("∞ * ∞") {
    assert(MiniFloat.PositiveInfinity * MiniFloat.PositiveInfinity === MiniFloat.PositiveInfinity)
  }

  test("∞ * 1") {
    assert(MiniFloat.PositiveInfinity * MiniFloat.One === MiniFloat.PositiveInfinity)
  }

  test("∞ * (-∞)") {
    assert(MiniFloat.PositiveInfinity * MiniFloat.NegativeInfinity === MiniFloat.NegativeInfinity)
  }

  test("NaN multiplication") {
    forAll { mf: MiniFloat =>
      assert((mf * MiniFloat.NaN).isNaN)
    }
  }

  // Division

  test("divide by zero") {
    forAll { mf: MiniFloat =>
      val result = mf / MiniFloat.Zero

      if (mf.isNaN || mf === MiniFloat.Zero) {
        assert(result.isNaN)
      } else if (mf.toFloat < 0f) {
        assert(result === MiniFloat.NegativeInfinity)
      } else {
        assert(result === MiniFloat.PositiveInfinity)
      }
    }
  }

  test("division consistent with float division") {
    forAll { (left: MiniFloat, right: MiniFloat) =>
      val result = left / right

      if (result.isNaN) {
        assert(MiniFloat.from(left.toFloat / right.toFloat).isNaN)
      } else {
        assert(result === MiniFloat.from(left.toFloat / right.toFloat))
      }
    }
  }

}

object MiniFloatSpec {
  implicit val arbMiniFloat: Arbitrary[MiniFloat] = Arbitrary(Gen.oneOf(MiniFloat.allValues))

  private[testing] sealed abstract class FloatArithmeticOp(
    val char: Char,
    val forMiniFloat: (MiniFloat, MiniFloat) => MiniFloat,
    val forFloat: (Float, Float) => Float,
  ) {}

  private[testing] object FloatArithmeticOp {
    case object Addition       extends FloatArithmeticOp('+', _ + _, _ + _)
    case object Multiplication extends FloatArithmeticOp('*', _ * _, _ * _)
    case object Subtraction    extends FloatArithmeticOp('-', _ - _, _ - _)
    case object Division       extends FloatArithmeticOp('/', _ / _, _ / _)

    implicit val arb: Arbitrary[FloatArithmeticOp] =
      Arbitrary(Gen.oneOf(Addition, Multiplication, Subtraction, Division))
  }
}
