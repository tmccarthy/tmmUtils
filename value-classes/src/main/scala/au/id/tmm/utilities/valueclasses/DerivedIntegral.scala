package au.id.tmm.utilities.valueclasses

private[valueclasses] class DerivedIntegral[A, I : Integral](asIntegral: A => I, makeA: I => A) extends Integral[A] {
  private val integralForI: Integral[I] = implicitly

  @inline override def quot(x: A, y: A): A                 = makeA(integralForI.quot(asIntegral(x), asIntegral(y)))
  @inline override def rem(x: A, y: A): A                  = makeA(integralForI.rem(asIntegral(x), asIntegral(y)))
  @inline override def plus(x: A, y: A): A                 = makeA(integralForI.plus(asIntegral(x), asIntegral(y)))
  @inline override def minus(x: A, y: A): A                = makeA(integralForI.minus(asIntegral(x), asIntegral(y)))
  @inline override def times(x: A, y: A): A                = makeA(integralForI.times(asIntegral(x), asIntegral(y)))
  @inline override def compare(x: A, y: A): Int            = integralForI.compare(asIntegral(x), asIntegral(y))
  @inline override def negate(x: A): A                     = makeA(integralForI.negate(asIntegral(x)))
  @inline override def toInt(x: A): Int                    = integralForI.toInt(asIntegral(x))
  @inline override def toLong(x: A): Long                  = integralForI.toLong(asIntegral(x))
  @inline override def toFloat(x: A): Float                = integralForI.toFloat(asIntegral(x))
  @inline override def toDouble(x: A): Double              = integralForI.toDouble(asIntegral(x))
  @inline override def fromInt(x: Int): A                  = makeA(integralForI.fromInt(x))
  @inline override def parseString(str: String): Option[A] = integralForI.parseString(str).map(makeA)
}
