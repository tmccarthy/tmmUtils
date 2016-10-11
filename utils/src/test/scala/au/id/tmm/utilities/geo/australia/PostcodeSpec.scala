package au.id.tmm.utilities.geo.australia

import au.id.tmm.utilities.testing.ImprovedFlatSpec

class PostcodeSpec extends ImprovedFlatSpec {

  "a postcode" must "not include a non-numeric character" in {
    intercept[IllegalArgumentException] {
      Postcode("300a")
    }
  }

  it must "have exactly 4 digits" in {
    intercept[IllegalArgumentException] {
      Postcode("800")
    }
  }
}
