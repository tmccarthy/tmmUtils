package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.DupelessSeq
import au.id.tmm.utilities.collection.cats.instances.dupelessSeq._
import au.id.tmm.utilities.collection.cats.instances.dupelessSeq.unlawful._
import cats.laws.discipline.{MonadTests, SemigroupalTests}
import cats.tests.CatsSuite

class DupelessSeqUnlawfulInstancesLawsSpec extends CatsSuite {

  private implicit val dupelessSeqIsomorphism: SemigroupalTests.Isomorphisms[DupelessSeq] = null // TODO

  checkAll(
    "Monad for DupelessSeq",
    MonadTests[DupelessSeq].monad[Int, Int, Int],
  )

}
