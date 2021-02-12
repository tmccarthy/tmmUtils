package au.id.tmm.utilities.testing

import scala.collection.immutable.ArraySeq

sealed trait MiniFloat {

  def toFloat: Float

  def +(that: MiniFloat): MiniFloat
  def -(that: MiniFloat): MiniFloat
  def *(that: MiniFloat): MiniFloat
  def /(that: MiniFloat): MiniFloat
  def unary_- : MiniFloat

  def safeDiv(that: MiniFloat): Either[ArithmeticException, MiniFloat]

  def isNaN: Boolean
  def isFinite: Boolean

}

object MiniFloat {

  // TODO make these vals
  def MinPositiveValue: MiniFloat = ???
  def PositiveInfinity: MiniFloat = ???
  def NegativeInfinity: MiniFloat = ???
  def NaN: MiniFloat = ???
  def Zero: MiniFloat = ???
  def NegativeOne: MiniFloat = ???
  def One: MiniFloat = ???
  def MaxValue: MiniFloat = ???
  def MinValue: MiniFloat = ???

  def allValues: ArraySeq[MiniFloat] = ???

  def from(float: Float): MiniFloat = ???

}