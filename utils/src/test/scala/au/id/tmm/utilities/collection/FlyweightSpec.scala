package au.id.tmm.utilities.collection

import au.id.tmm.utilities.geo.australia.Postcode
import au.id.tmm.utilities.testing.ImprovedFlatSpec

class FlyweightSpec extends ImprovedFlatSpec {

  private val testFlyweight = Flyweight(Postcode.apply)

  "a flyweight" should "construct its element from the supplied function" in {
    assert(testFlyweight("3000") === Postcode("3000"))
  }

  it should "reuse previous values" in {
    assert(testFlyweight("3000") eq testFlyweight("3000"))
  }
}
