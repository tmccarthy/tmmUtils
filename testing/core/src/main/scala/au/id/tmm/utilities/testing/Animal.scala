package au.id.tmm.utilities.testing

import scala.collection.immutable.ArraySeq

sealed abstract class Animal(val category: Animal.Category) {
  def name: String = toString

  override def hashCode(): Int =
    this match {
      case Animal.Tiger     => 0
      case Animal.Lion      => 1
      case Animal.Puma      => 2
      case Animal.Eagle     => 3
      case Animal.Flamingo  => 4
      case Animal.Crow      => 5
      case Animal.Snake     => 6
      case Animal.Lizard    => 7
      case Animal.Crocodile => 8
      case Animal.Tuna      => 9
      case Animal.Shark     => 10
      case Animal.Minnow    => 11
      case Animal.Koala     => 12
      case Animal.Kangaroo  => 13
      case Animal.Wombat    => 14
      case Animal.Crab      => 15
      case Animal.Shrimp    => 16
      case Animal.Lobster   => 17
      case Animal.Ant       => 18
      case Animal.Butterfly => 19
      case Animal.Bee       => 20
    }
}

object Animal {

  val ALL: ArraySeq[Animal] = ArraySeq(
    Tiger,
    Lion,
    Puma,
    Eagle,
    Flamingo,
    Crow,
    Snake,
    Lizard,
    Crocodile,
    Tuna,
    Shark,
    Minnow,
    Koala,
    Kangaroo,
    Wombat,
    Crab,
    Shrimp,
    Lobster,
    Ant,
    Butterfly,
    Bee,
  )

  case object Tiger     extends Animal(Category.BigCat)
  case object Lion      extends Animal(Category.BigCat)
  case object Puma      extends Animal(Category.BigCat)
  case object Eagle     extends Animal(Category.Bird)
  case object Flamingo  extends Animal(Category.Bird)
  case object Crow      extends Animal(Category.Bird)
  case object Snake     extends Animal(Category.Reptile)
  case object Lizard    extends Animal(Category.Reptile)
  case object Crocodile extends Animal(Category.Reptile)
  case object Tuna      extends Animal(Category.Fish)
  case object Shark     extends Animal(Category.Fish)
  case object Minnow    extends Animal(Category.Fish)
  case object Koala     extends Animal(Category.Marsupial)
  case object Kangaroo  extends Animal(Category.Marsupial)
  case object Wombat    extends Animal(Category.Marsupial)
  case object Crab      extends Animal(Category.Crustacean)
  case object Shrimp    extends Animal(Category.Crustacean)
  case object Lobster   extends Animal(Category.Crustacean)
  case object Ant       extends Animal(Category.Insect)
  case object Butterfly extends Animal(Category.Insect)
  case object Bee       extends Animal(Category.Insect)

  sealed trait Category {
    override def hashCode(): Int =
      this match {
        case Category.BigCat     => 0
        case Category.Bird       => 1
        case Category.Reptile    => 2
        case Category.Fish       => 3
        case Category.Marsupial  => 4
        case Category.Crustacean => 5
        case Category.Insect     => 6
      }

    def name: String = this.toString
  }

  object Category {
    val ALL: ArraySeq[Category] = ArraySeq(
      BigCat,
      Bird,
      Reptile,
      Fish,
      Marsupial,
      Crustacean,
      Insect,
    )

    case object BigCat     extends Category
    case object Bird       extends Category
    case object Reptile    extends Category
    case object Fish       extends Category
    case object Marsupial  extends Category
    case object Crustacean extends Category
    case object Insect     extends Category
  }

}
