package au.id.tmm.utilities.collection

import org.scalatest.FlatSpec

class NonEmptyDupelessSeqSpec extends FlatSpec {

  "a non-empty dupelessSeq" can "be flattened" in {
    val neds: NonEmptyDupelessSeq[NonEmptyDupelessSeq[Int]] = NonEmptyDupelessSeq.of(
      NonEmptyDupelessSeq.of(1),
      NonEmptyDupelessSeq.of(2, 3),
    )

    assert(neds.flatten === NonEmptyDupelessSeq.of(1, 2, 3))
  }

}
