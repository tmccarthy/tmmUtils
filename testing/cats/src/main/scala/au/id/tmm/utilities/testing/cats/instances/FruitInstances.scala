package au.id.tmm.utilities.testing.cats.instances

import au.id.tmm.utilities.testing.Fruit
import cats.{Hash, Show}

trait FruitInstances {

  implicit val tmmUtilsCatsInstancesForFruit: Hash[Fruit] with Show[Fruit] = new Hash[Fruit] with Show[Fruit] {
    override def hash(x: Fruit): Int              = x.hashCode()
    override def eqv(x: Fruit, y: Fruit): Boolean = x == y
    override def show(t: Fruit): String           = t.name
  }

}
