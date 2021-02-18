package au.id.tmm.utilities.testing

import scala.collection.Searching
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

  private final class Finite(significand: Int, exponent: Int)
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

    val max         = new Finite(maxSignificand, maxExponent)
    val min         = new Finite(minSignificand, maxExponent)
    val minPositive = new Finite(significand = 1, exponent = minExponent)

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

  // TODO this is now very ugly
  def from(float: Float): MiniFloat =
    float match {
      case Float.PositiveInfinity => PositiveInfinity
      case Float.NegativeInfinity => NegativeInfinity
      case f if f.isNaN           => NaN
      case _ => {
        Finite.allValuesAsFloats.search(float) match {
          case Searching.Found(foundIndex) => Finite.allValues(foundIndex)
          case Searching.InsertionPoint(insertionPoint) =>
            if (insertionPoint == 0) {
              if (float <= MinValue.toFloat * Finite.base) NegativeInfinity else MinValue
            } else if (insertionPoint >= Finite.allValuesAsFloats.size) {
              if (float >= MaxValue.toFloat * Finite.base) PositiveInfinity else MaxValue
            } else {
              val candidateBelow: Float = Finite.allValuesAsFloats(insertionPoint - 1)
              val candidateAbove: Float = Finite.allValuesAsFloats(insertionPoint)

              // choose closest
              if (math.abs(float - candidateBelow) <= math.abs(float - candidateAbove)) {
                Finite.allValues(insertionPoint - 1)
              } else {
                Finite.allValues(insertionPoint)
              }
            }
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
