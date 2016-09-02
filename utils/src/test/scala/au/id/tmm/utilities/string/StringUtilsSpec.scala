package au.id.tmm.utilities.string

import au.id.tmm.utilities.string.StringUtils.ImprovedString
import au.id.tmm.utilities.testing.ImprovedFlatSpec

class StringUtilsSpec extends ImprovedFlatSpec {

  // TODO make a class for this
  private val LORUM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
    "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris " +
    "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum " +
    "dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia " +
    "deserunt mollit anim id est laborum."

  behaviour of "containsIgnoreCase"

  it should "correctly identify a contains that matches case" in {
    assert("hello" containsIgnoreCase "lo")
  }

  it should "correctly identify a contains that does not match case" in {
    assert("hello" containsIgnoreCase "LO")
  }

  it should "correctly fail when the string does not contain the substring" in {
    assert("hello" doesNotContainIgnoreCase "world")
  }

  behaviour of "containsAnyIgnoreCase"

  it should "correctly identify a contains that matches case" in {
    assert("hello".containsAnyIgnoreCase("lo", "low"))
  }

  it should "correctly identify a contains that does not match case" in {
    assert("hello".containsAnyIgnoreCase("LO", "low"))
  }

  it should "correctly fail when the string does not contain the substring" in {
    assert(!"hello".containsAnyIgnoreCase("world", "low"))
  }

  behaviour of "containsWordIgnoreCase"

  it should "correctly identify a contained word that matches case" in {
    assert(LORUM_IPSUM containsWordIgnoreCase "Lorem")
  }

  it should "correctly identify a contained word that does not match case" in {
    assert(LORUM_IPSUM.containsWordIgnoreCase("LOREM"))
  }

  it should "return false when the string does not contain the word at all" in {
    assert(!LORUM_IPSUM.containsWordIgnoreCase("world"))
  }

  it should "return false when the string contains the substring but not as a word" in {
    assert(!LORUM_IPSUM.containsWordIgnoreCase("Lor"))
  }

  behaviour of "containsAnyWordIgnoreCase"

  it should "correctly identify a contained word that matches case" in {
    assert(LORUM_IPSUM.containsAnyWordIgnoreCase("Lorem", "blah"))
  }

  it should "correctly identify a contained word that does not match case" in {
    assert(LORUM_IPSUM.containsAnyWordIgnoreCase("LOREM", "blah"))
  }

  it should "return false when the string does not contain the word at all" in {
    assert(!LORUM_IPSUM.containsAnyWordIgnoreCase("world", "blah"))
  }

  it should "return false when the string contains the substring but not as a word" in {
    assert(!LORUM_IPSUM.containsAnyWordIgnoreCase("Lor", "blah"))
  }

  behaviour of "rtrim"

  it should "trim trailing spaces" in {
    assert("hello  ".rtrim === "hello")
  }

  it should "trim trailing tabs" in {
    assert("hello\t".rtrim === "hello")
  }

  it should "return the same string if there's nothing to trim" in {
    assert("hello".rtrim eq "hello")
  }

  behaviour of "ltrim"

  it should "trim leading spaces" in {
    assert("  hello".ltrim === "hello")
  }

  it should "trim leading tabs" in {
    assert("\thello".ltrim === "hello")
  }

  it should "return the same string if there's nothing to trim" in {
    assert("hello".ltrim eq "hello")
  }
}
