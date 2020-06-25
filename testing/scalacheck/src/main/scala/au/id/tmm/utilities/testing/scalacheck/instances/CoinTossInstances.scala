package au.id.tmm.utilities.testing.scalacheck.instances

import au.id.tmm.utilities.testing.CoinToss
import org.scalacheck.{Arbitrary, Cogen, Gen}

trait CoinTossInstances {

  implicit val tmmUtilsScalacheckArbitraryForCoinToss: Arbitrary[CoinToss] = Arbitrary(
    Gen.oneOf(CoinToss.ALL),
  )
  implicit val tmmUtilsScalacheckCogenForCoinToss: Cogen[CoinToss] = Cogen.cogenInt.contramap(_.hashCode)

}
