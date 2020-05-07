package au.id.tmm.utilities.cats.syntax

import cats.data.NonEmptyVector

final class VectorSyntax[A] private[syntax](vector: Vector[A]) {

  def groupByNevUniversalEquals[K](f: A => K): Map[K, NonEmptyVector[A]] =
    vector.groupBy(f).map {
      case (k, vector) => k -> NonEmptyVector.fromVectorUnsafe(vector)
    }

  def groupMapNevUniversalEquals[K, B](key: A => K)(f: A => B): Map[K, NonEmptyVector[B]] =
    vector.groupMap(key)(f).map {
      case (k, vector) => k -> NonEmptyVector.fromVectorUnsafe(vector)
    }

}
