package au.id.tmm.utilities.testing

import scala.collection.immutable.ArraySeq

// TODO should have -0.0 also
// (-8.0, -4.0, -2.0, -1.0, -0.5, 0.0, 0.5, 1.0, 2.0, 4.0, 8.0)
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
      extends MiniFloat(significand * math.pow(Finite.base, exponent).toFloat)

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
      * If the given exponent and significand fit within the limits, return the resulting `Finite`. Otherwise, attempt
      * to construct an equivalent `Finite` by increasing the significand at the expense of the exponent. If this isn't
      * possible (i.e. there is no way of expressing the given value within the bounds), returns `None`.
      */
    def ifCanFit(significand: Int, exponent: Int): Option[Finite] =
      if (significand == 0) {
        Some(zero)
      } else if (withinBounds(significand, exponent)) {
        Some(new Finite(significand, exponent))
      } else if (exponent > maxExponent) {
        val proposedSignificand: Int = significand * base
        val proposedExponent: Int    = exponent - 1

        Option.when(withinBounds(proposedSignificand, proposedExponent)) {
          new Finite(proposedSignificand, proposedExponent)
        }
      } else if (exponent < minExponent) {
        Some(zero)
      } else {
        None
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
      case _ => {
        val exponent: Int    = math.getExponent(float)
        val significand: Int = math.round(float / math.pow(Finite.base, exponent).toFloat)

        Finite
          .ifCanFit(
            significand,
            exponent,
          )
          .getOrElse {
            if (significand >= 0) PositiveInfinity else NegativeInfinity
          }
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
}
