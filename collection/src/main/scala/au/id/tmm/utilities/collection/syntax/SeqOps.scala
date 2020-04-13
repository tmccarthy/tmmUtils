package au.id.tmm.utilities.collection.syntax

import scala.collection.BuildFrom

final class SeqOps[C[_] <: scala.collection.Seq[_], A] private[syntax] (seq: C[A])(implicit buildFrom: BuildFrom[C[A], A, C[A]]) {
  def everyNth(n: Int): C[A] =
    buildFrom.fromSpecific(seq)(Range(0, seq.length, n).iterator.map(i => seq.apply(i).asInstanceOf[A]))
}
