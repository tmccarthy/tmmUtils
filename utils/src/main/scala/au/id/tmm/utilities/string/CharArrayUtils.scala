package au.id.tmm.utilities.string

import java.util

object CharArrayUtils {
  implicit class ImprovedCharArray(array: Array[Char]) {
    def zeroOut(): Unit = util.Arrays.fill(array, '\0')
  }
}
