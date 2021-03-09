package au.id.tmm.utilities.testing.cats.instances

import au.id.tmm.utilities.testing.MiniFloat
import au.id.tmm.utilities.testing.cats.instances.miniFloat._
import au.id.tmm.utilities.testing.scalacheck.instances.miniFloat._
import cats.kernel.laws.discipline.{HashTests, OrderTests}
import munit.{DisciplineSuite, FunSuite}

class MiniFloatInstancesSpec extends FunSuite with DisciplineSuite {
  checkAll("MiniFloat Order", OrderTests[MiniFloat].order)
  checkAll("MiniFloat Hash", HashTests[MiniFloat].hash)
}
