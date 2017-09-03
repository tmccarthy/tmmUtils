package au.id.tmm.utilities.collection

import au.id.tmm.utilities.collection.CollectionUtils.TraversableOps

/**
  * A rank
  * @param ordinal     The rank (zero-indexed)
  * @param totalCount  The total number of records
  * @param rankIsShared True if more than one element shared this ordinal rank
  */
final case class Rank(
                       ordinal: Int,
                       totalCount: Int,
                       rankIsShared: Boolean,
                     )

object Rank {

  def ranksFrom[A](values: Iterable[(A, Double)]): Map[A, Rank] = {

    val totalCount = values.size

    values
      .toStream
      .sortBy(_._2)
      .reverse
      .zipWithIndex
      .groupedBy { case ((element, score), ordinal) =>
        score
      }
      .flatMap { case (score, recordsWithSameScoreStream) =>
        val recordsWithSameScore = recordsWithSameScoreStream.toVector

        val moreThanOneRecordWithSameScore = recordsWithSameScore.size > 1

        val highestOrdinalForRecordsWithSameScore = recordsWithSameScore.head._2

        val rank = Rank(
          highestOrdinalForRecordsWithSameScore,
          totalCount,
          rankIsShared = moreThanOneRecordWithSameScore,
        )

        recordsWithSameScore
          .map { case ((element, _), _) =>
            element -> rank
          }
      }
      .toMap
  }
}
