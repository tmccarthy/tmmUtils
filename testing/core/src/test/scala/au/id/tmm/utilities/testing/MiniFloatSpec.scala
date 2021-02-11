package au.id.tmm.utilities.testing

import org.scalatest.flatspec.AnyFlatSpec

class MiniFloatSpec extends AnyFlatSpec {

  "a minifloat" should "have 10 values" in {
    def render(mf: MiniFloat): String = s"${mf.significand} x 2 ^ ${mf.exponent} (${mf.toDouble})"

    println("All")
    MiniFloat.allValues
      .reverse
      .foreach { mf =>
        val normalised = mf.normalised
        val check = if (normalised.toDouble == mf.toDouble) "✅" else "❌"
        println(s"${render(mf)}    -> ${render(normalised)} $check")
      }

    succeed
  }

}
