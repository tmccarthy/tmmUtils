package au.id.tmm.utilities.datetime

import java.time.LocalDate

object LocalDateOrdering extends Ordering[LocalDate] {
  override def compare(left: LocalDate, right: LocalDate): Int = {
    if (left isBefore right) {
      -1
    } else if (left isAfter right) {
      1
    } else {
      0
    }
  }
}
