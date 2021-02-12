package au.id.tmm.utilities.testing

import org.scalacheck.{Arbitrary, Gen}
import org.scalactic.source.Position
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import au.id.tmm.utilities.testing.MiniFloatSpec.arbMiniFloat

class MiniFloatSpec extends AnyFunSuite with ScalaCheckDrivenPropertyChecks {

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

}

object MiniFloatSpec {
  implicit val arbMiniFloat: Arbitrary[MiniFloat] = Arbitrary(Gen.oneOf(MiniFloat.allValues))
}