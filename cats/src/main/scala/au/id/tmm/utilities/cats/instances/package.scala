package au.id.tmm.utilities.cats

package object instances {
  object numeric extends NumericInstances

  object all extends AnyRef with NumericInstances
}
