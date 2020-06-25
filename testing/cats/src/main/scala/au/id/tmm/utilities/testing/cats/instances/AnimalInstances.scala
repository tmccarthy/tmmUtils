package au.id.tmm.utilities.testing.cats.instances

import au.id.tmm.utilities.testing.Animal
import cats.{Hash, Show}

trait AnimalInstances {

  implicit val tmmUtilsCatsInstancesForAnimal: Hash[Animal] with Show[Animal] = new Hash[Animal] with Show[Animal] {
    override def hash(x: Animal): Int               = x.hashCode()
    override def eqv(x: Animal, y: Animal): Boolean = x == y
    override def show(t: Animal): String            = t.name
  }

  implicit val tmmUtilsCatsInstancesForAnimalCategory: Hash[Animal.Category] with Show[Animal.Category] =
    new Hash[Animal.Category] with Show[Animal.Category] {
      override def hash(x: Animal.Category): Int                        = x.hashCode()
      override def eqv(x: Animal.Category, y: Animal.Category): Boolean = x == y
      override def show(t: Animal.Category): String                     = t.name
    }

}
