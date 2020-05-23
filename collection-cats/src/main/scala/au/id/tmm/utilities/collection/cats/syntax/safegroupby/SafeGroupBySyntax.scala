package au.id.tmm.utilities.collection.cats.syntax.safegroupby

import cats.~>

import scala.collection.{IterableOps, mutable}

abstract class SafeGroupBySyntax[C[A] <: IterableOps[A, C, C[A]], NEC[_]](makeUnsafe: C ~> NEC) {

  implicit class Ops[A](ca: C[A]) {

    private type CBuilder[X] = mutable.Builder[X, C[X]]

    def safeGroupBy[K](f: A => K): Map[K, NEC[A]] =
      safeGroupMap(f)(identity)

    def safeGroupMap[K, V](key: A => K)(f: A => V): Map[K, NEC[V]] = {
      val interim = mutable.Map[K, CBuilder[V]]()

      ca.foreach { a: A =>
        val k: K = key(a)
        val v: V = f(a)

        val cBuilder: CBuilder[V] = interim.getOrElseUpdate(k, ca.iterableFactory.newBuilder)

        cBuilder.addOne(v)
      }

      val result = Map.newBuilder[K, NEC[V]]

      result.sizeHint(interim.size)

      interim.foreach {
        case (k, cBuilder) =>
          val c: C[V]     = cBuilder.result()
          val nec: NEC[V] = makeUnsafe(c)
          result.addOne(k -> nec)
      }

      result.result()
    }

    def safeGroupByKey[K, V](implicit ev: A <:< (K, V)): Map[K, NEC[V]] =
      safeGroupMap(_._1)(_._2)

  }

}
