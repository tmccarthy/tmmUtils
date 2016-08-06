package au.id.tmm.utilities.testing

import org.scalatest.{FlatSpec, GivenWhenThen, OneInstancePerTest}

class ImprovedFlatSpec extends FlatSpec with GivenWhenThen with OneInstancePerTest {
  protected val behaviour = behavior
}