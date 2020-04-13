package au.id.tmm.utilities.valueclasses

private[valueclasses] class DerivedFractional[A, F : Fractional](asFractional: A => F, makeA: F => A)
    extends Fractional[A] {
  private val fractionalForF: Fractional[F] = implicitly

  @inline override def div(x: A, y: A): A                  = makeA(fractionalForF.div(asFractional(x), asFractional(y)))
  @inline override def plus(x: A, y: A): A                 = makeA(fractionalForF.plus(asFractional(x), asFractional(y)))
  @inline override def minus(x: A, y: A): A                = makeA(fractionalForF.minus(asFractional(x), asFractional(y)))
  @inline override def times(x: A, y: A): A                = makeA(fractionalForF.times(asFractional(x), asFractional(y)))
  @inline override def negate(x: A): A                     = makeA(fractionalForF.negate(asFractional(x)))
  @inline override def fromInt(x: Int): A                  = makeA(fractionalForF.fromInt(x))
  @inline override def toInt(x: A): Int                    = fractionalForF.toInt(asFractional(x))
  @inline override def toLong(x: A): Long                  = fractionalForF.toLong(asFractional(x))
  @inline override def toFloat(x: A): Float                = fractionalForF.toFloat(asFractional(x))
  @inline override def toDouble(x: A): Double              = fractionalForF.toDouble(asFractional(x))
  @inline override def compare(x: A, y: A): Int            = fractionalForF.compare(asFractional(x), asFractional(y))
  @inline override def parseString(str: String): Option[A] = fractionalForF.parseString(str).map(makeA)
}
