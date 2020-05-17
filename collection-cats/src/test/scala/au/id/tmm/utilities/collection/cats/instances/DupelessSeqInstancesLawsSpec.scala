package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.DupelessSeq
import au.id.tmm.utilities.collection.cats.instances.dupelessSeq._
import cats.data.Validated
import cats.kernel.laws.discipline.{BandTests, HashTests, MonoidTests}
import cats.laws.discipline.arbitrary._
import cats.laws.discipline.{MonoidKTests, TraverseTests}
import cats.tests.CatsSuite

class DupelessSeqInstancesLawsSpec extends CatsSuite {

  checkAll("Hash for DupelessSeq", HashTests[DupelessSeq[Int]].hash)
  checkAll("Monoid for DupelessSeq", MonoidTests[DupelessSeq[Int]].monoid)
  checkAll("Band for DupelessSeq", BandTests[DupelessSeq[Int]].band)
  checkAll("MonoidK for DupelessSeq", MonoidKTests[DupelessSeq].monoidK[Int])
  checkAll(
    "Traverse for DupelessSeq",
    TraverseTests[DupelessSeq].traverse[Int, Int, Int, Set[Int], Validated[Int, *], Option],
  )

}
