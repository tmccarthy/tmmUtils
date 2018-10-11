package au.id.tmm.utilities.geo.australia

sealed trait State extends Ordered[State] {
  /**
    * The full name of the state.
    */
  def name: String

  /**
    * The state's abbreviation.
    */
  def abbreviation: String

  /**
    * Whether this is a territory.
    */
  def isTerritory: Boolean

  /**
    * Whether the state name requires a definite article ("<bold>the</bold> ACT").
    */
  def requiresDefiniteArticle: Boolean = false

  override def compare(that: State): Int = State.ordering.compare(this, that)

  override def toString: String = s"$abbreviation"

  /**
    * The full name of the state, with a definite article if needed.
    */
  def toNiceString: String = {
    if (requiresDefiniteArticle) {
      s"the $name"
    } else {
      name
    }
  }
}

object State {
  sealed trait StateProper extends State {
    override val isTerritory: Boolean = false
  }

  sealed trait Territory extends State {
    override def isTerritory: Boolean = true
  }

  case object NSW extends StateProper {
    val name = "New South Wales"
    val abbreviation = "NSW"
  }

  case object QLD extends StateProper {
    val name = "Queensland"
    val abbreviation = "QLD"
  }

  case object SA extends StateProper {
    val name = "South Australia"
    val abbreviation = "SA"
  }

  case object TAS extends StateProper {
    val name = "Tasmania"
    val abbreviation = "TAS"
  }

  case object VIC extends StateProper {
    val name = "Victoria"
    val abbreviation = "VIC"
  }

  case object WA extends StateProper {
    val name = "Western Australia"
    val abbreviation = "WA"
  }

  case object NT extends Territory {
    val name = "Northern Territory"
    val abbreviation = "NT"
    override val requiresDefiniteArticle = true
  }

  case object ACT extends Territory {
    val name = "Australian Capital Territory"
    val abbreviation = "ACT"
    override val requiresDefiniteArticle = true
  }

  val ALL_STATES: Set[State] = Set(NSW, QLD, SA, TAS, VIC, WA, NT, ACT)

  private val abbreviationLookup = ALL_STATES.groupBy(_.abbreviation.toUpperCase).mapValues(_.head)

  def fromAbbreviation(abbreviation: String): Option[State] = abbreviationLookup.get(abbreviation.toUpperCase)

  private val ordering: Ordering[State] = Ordering.by(_.name)
}