package au.id.tmm.utilities.datetime

import java.time.{LocalDate, Month}

import au.id.tmm.utilities.testing.ImprovedFlatSpec

class LocalDateOrderingSpec extends ImprovedFlatSpec {

  private val earlierDate = LocalDate.of(1999, Month.MAY, 17)
  private val laterDate = LocalDate.of(2015, Month.JULY, 3)

  behaviour of LocalDateOrdering.getClass.getSimpleName

  it should "correctly find the max of 3 July 2015 and 17 May 1999" in {
    assert(laterDate === LocalDateOrdering.max(earlierDate, laterDate))
  }

  it should "correctly find the max of 17 May 1999 and 3 July 2015, in that order" in {
    assert(laterDate === LocalDateOrdering.max(laterDate, earlierDate))
  }

  it should "correctly find the min of 3 July 2015 and 17 May 1999" in {
    assert(earlierDate === LocalDateOrdering.min(earlierDate, laterDate))
  }

  it should "correctly identify equal dates" in {
    assert(LocalDateOrdering.equiv(earlierDate, earlierDate))
  }
}
