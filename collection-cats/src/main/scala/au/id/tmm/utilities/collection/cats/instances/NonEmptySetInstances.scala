package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.NonEmptySet
import cats.kernel.{CommutativeMonoid, Eq, Semilattice}
import cats.syntax.functor.toFunctorOps
import cats.{Applicative, CommutativeApplicative, Eval, Hash, Monad, SemigroupK, Show, Traverse, UnorderedTraverse}

trait NonEmptySetInstances extends NonEmptySetInstances1 {

  implicit def catsStdHashForTmmUtilsNonEmptySet[A : Hash]: Hash[NonEmptySet[A]] = new Hash[NonEmptySet[A]] {
    override def hash(x: NonEmptySet[A]): Int                       = Hash[Set[A]].hash(x)
    override def eqv(x: NonEmptySet[A], y: NonEmptySet[A]): Boolean = Eq[Set[A]].eqv(x.underlying, y.underlying)
  }

  implicit def catsStdShowForTmmUtilsNonEmptySet[A : Show]: Show[NonEmptySet[A]] =
    s => s.iterator.map(Show[A].show).mkString("NonEmptySet(", ", ", ")")

  object unlawful {
    // TODO should be a non-empty traverse
    implicit val catsUnlawfulInstancesForTmmUtilsNonEmptySet: Traverse[NonEmptySet] with Monad[NonEmptySet] =
      new Traverse[NonEmptySet] with Monad[NonEmptySet] {
        override def traverse[G[_], A, B](
          fa: NonEmptySet[A],
        )(
          f: A => G[B],
        )(implicit
          evidence$1: Applicative[G],
        ): G[NonEmptySet[B]] = {
          // TODO find some way of extracting this
          val unlawfulCommutativeApplicative: CommutativeApplicative[G] = new CommutativeApplicative[G] {
            override def pure[C](x: C): G[C]                     = evidence$1.pure(x)
            override def ap[C, D](ff: G[C => D])(fa: G[C]): G[D] = evidence$1.ap(ff)(fa)
          }

          NonEmptySetInstances.this.catsStdInstancesForNonEmptySet
            .unorderedTraverse(fa)(f)(unlawfulCommutativeApplicative)
        }

        override def foldLeft[A, B](fa: NonEmptySet[A], b: B)(f: (B, A) => B): B = fa.foldLeft(b)(f)

        override def foldRight[A, B](fa: NonEmptySet[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] =
          fa.foldRight(lb)(f)

        override def flatMap[A, B](fa: NonEmptySet[A])(f: A => NonEmptySet[B]): NonEmptySet[B] =
          fa.flatMap(f)

        // TODO implement 😬
        override def tailRecM[A, B](a: A)(f: A => NonEmptySet[Either[A, B]]): NonEmptySet[B] = ???

        override def pure[A](x: A): NonEmptySet[A] = NonEmptySet.one(x)
      }
  }

}

private[instances] trait NonEmptySetInstances1 {

  implicit def catsStdSemilatticeForNonEmptySet[A]: Semilattice[NonEmptySet[A]] = (x, y) => x concat y

  implicit val catsStdInstancesForNonEmptySet: SemigroupK[NonEmptySet] with UnorderedTraverse[NonEmptySet] =
    new SemigroupK[NonEmptySet] with UnorderedTraverse[NonEmptySet] {
      override def combineK[A](x: NonEmptySet[A], y: NonEmptySet[A]): NonEmptySet[A] = x concat y

      override def unorderedTraverse[G[_], A, B](
        sa: NonEmptySet[A],
      )(
        f: A => G[B],
      )(implicit
        evidence$1: CommutativeApplicative[G],
      ): G[NonEmptySet[B]] =
        UnorderedTraverse[Set].unorderedTraverse(sa.underlying)(f).map(NonEmptySet.fromSetUnsafe)

      override def unorderedFoldMap[A, B](fa: NonEmptySet[A])(f: A => B)(implicit evidence$1: CommutativeMonoid[B]): B =
        UnorderedTraverse[Set].unorderedFoldMap(fa.underlying)(f)
    }

}
