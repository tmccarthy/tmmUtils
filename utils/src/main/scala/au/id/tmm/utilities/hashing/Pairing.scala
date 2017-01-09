package au.id.tmm.utilities.hashing

import scala.math.{floor, pow, sqrt}


object Pairing {

  trait PairingFunction {

    def pair(left: Long, right: Long): Long

    def invert(paired: Long): (Long, Long)

  }

  object Szudzik extends PairingFunction {

    override def pair(x: Long, y: Long): Long = {
      require(x >= 0)
      require(y >= 0)

      if (y > x) {
        y * y + x
      } else {
        x * x + x + y
      }
    }

    override def invert(z: Long): (Long, Long) = {
      require(z > 0)

      val q = floor(sqrt(z)).toLong
      val l = z - pow(q, 2).toLong

      if (l < q) {
        (l, q)
      } else {
        (q, l - q)
      }
    }

  }

}
