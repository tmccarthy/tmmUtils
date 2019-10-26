package au.id.tmm.utilities.collection

import com.github.ghik.silencer.silent
import org.scalatest.FlatSpec

import scala.collection.immutable.ArraySeq

@silent("unrelated")
class DuplessSeq2_13Spec extends FlatSpec {

  "a DupelessSeq" can "be converted to an ArraySeq" in {
    assert(DupelessSeq(1, 2, 3).toArraySeq === ArraySeq(1, 2, 3))
  }

  it should "allow appending an element" in {
    assert((DupelessSeq(1, 2, 3).appended(4): DupelessSeq[Int]) === DupelessSeq(1, 2, 3, 4))
  }

  it should "allow appending a list" in {
    assert((DupelessSeq(1, 2, 3).appendedAll(List(4, 5, 6)): DupelessSeq[Int]) === DupelessSeq(1, 2, 3, 4, 5, 6))
  }

}
