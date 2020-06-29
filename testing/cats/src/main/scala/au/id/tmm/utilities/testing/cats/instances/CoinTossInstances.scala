package au.id.tmm.utilities.testing.cats.instances

import au.id.tmm.utilities.testing.CoinToss
import cats.laws.discipline.ExhaustiveCheck
import cats.{Hash, Show}

trait CoinTossInstances {

  implicit val tmmUtilsCatsInstancesForCoinToss: Hash[CoinToss] with Show[CoinToss] with ExhaustiveCheck[CoinToss] =
    new Hash[CoinToss] with Show[CoinToss] with ExhaustiveCheck[CoinToss] {
      override def hash(x: CoinToss): Int                 = x.hashCode()
      override def eqv(x: CoinToss, y: CoinToss): Boolean = x == y
      override def show(t: CoinToss): String              = t.name
      override def allValues: List[CoinToss]              = CoinToss.ALL.toList
    }

}
