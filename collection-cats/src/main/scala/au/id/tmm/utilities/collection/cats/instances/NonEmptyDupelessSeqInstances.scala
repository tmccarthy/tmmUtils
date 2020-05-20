package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.cats.instances.dupelessSeq._
import au.id.tmm.utilities.collection.{DupelessSeq, NonEmptyDupelessSeq}
import cats.kernel.{Band, Hash}
import cats.syntax.show.toShow
import cats.{Apply, Eval, Monad, NonEmptyTraverse, SemigroupK, Show}

trait NonEmptyDupelessSeqInstances {

  implicit def catsStdHashForNonEmptyDupelessSeq[A : Hash]: Hash[NonEmptyDupelessSeq[A]] = Hash.by(_.underlying)

  implicit def catsStdShowForNonEmptyDupelessSeq[A : Show]: Show[NonEmptyDupelessSeq[A]] = s => s"NonEmpty${s.underlying.show}"

  implicit def catsStdSemigroupForNonEmptyDupelessSeq[A]: Band[NonEmptyDupelessSeq[A]] = _ appendedAll _

  implicit val catsStdInstancesForNonEmptyDupelessSeq: SemigroupK[NonEmptyDupelessSeq] with NonEmptyTraverse[NonEmptyDupelessSeq] = new SemigroupK[NonEmptyDupelessSeq] with NonEmptyTraverse[NonEmptyDupelessSeq] {
    override def combineK[A](x: NonEmptyDupelessSeq[A], y: NonEmptyDupelessSeq[A]): NonEmptyDupelessSeq[A] = x appendedAll y

    override def nonEmptyTraverse[G[_], A, B](fa: NonEmptyDupelessSeq[A])(f: A => G[B])(implicit evidence$1: Apply[G]): G[NonEmptyDupelessSeq[B]] = ???

    override def reduceLeftTo[A, B](fa: NonEmptyDupelessSeq[A])(f: A => B)(g: (B, A) => B): B =
      fa.tail.foldRight[B](f(fa.head)) {
        case (a, b) => g(b, a)
      }

    override def reduceRightTo[A, B](fa: NonEmptyDupelessSeq[A])(f: A => B)(g: (A, Eval[B]) => Eval[B]): Eval[B] =
      fa.init.foldRight[Eval[B]](Eval.now[A](fa.last).map(f))(g)

    override def foldLeft[A, B](fa: NonEmptyDupelessSeq[A], b: B)(f: (B, A) => B): B =
      fa.foldLeft(b)(f)

    override def foldRight[A, B](fa: NonEmptyDupelessSeq[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] =
      fa.foldRight(lb)(f)
  }

  object unlawful {
    import au.id.tmm.utilities.collection.cats.instances.dupelessSeq.unlawful._

    implicit val catsUnlawfulInstancesForNonEmptyDupelessSeq: Monad[NonEmptyDupelessSeq] = new Monad[NonEmptyDupelessSeq] {
      override def flatMap[A, B](fa: NonEmptyDupelessSeq[A])(f: A => NonEmptyDupelessSeq[B]): NonEmptyDupelessSeq[B] = fa.flatMap(f)

      override def tailRecM[A, B](a: A)(f: A => NonEmptyDupelessSeq[Either[A, B]]): NonEmptyDupelessSeq[B] =
        NonEmptyDupelessSeq.fromIterableUnsafe(Monad[DupelessSeq].tailRecM(a)(f.andThen(_.underlying)))

      override def pure[A](x: A): NonEmptyDupelessSeq[A] = NonEmptyDupelessSeq.one(x)
    }
  }

}
