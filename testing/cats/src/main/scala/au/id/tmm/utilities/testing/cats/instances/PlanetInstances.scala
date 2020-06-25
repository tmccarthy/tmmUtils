package au.id.tmm.utilities.testing.cats.instances

import au.id.tmm.utilities.testing.Planet
import cats.{Hash, Show}

trait PlanetInstances {

  implicit val tmmUtilsCatsInstancesForPlanet: Hash[Planet] with Show[Planet] = new Hash[Planet] with Show[Planet] {
    override def hash(x: Planet): Int               = x.hashCode()
    override def eqv(x: Planet, y: Planet): Boolean = x == y
    override def show(t: Planet): String            = t.name
  }

  implicit val tmmUtilsCatsInstancesForPlanetFeature: Hash[Planet.Feature] with Show[Planet.Feature] =
    new Hash[Planet.Feature] with Show[Planet.Feature] {
      override def hash(x: Planet.Feature): Int                       = x.hashCode()
      override def eqv(x: Planet.Feature, y: Planet.Feature): Boolean = x == y
      override def show(t: Planet.Feature): String                    = t.name
    }

}
