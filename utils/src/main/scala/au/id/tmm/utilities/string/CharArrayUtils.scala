package au.id.tmm.utilities.string

import java.util

object CharArrayUtils {
  implicit class ImprovedCharArray(array: Array[Char]) {

    /**
      * Replaces all elements in this array with the null character.
      */
    def zeroOut(): Unit = util.Arrays.fill(array, '\u0000')

  }
}
