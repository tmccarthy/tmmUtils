package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.NonEmptyArraySeq
import cats.data.Ior
import cats.kernel.{Eq, Hash, Semigroup}
import cats.{Align, Apply, Bimonad, Eval, Functor, NonEmptyTraverse, SemigroupK, Show}

trait NonEmptyArraySeqInstances extends NonEmptyArraySeqInstances1 {

  implicit def catsStdHashForTmmUtilsNonEmptyArraySeq[A : Hash]: Hash[NonEmptyArraySeq[A]] =
    new Hash[NonEmptyArraySeq[A]] {
      override def hash(x: NonEmptyArraySeq[A]): Int                            = ???
      override def eqv(x: NonEmptyArraySeq[A], y: NonEmptyArraySeq[A]): Boolean = ???
    }

  implicit def catsStdShowForTmmUtilsNonEmptyArraySeq[A : Show]: Show[NonEmptyArraySeq[A]] =
    new Show[NonEmptyArraySeq[A]] {
      override def show(t: NonEmptyArraySeq[A]): String = ???
    }

  implicit val catsStdInstancesForTmmUtilsNonEmptyArraySeq: NonEmptyTraverse[NonEmptyArraySeq]
    with Bimonad[NonEmptyArraySeq]
    with SemigroupK[NonEmptyArraySeq]
    with Align[NonEmptyArraySeq] = new NonEmptyTraverse[NonEmptyArraySeq] with Bimonad[NonEmptyArraySeq]
  with SemigroupK[NonEmptyArraySeq] with Align[NonEmptyArraySeq] {
    override def nonEmptyTraverse[G[_], A, B](
      fa: NonEmptyArraySeq[A],
    )(
      f: A => G[B],
    )(implicit
      evidence$1: Apply[G],
    ): G[NonEmptyArraySeq[B]] = ???

    override def combineK[A](x: NonEmptyArraySeq[A], y: NonEmptyArraySeq[A]): NonEmptyArraySeq[A] = ???

    override def functor: Functor[NonEmptyArraySeq] = this

    override def align[A, B](fa: NonEmptyArraySeq[A], fb: NonEmptyArraySeq[B]): NonEmptyArraySeq[Ior[A, B]] = ???

    override def foldLeft[A, B](fa: NonEmptyArraySeq[A], b: B)(f: (B, A) => B): B = ???

    override def foldRight[A, B](fa: NonEmptyArraySeq[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] = ???

    override def flatMap[A, B](fa: NonEmptyArraySeq[A])(f: A => NonEmptyArraySeq[B]): NonEmptyArraySeq[B] = ???

    override def tailRecM[A, B](a: A)(f: A => NonEmptyArraySeq[Either[A, B]]): NonEmptyArraySeq[B] = ???

    override def pure[A](x: A): NonEmptyArraySeq[A] = ???

    override def coflatMap[A, B](fa: NonEmptyArraySeq[A])(f: NonEmptyArraySeq[A] => B): NonEmptyArraySeq[B] = ???

    override def reduceLeftTo[A, B](fa: NonEmptyArraySeq[A])(f: A => B)(g: (B, A) => B): B = ???

    override def reduceRightTo[A, B](fa: NonEmptyArraySeq[A])(f: A => B)(g: (A, Eval[B]) => Eval[B]): Eval[B] = ???

    override def extract[A](x: NonEmptyArraySeq[A]): A = ???
  }

  implicit def catsStdSemigroupForTmmUtilsNonEmptyArraySeq[A]: Semigroup[NonEmptyArraySeq[A]] =
    catsStdInstancesForTmmUtilsNonEmptyArraySeq.algebra[A]

}

trait NonEmptyArraySeqInstances1 {
  implicit def catsStdEqForTmmUtilsNonEmptyArraySeq[A : Eq]: Eq[NonEmptyArraySeq[A]] =
    new Eq[NonEmptyArraySeq[A]] {
      override def eqv(x: NonEmptyArraySeq[A], y: NonEmptyArraySeq[A]): Boolean = ???
    }
}
