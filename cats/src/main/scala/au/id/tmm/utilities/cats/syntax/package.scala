package au.id.tmm.utilities.cats

package object syntax {
  object list {
    implicit def toListSyntax[A](list: List[A]): ListSyntax[A] = new ListSyntax[A](list)
  }

  object vector {
    implicit def toVectorSyntax[A](vector: Vector[A]): VectorSyntax[A] = new VectorSyntax[A](vector)
  }
}
