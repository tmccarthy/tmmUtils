package au.id.tmm.utilities

package object valueclasses {

  def deriveIntegral[A, I : Integral](asIntegral: A => I, makeA: I => A): Integral[A] =
    new DerivedIntegral(asIntegral, makeA)

  def deriveFractional[A, F : Fractional](asFractional: A => F, makeA: F => A): Fractional[A] =
    new DerivedFractional(asFractional, makeA)

  def deriveOrdering[A, O : Ordering](asOrdered: A => O): Ordering[A] =
    new DerivedOrdering(asOrdered)

}
