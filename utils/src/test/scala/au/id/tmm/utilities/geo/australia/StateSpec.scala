package au.id.tmm.utilities.geo.australia

import au.id.tmm.utilities.testing.ImprovedFlatSpec

class StateSpec extends ImprovedFlatSpec {

  "a state" should "indicate if it is not a territory" in {
    assert(!State.NSW.isTerritory)
  }

  it should "indicate if it is a territory" in {
    assert(State.NT.isTerritory)
  }

  it should "be ordered according to its name" in {
    assert(State.SA > State.NSW)
  }

  it can "be looked up by its abbreviation" in {
    assert(State.fromAbbreviation("SA") === Some(State.SA))
  }

  it should "have a toString" in {
    assert(State.SA.toString === "State(SA)")
  }

  it should "have a toNiceString" in {
    assert(State.SA.toNiceString === "South Australia")
  }

  it should "include the definite article in the nice string representation when appropriate" in {
    assert(State.ACT.toNiceString === "the Australian Capital Territory")
  }
}
