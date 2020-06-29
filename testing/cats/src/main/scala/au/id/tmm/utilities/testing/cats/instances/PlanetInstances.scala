package au.id.tmm.utilities.testing.cats.instances

import au.id.tmm.utilities.testing.Planet
import cats.laws.discipline.ExhaustiveCheck
import cats.{Hash, Show}

trait PlanetInstances {

  implicit val tmmUtilsCatsInstancesForPlanet: Hash[Planet] with Show[Planet] with ExhaustiveCheck[Planet] =
    new Hash[Planet] with Show[Planet] with ExhaustiveCheck[Planet] {
      override def hash(x: Planet): Int               = x.hashCode()
      override def eqv(x: Planet, y: Planet): Boolean = x == y
      override def show(t: Planet): String            = t.name
      override def allValues: List[Planet]            = Planet.ALL.toList
    }

  implicit val tmmUtilsCatsInstancesForPlanetFeature
    : Hash[Planet.Feature] with Show[Planet.Feature] with ExhaustiveCheck[Planet.Feature] =
    new Hash[Planet.Feature] with Show[Planet.Feature] with ExhaustiveCheck[Planet.Feature] {
      override def hash(x: Planet.Feature): Int                       = x.hashCode()
      override def eqv(x: Planet.Feature, y: Planet.Feature): Boolean = x == y
      override def show(t: Planet.Feature): String                    = t.name
      override def allValues: List[Planet.Feature]                    = Planet.Feature.ALL.toList
    }

}
