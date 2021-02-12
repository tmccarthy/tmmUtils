package au.id.tmm.utilities.testing

import au.id.tmm.utilities.testing.MiniFloat.{base, maxSignificand, minExponent}

import scala.annotation.tailrec

final class MiniFloat private (
  private[testing] val sign: Boolean,
  private[testing] val significand: Byte,
  private[testing] val exponent: Byte,
) {

  private def signByte: Byte = if (sign) 0x1 else -0x1

  def toDouble: Double = signByte * significand.toDouble * math.pow(base, exponent)

  @tailrec
  def normalised: MiniFloat =
    if (significand == 0) {
      MiniFloat.zero
    } else if ((significand * base) <= maxSignificand && exponent > minExponent) {
      new MiniFloat(sign, (significand * base).toByte, (exponent - 1).toByte).normalised
    } else {
      this
    }

  override def equals(other: Any): Boolean = other match {
    case that: MiniFloat =>
      this.toDouble == that.toDouble
    case _ => false
  }

  override def hashCode(): Int = toDouble.hashCode
}

object MiniFloat {

  private val zero: MiniFloat = new MiniFloat(true, 0, 0)

  private val base: Byte = 2.toByte

  private val minSignificand: Byte = 0
  private val maxSignificand: Byte = 2
  private val minExponent: Byte = -4
  private val maxExponent: Byte = 4

  val allValues: List[MiniFloat] = {
    for {
      sign <- List(true, false)
      significand <- Range.inclusive(minSignificand, maxSignificand)
      exponent <- Range.inclusive(minExponent, maxExponent)
    } yield new MiniFloat(sign, significand.toByte, exponent.toByte).normalised
  }.distinct.sortBy(_.toDouble)

}