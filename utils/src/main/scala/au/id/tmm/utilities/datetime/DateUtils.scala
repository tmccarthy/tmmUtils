package au.id.tmm.utilities.datetime

import java.time.temporal.TemporalAmount
import java.time._
import java.util.Date

object DateUtils {
  implicit class ImprovedLocalDate(localDate: LocalDate) {
    def toOldDateAtZone(zoneId: ZoneId): Date = {
      val startOfDay: Instant = localDate.atStartOfDay(zoneId).toInstant

      Date.from(startOfDay)
    }
  }

  implicit class DateArithmetic(date: LocalDate) {
    def +(amountToAdd: TemporalAmount): LocalDate = date.plus(amountToAdd)
    def -(amountToAdd: TemporalAmount): LocalDate = date.minus(amountToAdd)

    def -(dateToSubtract: LocalDate): Period = Period.between(dateToSubtract, date)
  }

  implicit class ImprovedOldDate(date: Date) {
    def toLocalDateAtZone(zoneId: ZoneId): LocalDate = {
      val instant: Instant = Instant.ofEpochMilli(date.getTime)

      LocalDateTime.ofInstant(instant, zoneId).toLocalDate
    }

    def toSqlDate: java.sql.Date = new java.sql.Date(date.getTime)
  }
}
