package au.id.tmm.utilities.codec.binarycodecs

import scala.collection.immutable.ArraySeq

trait BytesLike[-A] {

  def unsafeBytes(a: A): Array[Byte]

}

object BytesLike {

  @inline def apply[A : BytesLike]: BytesLike[A] = implicitly[BytesLike[A]]

  implicit val bytesLikeForArray: BytesLike[Array[Byte]]                   = b => b
  implicit val bytesLikeForSpecialisedArraySeq: BytesLike[ArraySeq.ofByte] = _.unsafeArray
  implicit val bytesLikeForArraySeq: BytesLike[ArraySeq[Byte]]             = _.unsafeArray.asInstanceOf[Array[Byte]]
  implicit val bytesLikeForIterable: BytesLike[Iterable[Byte]]             = _.toArray

}
