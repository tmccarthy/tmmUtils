package au.id.tmm.utilities.string

import au.id.tmm.utilities.testing.ImprovedFlatSpec

class CharSetsSpec extends ImprovedFlatSpec {

  "the latin digits" should "include '1'" in {
    assert(CharSets.LATIN_DIGITS contains '1')
  }

  it should "not contain 'a'" in {
    assert(!(CharSets.LATIN_DIGITS contains 'a'))
  }

  it should "not contain the Arabic digit '٩'" in {
    assert(!(CharSets.LATIN_DIGITS contains '٩'))
  }
}
