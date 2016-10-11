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

  it should "have a nice toString" in {
    assert(State.SA.toString === "State(SA)")
  }

}
