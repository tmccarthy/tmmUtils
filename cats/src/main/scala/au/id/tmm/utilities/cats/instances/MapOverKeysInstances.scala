package au.id.tmm.utilities.cats.instances

import cats.kernel.{Monoid, Semigroup}
import cats.{Functor, MonoidK}

trait MapOverKeysInstances {
  implicit def functor[V : Semigroup]: Functor[Map[*, V]] = new Functor[Map[*, V]] {
    override def map[A, B](fa: Map[A, V])(f: A => B): Map[B, V] =
      fa.groupMapReduce[B, V](
        key = {
          case (a, v) => f(a): B
        },
      )(
        f = {
          case (a, v) => v
        },
      )(
        reduce = Semigroup[V].combine,
      )
  }

  implicit def monoidk[V : Monoid]: MonoidK[Map[*, V]] = new MonoidK[Map[*, V]] {
    override def empty[A]: Map[A, V] = Map.empty[A, V]
    override def combineK[A](x: Map[A, V], y: Map[A, V]): Map[A, V] = {
      val newKeySet: Set[A] = x.keySet ++ y.keySet
      newKeySet.map { a =>
        val left: V  = x.getOrElse(a, Monoid[V].empty)
        val right: V = y.getOrElse(a, Monoid[V].empty)
        a -> Monoid[V].combine(left, right)
      }.toMap
    }
  }
}
