package au.id.tmm.utilities.testing

import scala.collection.immutable.ArraySeq

/**
  * A floating-point number with a range of values small enough to make it efficient to test every value.
  *
  * `MiniFloat` can have one of the following 14 values:
  * <ul>
  *  <li>`NegativeInfinity`</li>
  *  <li>`-8.0`</li>
  *  <li>`-4.0`</li>
  *  <li>`-2.0`</li>
  *  <li>`-1.0`</li>
  *  <li>`-0.5`</li>
  *  <li>`0.0`</li>
  *  <li>`0.5`</li>
  *  <li>`1.0`</li>
  *  <li>`2.0`</li>
  *  <li>`4.0`</li>
  *  <li>`8.0`</li>
  *  <li>`PositiveInfinity`</li>
  *  <li>`NaN`</li>
  * </ul>
  *
  * In almost all respects (overflows, value approximation, floating point errors) it behaves similarly to `Float` and
  * `Double`. The main difference is that `MiniFloat` does not support the `-0.0` value, which is represented as `0.0`.
  */
sealed abstract class MiniFloat private (val toFloat: Float) {

  def toDouble: Double = toFloat.toDouble
  def toInt: Int       = toFloat.toInt
  def toLong: Long     = toFloat.toLong

  def +(that: MiniFloat): MiniFloat = MiniFloat.from(this.toFloat + that.toFloat)
  def -(that: MiniFloat): MiniFloat = MiniFloat.from(this.toFloat - that.toFloat)
  def *(that: MiniFloat): MiniFloat = MiniFloat.from(this.toFloat * that.toFloat)
  def /(that: MiniFloat): MiniFloat = MiniFloat.from(this.toFloat / that.toFloat)
  def unary_- : MiniFloat           = MiniFloat.from(-this.toFloat)

  def isNaN: Boolean    = toFloat.isNaN
  def isFinite: Boolean = toFloat.isFinite

  override def toString = s"MiniFloat($toFloat)"

  override def equals(other: Any): Boolean = other match {
    case that: MiniFloat => this.toFloat == that.toFloat
    case _               => false
  }

  override def hashCode: Int = java.lang.Float.hashCode(toFloat)
}

object MiniFloat {

  object PositiveInfinity extends MiniFloat(Float.PositiveInfinity)
  object NegativeInfinity extends MiniFloat(Float.NegativeInfinity)
  object NaN              extends MiniFloat(Float.NaN)

  private final class Finite private (significand: Int, exponent: Int)
      extends MiniFloat(significand * math.pow(Finite.base.toDouble, exponent.toDouble).toFloat)

  private[MiniFloat] object Finite {

    private[MiniFloat] val base = 2

    private val minSignificand = -2
    private val maxSignificand = 2

    private val minExponent = -1
    private val maxExponent = 2

    val allValues: ArraySeq[Finite] = {
      for {
        significand <- Range.inclusive(minSignificand, maxSignificand)
        exponent    <- Range.inclusive(minExponent, maxExponent)
      } yield new Finite(significand, exponent)
    }.to(ArraySeq).distinct.sortBy(_.toFloat)

    private[MiniFloat] val allValuesAsFloats: ArraySeq[Float] = allValues.map(_.toFloat)

    val zero        = new Finite(0, 0)
    val max         = new Finite(maxSignificand, maxExponent)
    val min         = new Finite(minSignificand, maxExponent)
    val minPositive = new Finite(significand = 1, exponent = minExponent)

    /**
      * Returns `None` if the given float cannot fit in an instance of `Finite`.
      */
    def from(float: Float): Option[Finite] = {
      val exponent: Int    = math.getExponent(float)
      val significand: Int = math.round(float / math.pow(Finite.base.toDouble, exponent.toDouble).toFloat)

      if (significand == 0 || exponent < minExponent) {
        Some(zero)
      } else if (withinBounds(significand, exponent)) {
        Some(new Finite(significand, exponent))
      } else if (exponent > maxExponent) {
        try {
          val ordersOfMagnitudeToShift = math.subtractExact(exponent, maxExponent)

          val proposedSignificand: Int = math.multiplyExact(
            significand,
            math.pow(base.toDouble, ordersOfMagnitudeToShift.toDouble).toInt,
          )
          val proposedExponent: Int = math.subtractExact(exponent, ordersOfMagnitudeToShift)

          Option.when(withinBounds(proposedSignificand, proposedExponent)) {
            new Finite(proposedSignificand, proposedExponent)
          }
        } catch {
          case _: ArithmeticException => None
        }
      } else {
        None
      }
    }

    private def withinBounds(significand: Int, exponent: Int): Boolean =
      (minExponent <= exponent && exponent <= maxExponent) &&
        (minSignificand <= significand && significand <= maxSignificand)

  }

  val Zero: MiniFloat        = MiniFloat.from(0f)
  val NegativeOne: MiniFloat = MiniFloat.from(-1f)
  val One: MiniFloat         = MiniFloat.from(1f)

  val MaxValue: MiniFloat         = Finite.max
  val MinValue: MiniFloat         = Finite.min
  val MinPositiveValue: MiniFloat = Finite.minPositive

  def allValues: ArraySeq[MiniFloat] =
    ArraySeq(NegativeInfinity) ++
      Finite.allValues :+
      PositiveInfinity :+
      NaN

  def from(float: Float): MiniFloat =
    float match {
      case Float.PositiveInfinity => PositiveInfinity
      case Float.NegativeInfinity => NegativeInfinity
      case f if f.isNaN           => NaN
      case _ =>
        Finite
          .from(float)
          .getOrElse {
            if (float > 0) PositiveInfinity else NegativeInfinity
          }
    }

  def from(double: Double): MiniFloat = from(double.toFloat)
  def from(int: Int): MiniFloat       = from(int.toFloat)
  def from(long: Long): MiniFloat     = from(long.toFloat)

  implicit val ordering: Ordering[MiniFloat] = Orderings.IeeeOrdering

  object Orderings {
    val IeeeOrdering: Ordering[MiniFloat]  = Ordering.by[MiniFloat, Float](_.toFloat)(Ordering.Float.IeeeOrdering)
    val TotalOrdering: Ordering[MiniFloat] = Ordering.by[MiniFloat, Float](_.toFloat)(Ordering.Float.TotalOrdering)
  }

  implicit val fractional: Fractional[MiniFloat] = new Fractional[MiniFloat] {
    override def div(x: MiniFloat, y: MiniFloat): MiniFloat   = x / y
    override def plus(x: MiniFloat, y: MiniFloat): MiniFloat  = x + y
    override def minus(x: MiniFloat, y: MiniFloat): MiniFloat = x - y
    override def times(x: MiniFloat, y: MiniFloat): MiniFloat = x * y
    override def negate(x: MiniFloat): MiniFloat              = -x
    override def fromInt(x: Int): MiniFloat                   = from(x)
    override def parseString(str: String): Option[MiniFloat]  = implicitly[Fractional[Float]].parseString(str).map(from)
    override def toInt(x: MiniFloat): Int                     = x.toInt
    override def toLong(x: MiniFloat): Long                   = x.toLong
    override def toFloat(x: MiniFloat): Float                 = x.toFloat
    override def toDouble(x: MiniFloat): Double               = x.toDouble
    override def compare(x: MiniFloat, y: MiniFloat): Int     = Orderings.IeeeOrdering.compare(x, y)
  }

}
