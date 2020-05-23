package au.id.tmm.utilities.collection.cats.syntax.safegroupby

import cats.~>

import scala.collection.{IterableOps, MapView}

abstract class SafeGroupBySyntax[C[A] <: IterableOps[A, C, C[A]], NEC[_]](makeUnsafe: C ~> NEC) {

  implicit class Ops[A](ca: C[A]) {

    def safeGroupBy[K](f: A => K): MapView[K, NEC[A]] =
      ca.groupBy(f).view.mapValues(makeUnsafe.apply)

    def safeGroupMap[K, V](key: A => K)(f: A => V): MapView[K, NEC[V]] =
      ca.groupMap(key)(f).view.mapValues(makeUnsafe.apply)

    def safeGroupByKey[K, V](implicit ev: A <:< (K, V)): MapView[K, NEC[V]] =
      ca.groupMap(_._1)(_._2).view.mapValues(makeUnsafe.apply)

  }

}
