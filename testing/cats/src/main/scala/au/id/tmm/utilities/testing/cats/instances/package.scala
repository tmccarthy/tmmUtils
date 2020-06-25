package au.id.tmm.utilities.testing.cats

package object instances {

  object animalInstances       extends AnimalInstances
  object fruitInstances        extends FruitInstances
  object planetInstances       extends PlanetInstances
  object trafficLightInstances extends TrafficLightInstances

  object all extends AnyRef with AnimalInstances with FruitInstances with PlanetInstances with TrafficLightInstances

}
