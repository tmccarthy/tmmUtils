package au.id.tmm.utilities.hashing

import au.id.tmm.utilities.encoding.EncodingUtils.{ArrayConversions, StringConversions}

import scala.collection.mutable

/**
  * The output of a hash function, an array of bytes.
  */
class Digest private (private val bytes: mutable.WrappedArray[Byte]) {

  def asArray(): Array[Byte] = bytes.clone().array

  def asWrappedArray(): mutable.WrappedArray[Byte] = bytes.clone()

  lazy val asHexString: String = bytes.array.toHex

  lazy val asBase64: String = bytes.array.toBase64

  def length: Int = bytes.length

  def canEqual(other: Any): Boolean = other.isInstanceOf[Digest]

  override def equals(other: Any): Boolean = other match {
    case that: Digest =>
      (that canEqual this) &&
        bytes == that.bytes
    case _ => false
  }

  override lazy val hashCode: Int = {
    val state = Seq(bytes: _*)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override lazy val toString: String = s"${getClass.getSimpleName}($asHexString)"
}

object Digest {
  def apply(hexString: String): Digest = new Digest(hexString.fromHex)

  def apply(bytes: Array[Byte]): Digest = Digest(wrapByteArray(bytes))

  def apply(bytes: mutable.WrappedArray[Byte]): Digest = {
    val arrayCopy = bytes.clone()

    new Digest(arrayCopy)
  }
}