package au.id.tmm.utilities.testing.cats.instances

import au.id.tmm.utilities.testing.cats.instances.wrapped._
import au.id.tmm.utilities.testing.scalacheck.instances.wrapped._
import au.id.tmm.utilities.testing.{Wrapped, WrappedK}
import cats.laws.discipline.InvariantTests
import org.scalatest
import org.scalatest.flatspec.AnyFlatSpec
import org.typelevel.discipline.scalatest.FlatSpecDiscipline

class WrappedInstancesSpec extends AnyFlatSpec with FlatSpecDiscipline with scalatest.prop.Configuration {

  private type WrappedOption[A] = WrappedK[Option, A]

  checkAll("Wrapped Invariant", InvariantTests[Wrapped].invariant[Int, Int, Int])
  checkAll("WrappedK Invariant", InvariantTests[WrappedOption].invariant[Int, Int, Int])

}
