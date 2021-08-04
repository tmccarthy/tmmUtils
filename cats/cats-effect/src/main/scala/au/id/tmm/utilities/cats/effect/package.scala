package au.id.tmm.utilities.cats

import cats.effect.IO
import cats.effect.kernel.{Ref, Resource}
import cats.effect.std.Semaphore

package object effect {
  type ResourceIO[+A] = Resource[IO, A]
  type RefIO[A]       = Ref[IO, A]
  type SemaphoreIO    = Semaphore[IO]
}
