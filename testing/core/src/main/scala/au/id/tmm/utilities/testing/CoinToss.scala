package au.id.tmm.utilities.testing

import scala.collection.immutable.ArraySeq

sealed abstract class CoinToss {
  def name: String = toString

  override def hashCode(): Int =
    this match {
      case CoinToss.Heads => 0
      case CoinToss.Tails => 1
    }
}

object CoinToss {
  val ALL = ArraySeq(Heads, Tails)

  case object Heads extends CoinToss
  case object Tails extends CoinToss

  implicit val ordering: Ordering[CoinToss] = Ordering.by(_.hashCode())
}
