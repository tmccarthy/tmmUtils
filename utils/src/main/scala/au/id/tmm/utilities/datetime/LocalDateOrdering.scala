package au.id.tmm.utilities.datetime

import java.time.LocalDate

/**
  * Ordering for [[java.time.LocalDate]]
  */
object LocalDateOrdering extends Ordering[LocalDate] {
  override def compare(left: LocalDate, right: LocalDate): Int = {
    left.compareTo(right)
  }
}
