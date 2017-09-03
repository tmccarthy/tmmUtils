package au.id.tmm.utilities.collection

import au.id.tmm.utilities.testing.ImprovedFlatSpec

class RankSpec extends ImprovedFlatSpec {

  "a rank" should "have an ordinal" in {
    val sut = Rank(ordinal = 0, totalCount = 1, rankIsShared = false)

    assert(sut.ordinal === 0)
  }

  it should "have a total count" in {
    val sut = Rank(ordinal = 0, totalCount = 1, rankIsShared = false)

    assert(sut.totalCount === 1)
  }

  it should "have an indication of whether more than one record shares the same rank" in {
    val sut = Rank(ordinal = 0, totalCount = 1, rankIsShared = true)

    assert(sut.rankIsShared === true)
  }

  "ranks" can "be generated from a map" in {
    val map = Map(
      "A" -> 2d,
      "B" -> 4d,
      "C" -> 6d,
    )

    val actualRanks = Rank.ranksFrom(map)
    val expectedRanks = Map(
      "C" -> Rank(ordinal = 0, totalCount = 3, rankIsShared = false),
      "B" -> Rank(ordinal = 1, totalCount = 3, rankIsShared = false),
      "A" -> Rank(ordinal = 2, totalCount = 3, rankIsShared = false),
    )

    assert(expectedRanks === actualRanks)
  }

  "ranks" should "handle duplicate values" in {
    val values = Map(
      "A" -> 5d,
      "B" -> 6d,
      "C" -> 7d,
      "D" -> 6d,
    )

    val actualRanks = Rank.ranksFrom(values)

    val expectedRanks = Map(
      "C" -> Rank(ordinal = 0, totalCount = 4, rankIsShared = false),
      "B" -> Rank(ordinal = 1, totalCount = 4, rankIsShared = true),
      "D" -> Rank(ordinal = 1, totalCount = 4, rankIsShared = true),
      "A" -> Rank(ordinal = 3, totalCount = 4, rankIsShared = false),
    )

    assert(expectedRanks === actualRanks)
  }
}
