package au.id.tmm.utilities.testing

import scala.collection.Searching
import scala.collection.immutable.ArraySeq

/**
  *
  *
  */
// (-8.0, -4.0, -2.0, -1.0, -0.5, 0.0, 0.5, 1.0, 2.0, 4.0, 8.0)
final class MiniFloat private (private val underlying: Float) extends AnyVal {

  def toFloat: Float = underlying

  def +(that: MiniFloat): MiniFloat = MiniFloat.from(this.underlying + that.underlying)
  def -(that: MiniFloat): MiniFloat = MiniFloat.from(this.underlying - that.underlying)
  def *(that: MiniFloat): MiniFloat = MiniFloat.from(this.underlying * that.underlying)
  def /(that: MiniFloat): MiniFloat = MiniFloat.from(this.underlying / that.underlying)
  def unary_- : MiniFloat = MiniFloat.from(-this.underlying)

  def isNaN: Boolean = underlying.isNaN
  def isFinite: Boolean = underlying.isFinite

  override def toString = s"MiniFloat($underlying)"

}

object MiniFloat {

  private val minSignificand = -2
  private val maxSignificand = 2

  private val minExponent = -1
  private val maxExponent = 2

  // TODO make these vals
  val Zero: MiniFloat = new MiniFloat(0f)
  val NegativeOne: MiniFloat = new MiniFloat(-1f)
  val One: MiniFloat = new MiniFloat(1f)

  val MaxValue: MiniFloat = new MiniFloat(maxSignificand * math.pow(2, maxExponent).toFloat)
  val MinValue: MiniFloat = new MiniFloat(minSignificand * math.pow(2, maxExponent).toFloat)
  val MinPositiveValue: MiniFloat = new MiniFloat(math.pow(2, minSignificand).toFloat)

  val PositiveInfinity: MiniFloat = new MiniFloat(Float.PositiveInfinity)
  val NegativeInfinity: MiniFloat = new MiniFloat(Float.NegativeInfinity)
  val NaN: MiniFloat = new MiniFloat(Float.NaN)

  private val allFloatValues: ArraySeq[Float] = {
    for {
      significand <- Range.inclusive(minSignificand, maxSignificand)
      exponent    <- Range.inclusive(minExponent, maxExponent)
    } yield significand * math.pow(2, exponent).toFloat
  }.sorted.distinct.to(ArraySeq)

  def allValues: ArraySeq[MiniFloat] = allFloatValues.map(new MiniFloat(_))

  def from(float: Float): MiniFloat = {
    if (!float.isFinite) {
      new MiniFloat(float)
    } else {
      allFloatValues.search(float) match {
        case Searching.Found(foundIndex) => new MiniFloat(allFloatValues.apply(foundIndex))
        case Searching.InsertionPoint(insertionPoint) => {
          val candidateBelow: Option[Float] = allFloatValues.lift(insertionPoint - 1)
          val candidateAbove: Option[Float] = allFloatValues.lift(insertionPoint)

          (candidateBelow, candidateAbove) match {
            case (Some(candidateBelow), Some(candidateAbove)) => if (math.abs(float - candidateBelow) < math.abs(float - candidateAbove)) new MiniFloat(candidateBelow) else new MiniFloat(candidateAbove)
            case (None, Some(candidateAbove)) => new MiniFloat(candidateAbove)
            case (Some(candidateBelow), None) => new MiniFloat(candidateBelow)
            case (None, None) => MiniFloat.NaN
          }
        }
      }
    }
  }
  // TODO conversions from other numbers

  // TODO ordering
}