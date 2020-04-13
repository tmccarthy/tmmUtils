package au.id.tmm.utilities.valueclasses

private[valueclasses] class DerivedOrdering[A, O : Ordering](asOrdered: A => O) extends Ordering[A] {
  private val orderingForO: Ordering[O] = implicitly

  @inline override def compare(x: A, y: A): Int = orderingForO.compare(asOrdered(x), asOrdered(y))
}
