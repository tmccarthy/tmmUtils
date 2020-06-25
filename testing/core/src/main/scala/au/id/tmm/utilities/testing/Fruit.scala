package au.id.tmm.utilities.testing

import scala.collection.immutable.ArraySeq

sealed abstract class Fruit(val emoji: String) {
  override def hashCode: Int =
    this match {
      case Fruit.Apple      => 0
      case Fruit.Banana     => 1
      case Fruit.Mango      => 2
      case Fruit.Peach      => 3
      case Fruit.Pear       => 4
      case Fruit.Pineapple  => 5
      case Fruit.Strawberry => 6
      case Fruit.Watermelon => 7
    }

  def name: String = toString
}

object Fruit {
  val ALL: ArraySeq[Fruit] = ArraySeq(Apple, Banana, Mango, Peach, Pear, Pineapple, Strawberry, Watermelon)

  case object Apple      extends Fruit("🍎")
  case object Banana     extends Fruit("🍌")
  case object Mango      extends Fruit("🥭")
  case object Peach      extends Fruit("🍑")
  case object Pear       extends Fruit("🍐")
  case object Pineapple  extends Fruit("🍍")
  case object Strawberry extends Fruit("🍓")
  case object Watermelon extends Fruit("🍉")

  implicit val ordering: Ordering[Fruit] = Ordering.by(_.hashCode)
}
