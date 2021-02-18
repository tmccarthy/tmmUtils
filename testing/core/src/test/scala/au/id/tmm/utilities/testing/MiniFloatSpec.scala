package au.id.tmm.utilities.testing

import au.id.tmm.utilities.testing.MiniFloatSpec.{FloatArithmeticOp, arbMiniFloat}
import org.scalacheck.{Arbitrary, Gen}
import org.scalactic.source.Position
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

import scala.collection.MapView
import scala.collection.immutable.ArraySeq

class MiniFloatSpec extends AnyFunSuite with ScalaCheckDrivenPropertyChecks {

  override implicit val generatorDrivenConfig: PropertyCheckConfiguration =
    PropertyCheckConfiguration(minSuccessful = 5000)

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

  private def testFloatConversion(float: Float, expected: MiniFloat)(implicit pos: Position): Unit =
    test(s"MiniFloat.fromFloat($float) === $expected") {
      val miniFloat = MiniFloat.from(float)
      if (miniFloat.isNaN) {
        assert(expected.isNaN)
      } else {
        assert(miniFloat === expected)
      }
    }

  private def testFloatConversion(float: Float, expected: Float)(implicit pos: Position): Unit =
    test(s"MiniFloat.fromFloat($float) === $expected") {
      val miniFloat = MiniFloat.from(float)
      if (miniFloat.isNaN) {
        assert(expected.isNaN)
      } else {
        assert(miniFloat.toFloat === expected)
      }
    }

  testFloatConversion(Float.NegativeInfinity, MiniFloat.NegativeInfinity)
  testFloatConversion(Float.MinValue, MiniFloat.NegativeInfinity)
  testFloatConversion(-16f, MiniFloat.NegativeInfinity)
  testFloatConversion(math.nextDown(-12f), MiniFloat.NegativeInfinity)
  testFloatConversion(-12f, -8f)
  testFloatConversion(math.nextDown(-6f), -8f)
  testFloatConversion(-6f, -4f)
  testFloatConversion(math.nextDown(-3f), -4f)
  testFloatConversion(-3f, -2f)
  testFloatConversion(-1f, MiniFloat.NegativeOne)
  testFloatConversion(1f, MiniFloat.One)
  testFloatConversion(0f, MiniFloat.Zero)
  testFloatConversion(Float.MinPositiveValue, MiniFloat.Zero)
  testFloatConversion(-0f, MiniFloat.Zero)
  testFloatConversion(math.nextDown(0.125f), MiniFloat.Zero)
  testFloatConversion(0.125f, MiniFloat.Zero)
  testFloatConversion(math.nextDown(0.25f), MiniFloat.Zero)
  testFloatConversion(0.25f, MiniFloat.Zero)
  testFloatConversion(0.5f, 0.5f)
  testFloatConversion(1f, 1f)
  testFloatConversion(2f, 2f)
  testFloatConversion(math.nextDown(3f), 2f)
  testFloatConversion(3f, 4f)
  testFloatConversion(4f, 4f)
  testFloatConversion(5f, 4f)
  testFloatConversion(6f, 8f)
  testFloatConversion(7f, 8f)
  testFloatConversion(8f, 8f)
  testFloatConversion(math.nextDown(12f), 8f)
  testFloatConversion(12f, MiniFloat.PositiveInfinity)
  testFloatConversion(16f, MiniFloat.PositiveInfinity)
  testFloatConversion(Float.MaxValue, MiniFloat.PositiveInfinity)
  testFloatConversion(Float.PositiveInfinity, MiniFloat.PositiveInfinity)
  testFloatConversion(Float.NaN, MiniFloat.NaN)

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
      val additionOfInverse = left + (-right)
      val subtraction       = left - right

      if (additionOfInverse.isNaN) {
        assert(subtraction.isNaN)
      } else {
        assert(additionOfInverse === subtraction)
      }
    }
  }

  test("NaN subtraction") {
    forAll { mf: MiniFloat =>
      assert((mf - MiniFloat.NaN).isNaN)
    }
  }

  test("∞ - ∞") {
    assert((MiniFloat.PositiveInfinity - MiniFloat.PositiveInfinity).isNaN)
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

  // Ordering

  test("ordering consistent with float") {
    forAll { (left: MiniFloat, right: MiniFloat) =>
      assert(
        implicitly[Ordering[MiniFloat]].compare(left, right) ===
          implicitly[Ordering[Float]].compare(left.toFloat, right.toFloat),
      )
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
