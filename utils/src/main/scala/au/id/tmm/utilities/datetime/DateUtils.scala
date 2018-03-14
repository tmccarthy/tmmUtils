package au.id.tmm.utilities.datetime

import java.time._
import java.time.temporal.TemporalAmount
import java.util.Date

object DateUtils {
  implicit class ImprovedLocalDate(localDate: LocalDate) {

    /**
      * Converts this `LocalDate` to an old [[java.util.Date]] at the given timezone.
      */
    def toOldDateAtZone(zoneId: ZoneId): Date = {
      val startOfDay: Instant = localDate.atStartOfDay(zoneId).toInstant

      Date.from(startOfDay)
    }
  }

  implicit class DateArithmetic(date: LocalDate) {

    /**
      * Operator overload for [[java.time.LocalDate#plus]]
      */
    def +(amountToAdd: TemporalAmount): LocalDate = date.plus(amountToAdd)

    /**
      * Operator overload for [[java.time.LocalDate#minus]]
      */
    def -(amountToAdd: TemporalAmount): LocalDate = date.minus(amountToAdd)

    /**
      * Operator overload for [[java.time.Period#between]]
      */
    def -(dateToSubtract: LocalDate): Period = Period.between(dateToSubtract, date)
  }

  implicit class ImprovedOldDate(date: Date) {

    /**
      * Converts this `Date` to a [[java.time.LocalDate]] at the given timezone.
      */
    def toLocalDateAtZone(zoneId: ZoneId): LocalDate = {
      val instant: Instant = Instant.ofEpochMilli(date.getTime)

      LocalDateTime.ofInstant(instant, zoneId).toLocalDate
    }

    /**
      * Converts this `Date` to a [[java.sql.Date]]
      */
    def toSqlDate: java.sql.Date = new java.sql.Date(date.getTime)
  }
}
