package au.id.tmm.utilities

import _root_.cats.MonadError

package object cats {
  type MonadThrowable[F[_]] = MonadError[F, Throwable]
}
