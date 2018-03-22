package au.id.tmm.utilities.testing

import org.scalatest.{FlatSpec, GivenWhenThen, OneInstancePerTest}

/**
  * An "improved" implementation of <a href="http://doc.scalatest.org/1.8/org/scalatest/FlatSpec.html">`org.scalatest.FlatSpec`</a>,
  * which adds some commonly used traits and fixes some spelling.
  */
class ImprovedFlatSpec extends FlatSpec with GivenWhenThen with OneInstancePerTest {

  /**
    * Alias for [[behavior]], because Australian's make code too.
    */
  protected val behaviour: BehaviorWord = behavior
}