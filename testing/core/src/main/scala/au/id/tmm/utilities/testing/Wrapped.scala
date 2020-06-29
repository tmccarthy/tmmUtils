package au.id.tmm.utilities.testing

final case class Wrapped[A](unwrap: A)

final case class WrappedK[F[_], A](unwrap: F[A])
