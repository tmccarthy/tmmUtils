package au.id.tmm.utilities.collection

import scala.collection.BuildFrom

package object syntax {

  implicit def iteratorOps[A](iterator: Iterator[A]): IteratorOps[A] = new IteratorOps[A](iterator)

  implicit def seqOps[C[_] <: scala.collection.Seq[_], A](seq: C[A])(implicit buildFrom: BuildFrom[C[A], A, C[A]]): SeqOps[C, A] =
    new SeqOps[C, A](seq)

}
