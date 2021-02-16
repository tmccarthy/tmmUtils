package au.id.tmm.utilities.testing

import scala.collection.Searching
import scala.collection.immutable.ArraySeq

// (-8.0, -4.0, -2.0, -1.0, -0.5, 0.0, 0.5, 1.0, 2.0, 4.0, 8.0)
final class MiniFloat private (private val underlying: Float) extends AnyVal {

  def toFloat: Float   = underlying
  def toDouble: Double = underlying.toDouble
  def toInt: Int       = underlying.toInt
  def toLong: Long     = underlying.toLong

  def +(that: MiniFloat): MiniFloat = MiniFloat.from(this.underlying + that.underlying)
  def -(that: MiniFloat): MiniFloat = MiniFloat.from(this.underlying - that.underlying)
  def *(that: MiniFloat): MiniFloat = MiniFloat.from(this.underlying * that.underlying)
  def /(that: MiniFloat): MiniFloat = MiniFloat.from(this.underlying / that.underlying)
  def unary_- : MiniFloat           = MiniFloat.from(-this.underlying)

  def isNaN: Boolean    = underlying.isNaN
  def isFinite: Boolean = underlying.isFinite

  override def toString = s"MiniFloat($underlying)"

}

object MiniFloat {

  private val base = 2

  private val minSignificand = -2
  private val maxSignificand = 2

  private val minExponent = -1
  private val maxExponent = 2

  val Zero: MiniFloat        = new MiniFloat(0f)
  val NegativeOne: MiniFloat = new MiniFloat(-1f)
  val One: MiniFloat         = new MiniFloat(1f)

  val MaxValue: MiniFloat         = new MiniFloat(maxSignificand * math.pow(base, maxExponent).toFloat)
  val MinValue: MiniFloat         = new MiniFloat(minSignificand * math.pow(base, maxExponent).toFloat)
  val MinPositiveValue: MiniFloat = new MiniFloat(math.pow(base, minSignificand).toFloat)

  val PositiveInfinity: MiniFloat = new MiniFloat(Float.PositiveInfinity)
  val NegativeInfinity: MiniFloat = new MiniFloat(Float.NegativeInfinity)
  val NaN: MiniFloat              = new MiniFloat(Float.NaN)

  private val normalFloatValues: ArraySeq[Float] = {
    for {
      significand <- Range.inclusive(minSignificand, maxSignificand)
      exponent    <- Range.inclusive(minExponent, maxExponent)
    } yield significand * math.pow(base, exponent).toFloat
  }.to(ArraySeq).distinct.sorted

  def allValues: ArraySeq[MiniFloat] =
    ArraySeq(NegativeInfinity) ++
      normalFloatValues.map(new MiniFloat(_)) :+
      PositiveInfinity :+
      NaN

  def from(float: Float): MiniFloat =
    if (!float.isFinite) {
      new MiniFloat(float)
    } else {
      normalFloatValues.search(float) match {
        case Searching.Found(foundIndex) => new MiniFloat(normalFloatValues.apply(foundIndex))
        case Searching.InsertionPoint(insertionPoint) =>
          if (insertionPoint == 0) {
            if (float <= MinValue.underlying * base) NegativeInfinity else MinValue
          } else if (insertionPoint >= normalFloatValues.size) {
            if (float >= MaxValue.underlying * base) PositiveInfinity else MaxValue
          } else {
            val candidateBelow: Float = normalFloatValues(insertionPoint - 1)
            val candidateAbove: Float = normalFloatValues(insertionPoint)

            // choose closest
            if (math.abs(float - candidateBelow) <= math.abs(float - candidateAbove)) new MiniFloat(candidateBelow)
            else new MiniFloat(candidateAbove)
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
