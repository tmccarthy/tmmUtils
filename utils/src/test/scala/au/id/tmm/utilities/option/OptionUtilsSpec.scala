package au.id.tmm.utilities.option

import au.id.tmm.utilities.option.OptionUtils.ImprovedOption
import au.id.tmm.utilities.testing.ImprovedFlatSpec

import scala.util.{Failure, Success}

class OptionUtilsSpec extends ImprovedFlatSpec {

  behaviour of "failIfAbsent"

  it should "return a success if the value is present" in {
    assert(Some("hello").failIfAbsent(new RuntimeException) === Success("hello"))
  }

  it should "return a failure if the value is absent" in {
    val expectedThrowable = new RuntimeException

    assert(None.failIfAbsent(expectedThrowable) === Failure(expectedThrowable))
  }

}
