package au.id.tmm.utilities.geo.australia

final case class Address(lines: Vector[String],
                         suburb: String,
                         postcode: Postcode,
                         state: State) {
  def render: String = lines.mkString(" ") + s", $suburb, ${state.abbreviation}, ${postcode.code}"
}