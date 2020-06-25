package au.id.tmm.utilities.testing

import scala.collection.immutable.ArraySeq

sealed abstract class Planet(
  /**
    * In astronomical units
    */
  val distanceFromSunAU: Double,
  /**
    * In Earth equivalents
    */
  val massE: Double,
) {
  override def hashCode(): Int =
    this match {
      case Planet.Mercury => 0
      case Planet.Venus   => 1
      case Planet.Earth   => 2
      case Planet.Mars    => 3
      case Planet.Jupiter => 4
      case Planet.Saturn  => 5
      case Planet.Uranus  => 6
      case Planet.Neptune => 7
    }

  def name: String = toString
}

object Planet {

  val ALL: ArraySeq[Planet] = ArraySeq(
    Mercury,
    Venus,
    Earth,
    Mars,
    Jupiter,
    Saturn,
    Uranus,
    Neptune,
  )

  case object Mercury extends Planet(0.38709893d, 0.055d)
  case object Venus   extends Planet(0.72333199d, 0.815d)
  case object Earth   extends Planet(1.00000011d, 1d)
  case object Mars    extends Planet(1.52366231d, 0.107d)
  case object Jupiter extends Planet(5.20336301d, 318d)
  case object Saturn  extends Planet(9.53707032d, 95d)
  case object Uranus  extends Planet(19.19126393d, 14.5d)
  case object Neptune extends Planet(30.06896348d, 17d)

  implicit val ordering: Ordering[Planet] = Ordering.by(_.distanceFromSunAU)

  sealed abstract class Feature(planet: Planet) {
    def name: String = this.toString
  }

  object Feature {

    val ALL: ArraySeq[Feature] = ArraySeq(
      ApollodorusCrater,
      CalorisPlanitia,
      TolstojCrater,
      IshtarTerra,
      AphroditeTerra,
      MaxwellMontes,
      Moon,
      Australia,
      Pacific,
      Antarctica,
      OlympusMons,
      VallesMarineris,
      Phobos,
      Deimos,
      GreatRedSpot,
      Ganymede,
      Europa,
      Io,
      Callisto,
      Titan,
      ARing,
      BRing,
      Titania,
      Oberon,
      Ariel,
      Umbriel,
      Nereid,
      Proteus,
      Triton,
    )

    case object ApollodorusCrater extends Feature(Mercury)
    case object CalorisPlanitia   extends Feature(Mercury)
    case object TolstojCrater     extends Feature(Mercury)
    case object IshtarTerra       extends Feature(Venus)
    case object AphroditeTerra    extends Feature(Venus)
    case object MaxwellMontes     extends Feature(Venus)
    case object Moon              extends Feature(Earth)
    case object Australia         extends Feature(Earth)
    case object Pacific           extends Feature(Earth)
    case object Antarctica        extends Feature(Earth)
    case object OlympusMons       extends Feature(Mars)
    case object VallesMarineris   extends Feature(Mars)
    case object Phobos            extends Feature(Mars)
    case object Deimos            extends Feature(Mars)
    case object GreatRedSpot      extends Feature(Jupiter)
    case object Ganymede          extends Feature(Jupiter)
    case object Europa            extends Feature(Jupiter)
    case object Io                extends Feature(Jupiter)
    case object Callisto          extends Feature(Jupiter)
    case object Titan             extends Feature(Saturn)
    case object ARing             extends Feature(Saturn)
    case object BRing             extends Feature(Saturn)
    case object Titania           extends Feature(Uranus)
    case object Oberon            extends Feature(Uranus)
    case object Ariel             extends Feature(Uranus)
    case object Umbriel           extends Feature(Uranus)
    case object Triton            extends Feature(Neptune)
    case object Proteus           extends Feature(Neptune)
    case object Nereid            extends Feature(Neptune)

  }

}
