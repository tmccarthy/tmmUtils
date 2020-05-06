package au.id.tmm.utilities.cats.instances

import cats.{Functor, ~>}

package object valueclasses {

  def deriveFunctor[F[_], G[_] : Functor](
    wrap: G ~> F,
    unwrap: F ~> G,
  ): Functor[F] = new DerivedFunctor(wrap, unwrap)

}
