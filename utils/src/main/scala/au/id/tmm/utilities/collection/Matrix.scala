package au.id.tmm.utilities.collection

/**
  * An extremely simple implementation of a two-dimensional array.
  */
final case class Matrix[A](rows: Vector[Vector[A]]) {
  val width: Int = {
    val rowWidths = rows.map(_.size).distinct

    val numDistinctRowWidths = rowWidths.size

    numDistinctRowWidths match {
      case x if x > 1 => throw new IllegalArgumentException("Not all rows are the same size")
      case x if x == 1 => rowWidths.head
      case x if x < 1 => throw new IllegalArgumentException("Must have at least one row")
    }
  }

  require(width > 0, "Must have at least 1 column")

  val height: Int = rows.size

  def apply(row: Int, col: Int): A = rows(row)(col)

  def row(row: Int): Vector[A] = rows(row)

  def column(column: Int): Vector[A] = rows.map(_(column))
}

object Matrix {
  def apply[A](rows: Vector[A]*): Matrix[A] = Matrix(rows.toVector)
}