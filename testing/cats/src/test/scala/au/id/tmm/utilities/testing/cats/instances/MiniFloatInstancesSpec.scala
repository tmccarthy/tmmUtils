package au.id.tmm.utilities.testing.cats.instances

import au.id.tmm.utilities.testing.MiniFloat
import au.id.tmm.utilities.testing.cats.instances.miniFloat._
import au.id.tmm.utilities.testing.scalacheck.instances.miniFloat._
import cats.kernel.laws.discipline.{HashTests, OrderTests}
import org.scalatest
import org.scalatest.funsuite.AnyFunSuite
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

class MiniFloatInstancesSpec extends AnyFunSuite with FunSuiteDiscipline with scalatest.prop.Configuration {
  checkAll("MiniFloat Order", OrderTests[MiniFloat].order)
  checkAll("MiniFloat Hash", HashTests[MiniFloat].hash)
}
