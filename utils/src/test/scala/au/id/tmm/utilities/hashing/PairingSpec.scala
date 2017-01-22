package au.id.tmm.utilities.hashing

import au.id.tmm.utilities.testing.ImprovedFlatSpec

class PairingSpec extends ImprovedFlatSpec {

  private val pairs = Vector(
    (2592,510) -> 6721566,
    (846,2409) -> 5804127,
    (115,808) -> 652979,
    (188,2411) -> 5813109,
    (2041,2202) -> 4850845,
    (2349,2085) -> 5522235,
    (715,2372) -> 5627099,
    (2335,993) -> 5455553,
    (331,832) -> 692555
  )

  def addTestsFor(combination: ((Int, Int), Int)): Unit = {

    val ((x, y), z) = combination

    it should s"pair $x and $y to $z" in {
      assert(Pairing.Szudzik.pair(x, y) === z)
    }

    it should s"invert $z to $x and $y" in {
      assert(Pairing.Szudzik.invert(z) === (x, y))
    }
  }

  behaviour of "the Szudzik pairing function"

  pairs.foreach(addTestsFor)

  it should "reject negative numbers when pairing" in {
    intercept[IllegalArgumentException] {
      Pairing.Szudzik.pair(-1, 1)
    }

    intercept[IllegalArgumentException] {
      Pairing.Szudzik.pair(1, -1)
    }
  }

  it should "reject negative numbers when inverting" in {
    intercept[IllegalArgumentException] {
      Pairing.Szudzik.invert(-1)
    }
  }

  it should "combine 3 values" in {
    assert(Pairing.Szudzik.combine(42, 53, 1) === 8131053)
  }

  it should "invert to 3 values" in {
    assert(Pairing.Szudzik.invert3(8131053) === (42, 53, 1))
  }

  it should "throw on an overflow" in {
    intercept[ArithmeticException] {
      Pairing.Szudzik.pair(Long.MaxValue, 3)
    }
  }

}
