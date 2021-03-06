package au.id.tmm.utilities.cats.instances

import cats.Invariant

trait NumericInstances {

  implicit val tmmUtilsInvariantForFractional: Invariant[Fractional] = new Invariant[Fractional] {
    override def imap[A, B](fa: Fractional[A])(f: A => B)(g: B => A): Fractional[B] =
      new NumericInstances.IMappedFractional[A, B](fa)(f)(g)
  }

}

object NumericInstances {

  private[instances] class IMappedNumeric[A, B](numericA: Numeric[A])(f: A => B)(g: B => A) extends Numeric[B] {
    @inline override def plus(x: B, y: B): B                 = f(numericA.plus(g(x), g(y)))
    @inline override def minus(x: B, y: B): B                = f(numericA.minus(g(x), g(y)))
    @inline override def times(x: B, y: B): B                = f(numericA.times(g(x), g(y)))
    @inline override def negate(x: B): B                     = f(numericA.negate(g(x)))
    @inline override def fromInt(x: Int): B                  = f(numericA.fromInt(x))
    @inline override def toInt(x: B): Int                    = numericA.toInt(g(x))
    @inline override def toLong(x: B): Long                  = numericA.toLong(g(x))
    @inline override def toFloat(x: B): Float                = numericA.toFloat(g(x))
    @inline override def toDouble(x: B): Double              = numericA.toDouble(g(x))
    @inline override def compare(x: B, y: B): Int            = numericA.compare(g(x), g(y))
    @inline override def parseString(str: String): Option[B] = numericA.parseString(str).map(f)
  }

  private[instances] class IMappedFractional[A, B](fractionalA: Fractional[A])(f: A => B)(g: B => A)
      extends IMappedNumeric[A, B](fractionalA)(f)(g)
      with Fractional[B] {
    @inline override def div(x: B, y: B): B = f(fractionalA.div(g(x), g(y)))
  }

}
