package au.id.tmm.utilities.cats.instances

import au.id.tmm.utilities.cats.instances.numeric._
import au.id.tmm.utilities.testing.Wrapped
import cats.syntax.invariant._
import org.scalatest.flatspec.AnyFlatSpec

class NumericInstancesSpec extends AnyFlatSpec {

  "the fractional instance" should "be resolved correctly" in {
    val _: Fractional[Wrapped[Float]] = Fractional[Float].imap(Wrapped.apply)(_.unwrap)
  }

}
