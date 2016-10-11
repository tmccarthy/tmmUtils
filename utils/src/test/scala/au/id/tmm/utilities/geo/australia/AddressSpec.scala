package au.id.tmm.utilities.geo.australia

import au.id.tmm.utilities.testing.ImprovedFlatSpec

class AddressSpec extends ImprovedFlatSpec {

  "an address" can "be rendered as a string" in {
    val address = Address(Vector("Level 5", "270 Collins St"), "Melbourne", Postcode("3000"), State.VIC)

    assert(address.render === "Level 5 270 Collins St, Melbourne, VIC, 3000")
  }
}
