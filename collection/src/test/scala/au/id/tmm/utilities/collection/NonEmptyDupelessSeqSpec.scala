package au.id.tmm.utilities.collection

import org.scalatest.FlatSpec

class NonEmptyDupelessSeqSpec extends FlatSpec {

  "a non-empty duplessseq" can "not be split" in {
    intercept[UnsupportedOperationException](NonEmptyDupelessSeq.of(1).splitAt(1))
  }

}
