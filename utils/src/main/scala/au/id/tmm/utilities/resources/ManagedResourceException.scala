package au.id.tmm.utilities.resources

final class ManagedResourceException(val exceptions: Vector[Throwable])
  extends Exception ("Managed resource encountered exception", exceptions.headOption.orNull) {

  override def equals(other: Any): Boolean = other match {
    case that: ManagedResourceException =>
      exceptions == that.exceptions
    case _ => false
  }

  override val hashCode: Int = {
    val state = Seq(exceptions)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString = s"ManagedResourceException($exceptions)"
}
