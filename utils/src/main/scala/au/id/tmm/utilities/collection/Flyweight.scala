package au.id.tmm.utilities.collection

import scala.collection.mutable

final class Flyweight[A, B] private (generator: A => B) {
  private val map: mutable.Map[A, B] = mutable.Map()

  def apply(key: A): B = {
    map.synchronized {
      if (map.contains(key)) {
        map(key)
      } else {
        val value = generator(key)
        map.put(key, value)
        value
      }
    }
  }
}

object Flyweight {
  def apply[A, B](generator: A => B): Flyweight[A, B] = new Flyweight[A, B](generator)
}