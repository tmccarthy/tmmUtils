package au.id.tmm.utilities.cats

import cats.effect.IO
import cats.effect.kernel.Resource

package object effect {
  type ResourceIO[+A] = Resource[IO, A]
}
