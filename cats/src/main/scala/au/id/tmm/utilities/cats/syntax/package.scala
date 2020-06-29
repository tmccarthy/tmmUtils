package au.id.tmm.utilities.cats

import au.id.tmm.utilities.cats.classes.InvariantK

package object syntax {

  object invariantK extends InvariantK.ToInvariantKOps

  object all extends AnyRef with InvariantK.ToInvariantKOps

}
