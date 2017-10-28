package au.id.tmm.utilities.geo.australia

final case class State private (name: String,
                                abbreviation: String,
                                isTerritory: Boolean,
                                requiresDefiniteArticle: Boolean = false) extends Ordered[State] {

  override def compare(that: State): Int = State.ordering.compare(this, that)

  override def toString: String = s"${getClass.getSimpleName}($abbreviation)"

  def toNiceString: String = {
    if (requiresDefiniteArticle) {
      s"the $name"
    } else {
      name
    }
  }
}

object State {
  val NSW = State("New South Wales", "NSW", isTerritory = false)
  val QLD = State("Queensland", "QLD", isTerritory = false)
  val SA = State("South Australia", "SA", isTerritory = false)
  val TAS = State("Tasmania", "TAS", isTerritory = false)
  val VIC = State("Victoria", "VIC", isTerritory = false)
  val WA = State("Western Australia", "WA", isTerritory = false)

  val NT = State("Northern Territory", "NT", isTerritory = true, requiresDefiniteArticle = true)
  val ACT = State("Australian Capital Territory", "ACT", isTerritory = true, requiresDefiniteArticle = true)

  val ALL_STATES = Set(NSW, QLD, SA, TAS, VIC, WA, NT, ACT)

  private val abbreviationLookup = ALL_STATES.groupBy(_.abbreviation.toUpperCase).mapValues(_.head)

  def fromAbbreviation(abbreviation: String): Option[State] = abbreviationLookup.get(abbreviation.toUpperCase)

  private val ordering: Ordering[State] = Ordering.by(_.name)
}