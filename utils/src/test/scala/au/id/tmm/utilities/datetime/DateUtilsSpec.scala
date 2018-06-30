package au.id.tmm.utilities.datetime

import java.time.Month._
import java.time.{LocalDate, Period, ZoneId}
import java.util.Date

import au.id.tmm.utilities.datetime.DateUtils._
import au.id.tmm.utilities.testing.ImprovedFlatSpec

class DateUtilsSpec extends ImprovedFlatSpec {
  behaviour of "the toOldDateAtZone method"

  it should "correctly convert the date 3 July 2015 to an old Date" in {
    val originalDate = LocalDate.of(2015, JULY, 3)
    val timezone = ZoneId.of("Australia/Melbourne")

    val convertedDate = originalDate.toOldDateAtZone(timezone)

    assert(convertedDate.getTime == 1435845600000l)
  }

  it should "correctly convert the date 3 July 2015 to an old Date in another timezone" in {
    val originalDate = LocalDate.of(2015, JULY, 3)
    val timezone = ZoneId.of("Africa/Johannesburg")

    val convertedDate = originalDate.toOldDateAtZone(timezone)

    assert(convertedDate.getTime == 1435874400000l)
  }

  behaviour of "the toLocalDateAtZone method"

  it should "correctly convert the date 3 July 2015 to a LocalDate" in {
    val originalDate = new Date(1435845600000l)
    val timezone = ZoneId.of("Australia/Melbourne")

    val convertedDate = originalDate.toLocalDateAtZone(timezone)

    assert(convertedDate.getYear == 2015)
    assert(convertedDate.getMonth === JULY)
    assert(convertedDate.getDayOfMonth === 3)
  }

  it should "correctly convert the date 3 July 2015 to a LocalDate in another timezone" in {
    val originalDate = new Date(1435845600000l)
    val timezone = ZoneId.of("Africa/Johannesburg")

    val convertedDate = originalDate.toLocalDateAtZone(timezone)

    assert(convertedDate.getYear == 2015)
    assert(convertedDate.getMonth === JULY)
    assert(convertedDate.getDayOfMonth === 2)
  }

  behaviour of "the toSqlDate method"

  it should "correctly convert the date 3 July 2015 to an java.sql.Date" in {
    val originalDate = new Date(1435845600000l)

    val convertedDate = originalDate.toSqlDate

    assert(convertedDate.getTime === originalDate.getTime)
  }

  behaviour of "duration addition to a date"

  it should "correctly add 2 days to 13/03/2000" in {
    assert(LocalDate.of(2000, MARCH, 13) + Period.ofDays(2) === LocalDate.of(2000, MARCH, 15))
  }

  behaviour of "duration subtraction from a date"

  it should "correctly subtract 2 days from 13/03/2000" in {
    assert(LocalDate.of(2000, MARCH, 13) - Period.ofDays(2) === LocalDate.of(2000, MARCH, 11))
  }

  behavior of "the date difference"

  it should "have a one day difference between 13/03/2000 and 14/03/2000" in {
    val date1 = LocalDate.of(2000, MARCH, 13)
    val date2 = LocalDate.of(2000, MARCH, 14)

    assert(date2 - date1 === Period.ofDays(1))
  }

  it should "have a negative one day difference between 14/03/2000 and 13/03/2000" in {
    val date1 = LocalDate.of(2000, MARCH, 14)
    val date2 = LocalDate.of(2000, MARCH, 13)

    assert(date2 - date1 === Period.ofDays(-1))
  }
}
