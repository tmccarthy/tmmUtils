package au.id.tmm.utilities.cats.instances

import au.id.tmm.utilities.cats.instances.numeric._
import au.id.tmm.utilities.testing.Wrapped
import cats.syntax.invariant._
import munit.FunSuite

class NumericInstancesSpec extends FunSuite {

  test("the fractional instance should be resolved correctly") {
    val _: Fractional[Wrapped[Float]] = Fractional[Float].imap(Wrapped.apply)(_.unwrap)
  }

}
