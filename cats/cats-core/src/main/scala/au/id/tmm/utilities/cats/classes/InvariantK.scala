package au.id.tmm.utilities.cats.classes

import cats.{
  Alternative,
  Applicative,
  Eval,
  Foldable,
  Functor,
  Invariant,
  Monad,
  MonadError,
  Monoid,
  MonoidK,
  Semigroup,
  SemigroupK,
  Traverse,
  ~>,
}

trait InvariantK[T[_[_]]] {

  def imapK[F[_], G[_]](F: T[F])(fFG: F ~> G)(fGF: G ~> F): T[G]

}

object InvariantK {

  def apply[T[_[_]]](implicit T: InvariantK[T]): InvariantK[T] = T

  // format: off

  implicit val tmmUtilsInvariantKForInvariant: InvariantK[Invariant] = new InvariantK[Invariant] {
    override def imapK[F[_], G[_]](Fparam: Invariant[F])(fFGparam: F ~> G)(fGFparam: G ~> F): Invariant[G] = new DerivedInvariant[F, G] {
      override protected def F: Invariant[F] = Fparam
      override protected def fFG: F ~> G = fFGparam
      override protected def fGF: G ~> F = fGFparam
    }
  }

  implicit val tmmUtilsInvariantKForSemigroupK: InvariantK[SemigroupK] = new InvariantK[SemigroupK] {
    override def imapK[F[_], G[_]](Fparam: SemigroupK[F])(fFGparam: F ~> G)(fGFparam: G ~> F): SemigroupK[G] = new DerivedSemigroupK[F, G] {
      override protected def F: SemigroupK[F] = Fparam
      override protected def fFG: F ~> G = fFGparam
      override protected def fGF: G ~> F = fGFparam
    }
  }

  implicit val tmmUtilsInvariantKForMonoidK: InvariantK[MonoidK] = new InvariantK[MonoidK] {
    override def imapK[F[_], G[_]](Fparam: MonoidK[F])(fFGparam: F ~> G)(fGFparam: G ~> F): MonoidK[G] = new DerivedMonoidK[F, G] {
      override protected def F: MonoidK[F] = Fparam
      override protected def fFG: F ~> G = fFGparam
      override protected def fGF: G ~> F = fGFparam
    }
  }

  implicit val tmmUtilsInvariantKForFunctor: InvariantK[Functor] = new InvariantK[Functor] {
    override def imapK[F[_], G[_]](Fparam: Functor[F])(fFGparam: F ~> G)(fGFparam: G ~> F): Functor[G] = new DerivedFunctor[F, G] {
      override protected def F: Functor[F] = Fparam
      override protected def fFG: F ~> G = fFGparam
      override protected def fGF: G ~> F = fGFparam
    }
  }

  implicit val tmmUtilsInvariantKForApplicative: InvariantK[Applicative] = new InvariantK[Applicative] {
    override def imapK[F[_], G[_]](Fparam: Applicative[F])(fFGparam: F ~> G)(fGFparam: G ~> F): Applicative[G] = new DerivedApplicative[F, G] {
      override protected def F: Applicative[F] = Fparam
      override protected def fFG: F ~> G = fFGparam
      override protected def fGF: G ~> F = fGFparam
    }
  }

  implicit val tmmUtilsInvariantKForMonad: InvariantK[Monad] = new InvariantK[Monad] {
    override def imapK[F[_], G[_]](Fparam: Monad[F])(fFGparam: F ~> G)(fGFparam: G ~> F): Monad[G] = new DerivedMonad[F, G] {
      override protected def F: Monad[F] = Fparam
      override protected def fFG: F ~> G = fFGparam
      override protected def fGF: G ~> F = fGFparam
    }
  }

  implicit def tmmUtilsInvariantKForMonadError[E]: InvariantK[MonadError[*[_], E]] = new InvariantK[MonadError[*[_], E]] {
    override def imapK[F[_], G[_]](Fparam: MonadError[F, E])(fFGparam: F ~> G)(fGFparam: G ~> F): MonadError[G, E] = new DerivedMonadError[F, G, E] {
      override protected def F: MonadError[F, E] = Fparam
      override protected def fFG: F ~> G = fFGparam
      override protected def fGF: G ~> F = fGFparam
    }
  }

  implicit val tmmUtilsInvariantKForAlternative: InvariantK[Alternative] = new InvariantK[Alternative] {
    override def imapK[F[_], G[_]](Fparam: Alternative[F])(fFGparam: F ~> G)(fGFparam: G ~> F): Alternative[G] = new DerivedAlternative[F, G] {
      override protected def F: Alternative[F] = Fparam
      override protected def fFG: F ~> G = fFGparam
      override protected def fGF: G ~> F = fGFparam
    }
  }

  implicit val tmmUtilsInvariantKForFoldable: InvariantK[Foldable] = new InvariantK[Foldable] {
    override def imapK[F[_], G[_]](Fparam: Foldable[F])(fFGparam: F ~> G)(fGFparam: G ~> F): Foldable[G] = new DerivedFoldable[F, G] {
      override protected def F: Foldable[F] = Fparam
      override protected def fFG: F ~> G = fFGparam
      override protected def fGF: G ~> F = fGFparam
    }
  }

  implicit val tmmUtilsInvariantKForTraverse: InvariantK[Traverse] = new InvariantK[Traverse] {
    override def imapK[F[_], G[_]](Fparam: Traverse[F])(fFGparam: F ~> G)(fGFparam: G ~> F): Traverse[G] = new DerivedTraverse[F, G] {
      override protected def F: Traverse[F] = Fparam
      override protected def fFG: F ~> G = fFGparam
      override protected def fGF: G ~> F = fGFparam
    }
  }

