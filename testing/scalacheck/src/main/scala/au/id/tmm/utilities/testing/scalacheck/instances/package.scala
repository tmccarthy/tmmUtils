package au.id.tmm.utilities.testing.scalacheck

package object instances {

  object animal       extends AnimalInstances
  object fruit        extends FruitInstances
  object planet       extends PlanetInstances
  object trafficLight extends TrafficLightInstances
  object coinToss     extends CoinTossInstances
  object wrapped      extends WrappedInstances

  object all
      extends AnyRef
      with AnimalInstances
      with FruitInstances
      with PlanetInstances
      with TrafficLightInstances
      with CoinTossInstances
      with WrappedInstances

}
