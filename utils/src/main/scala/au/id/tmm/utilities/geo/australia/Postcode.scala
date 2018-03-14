package au.id.tmm.utilities.geo.australia

import au.id.tmm.utilities.string.CharSets

/**
  * An Australian postcode.
  */
final case class Postcode(code: String) {
  require(code.length == 4, "A postcode must have exactly 4 characters")
  require(code.toCharArray.forall(CharSets.LATIN_DIGITS.contains), "A postcode can contain only digits")
}