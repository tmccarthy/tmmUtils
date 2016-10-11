package au.id.tmm.utilities.geo

import au.id.tmm.utilities.testing.ImprovedFlatSpec

class LatLongSpec extends ImprovedFlatSpec {

  "a LatLong" should "store its latitude" in {
    assert(LatLong(42, -42).latitude === 42)
  }

  it should "store its longtitude" in {
    assert(LatLong(42, -42).longitude === -42)
  }

}