  private[classes] trait DerivedTypeclass[+T[_[_]], F[_], G[_]] {
    protected def F: T[F]
    protected def fFG: F ~> G
    protected def fGF: G ~> F
  }

  private[classes] trait DerivedInvariant[F[_], G[_]] extends DerivedTypeclass[Invariant, F, G] with Invariant[G] {
    override def imap[A, B](fa: G[A])(f: A => B)(g: B => A): G[B] = fFG(F.imap(fGF(fa))(f)(g))
  }

  private[classes] trait DerivedSemigroupK[F[_], G[_]] extends DerivedTypeclass[SemigroupK, F, G] with SemigroupK[G] {
    override def combineK[A](x: G[A], y: G[A]): G[A] = fFG(F.combineK(fGF(x), fGF(y)))
    override def algebra[A]: Semigroup[G[A]] = new Semigroup[G[A]] {
      private val semigroupFA: Semigroup[F[A]] = F.algebra
      override def combine(x: G[A], y: G[A]): G[A] = fFG(semigroupFA.combine(fGF(x), fGF(y)))
      override def combineAllOption(as: IterableOnce[G[A]]): Option[G[A]] = semigroupFA.combineAllOption(as.iterator.map(fGF.apply[A])).map(fFG.apply[A])
    }
  }

  private[classes] trait DerivedMonoidK[F[_], G[_]] extends DerivedTypeclass[MonoidK, F, G] with DerivedSemigroupK[F, G] with MonoidK[G] {
    override def empty[A]: G[A] = fFG(F.empty)
    override def algebra[A]: Monoid[G[A]] = new Monoid[G[A]] {
      private val monoidFA: Monoid[F[A]] = F.algebra
      override def combine(x: G[A], y: G[A]): G[A] = fFG(monoidFA.combine(fGF(x), fGF(y)))
      override def empty: G[A] = fFG(monoidFA.empty)
      override def combineAll(as: IterableOnce[G[A]]): G[A] = fFG(monoidFA.combineAll(as.iterator.map(fGF.apply[A])))
    }
  }

  private[classes] trait DerivedFunctor[F[_], G[_]] extends DerivedTypeclass[Functor, F, G] with DerivedInvariant[F, G] with Functor[G] {
    override def map[A, B](ga: G[A])(f: A => B): G[B] = fFG(F.map(fGF(ga))(f))
  }

  private[classes] trait DerivedApplicative[F[_], G[_]] extends DerivedTypeclass[Applicative, F, G] with DerivedFunctor[F, G] with Applicative[G] {
    override def pure[A](x: A): G[A] = fFG(F.pure(x))
    override def ap[A, B](ff: G[A => B])(ga: G[A]): G[B] = fFG(F.ap(fGF(ff))(fGF(ga)))
  }

  private[classes] trait DerivedMonad[F[_], G[_]] extends DerivedTypeclass[Monad, F, G] with DerivedApplicative[F, G] with Monad[G] {
    override def flatMap[A, B](ga: G[A])(f: A => G[B]): G[B] = fFG(F.flatMap(fGF(ga))(f.andThen(fGF.apply[B])))
    override def tailRecM[A, B](a: A)(f: A => G[Either[A, B]]): G[B] = fFG(F.tailRecM(a)(f.andThen(fGF.apply[Either[A, B]])))
  }

  private[classes] trait DerivedMonadError[F[_], G[_], E] extends DerivedTypeclass[MonadError[*[_], E], F, G] with DerivedMonad[F, G] with MonadError[G, E] {
    override def raiseError[A](e: E): G[A] = fFG(F.raiseError(e))
    override def handleErrorWith[A](ga: G[A])(f: E => G[A]): G[A] = fFG(F.handleErrorWith(fGF(ga))(f.andThen(fGF.apply[A])))
  }

  private[classes] trait DerivedAlternative[F[_], G[_]] extends DerivedTypeclass[Alternative, F, G] with DerivedApplicative[F, G] with DerivedMonoidK[F, G] with Alternative[G]

  private[classes] trait DerivedFoldable[F[_], G[_]] extends DerivedTypeclass[Foldable, F, G] with Foldable[G] {
    override def foldLeft[A, B](ga: G[A], b: B)(f: (B, A) => B): B = F.foldLeft(fGF(ga), b)(f)
    override def foldRight[A, B](ga: G[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] = F.foldRight(fGF(ga), lb)(f)
  }

  private[classes] trait DerivedTraverse[F[_], G[_]] extends DerivedTypeclass[Traverse, F, G] with DerivedFoldable[F, G] with Traverse[G] {
    override def traverse[GG[_], A, B](ga: G[A])(f: A => GG[B])(implicit GG: Applicative[GG]): GG[G[B]] = GG.map(F.traverse[GG, A, B](fGF(ga))(f))(fFG.apply[B])
  }

  // format: on

  final class Ops[T[_[_]], F[_]] private[InvariantK] (invariantK: InvariantK[T], tf: T[F]) {
    def imapK[G[_]](fFG: F ~> G)(fGF: G ~> F): T[G] = invariantK.imapK(tf)(fFG)(fGF)
  }

  trait ToInvariantKOps {
    implicit def toInvariantKOps[T[_[_]], F[_]](tf: T[F])(implicit invariantK: InvariantK[T]): InvariantK.Ops[T, F] =
      new InvariantK.Ops[T, F](invariantK, tf)
  }

}
