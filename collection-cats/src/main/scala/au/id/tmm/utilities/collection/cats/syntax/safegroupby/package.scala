package au.id.tmm.utilities.collection.cats.syntax

import au.id.tmm.utilities.collection.{DupelessSeq, NonEmptyDupelessSeq, NonEmptySet => TmmUtilsNonEmptySet}
import cats.data.{NonEmptyList, NonEmptyVector}
import cats.~>

package object safegroupby {

  object list extends SafeGroupBySyntax[List, NonEmptyList](λ[List ~> NonEmptyList](NonEmptyList.fromListUnsafe(_)))
  object vector
      extends SafeGroupBySyntax[Vector, NonEmptyVector](λ[Vector ~> NonEmptyVector](NonEmptyVector.fromVectorUnsafe(_)))
  object dupelessSeq
      extends SafeGroupBySyntax[DupelessSeq, NonEmptyDupelessSeq](
        λ[DupelessSeq ~> NonEmptyDupelessSeq](NonEmptyDupelessSeq.fromDupelessSeqUnsafe(_)))

  object set
      extends SafeGroupBySyntax[Set, TmmUtilsNonEmptySet](
        λ[Set ~> TmmUtilsNonEmptySet](TmmUtilsNonEmptySet.fromSetUnsafe(_)))

}
