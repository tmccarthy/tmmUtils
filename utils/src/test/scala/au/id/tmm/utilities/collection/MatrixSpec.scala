package au.id.tmm.utilities.collection

import au.id.tmm.utilities.testing.ImprovedFlatSpec

class MatrixSpec extends ImprovedFlatSpec {

  "a matrix" can "not have a row that deviates from the specified length" in {
    intercept[IllegalArgumentException] {
      Matrix(
        Vector(1, 2, 3),
        Vector(4, 5, 6, 7),
        Vector(7, 8, 9)
      )
    }
  }

  it should "support lookup by row/column" in {
    val matrix = Matrix(
      Vector(1, 2, 3),
      Vector(4, 5, 6),
      Vector(7, 8, 9)
    )

    assert(matrix(1, 2) === 6)
  }

  it should "support row extraction" in {
    val matrix = Matrix(
      Vector(1, 2, 3),
      Vector(4, 5, 6),
      Vector(7, 8, 9)
    )

    assert(matrix.row(2) === Vector(7, 8, 9))
  }

  it should "support column extraction" in {
    val matrix = Matrix(
      Vector(1, 2, 3),
      Vector(4, 5, 6),
      Vector(7, 8, 9)
    )

    assert(matrix.column(1) === Vector(2, 5, 8))
  }

  it should "record the height" in {
    val matrix = Matrix(
      Vector(1, 2, 3),
      Vector(4, 5, 6)
    )

    assert(matrix.height === 2)
  }

  it should "record the width" in {
    val matrix = Matrix(
      Vector(1, 2, 3),
      Vector(4, 5, 6)
    )

    assert(matrix.width === 3)
  }

  it should "forbid matrices with no rows" in {
    intercept[IllegalArgumentException](Matrix())
  }

  it should "forbid matrices with no columns" in {
    intercept[IllegalArgumentException](Matrix(Vector(), Vector()))
  }
}
