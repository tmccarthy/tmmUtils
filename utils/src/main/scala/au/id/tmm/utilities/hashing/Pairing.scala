package au.id.tmm.utilities.hashing

import java.lang.Math._

import scala.math.{floor, pow, sqrt}

object Pairing {

  trait PairingFunction {

    def pair(left: Long, right: Long): Long

    def combine(left: Long, centre: Long, right: Long): Long = combineN(left, centre, right)

    private def combineN(inputs: Long*): Long = inputs.reduceLeft(pair)

    def invert(paired: Long): (Long, Long)

    def invert3(combined: Long): (Long, Long, Long) = {
      val inverted = invertN(combined, 3)

      (inverted(0), inverted(1), inverted(2))
    }

    private def invertN(combined: Long, numOutputs: Int): List[Long] = {
      invertRemaining(Nil, combined, numOutputs)
    }

    @scala.annotation.tailrec
    private def invertRemaining(outputsSoFar: List[Long], lastCombined: Long, numRemainingOutputs: Int): List[Long] = {
      if (numRemainingOutputs == 0) {
        outputsSoFar
      } else if (numRemainingOutputs == 1) {
        invertRemaining(lastCombined +: outputsSoFar, 0, 0)
      } else {
        val (nextCombined, newOutput) = invert(lastCombined)

        invertRemaining(newOutput +: outputsSoFar, nextCombined, numRemainingOutputs - 1)
      }
    }
  }

  object Szudzik extends PairingFunction {

    override def pair(x: Long, y: Long): Long = {
      require(x >= 0)
      require(y >= 0)

      if (y > x) {
        addExact(multiplyExact(y, y), x)
      } else {
        addExact(addExact(multiplyExact(x, x), x), y)
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
