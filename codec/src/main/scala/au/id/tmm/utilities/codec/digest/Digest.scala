package au.id.tmm.utilities.codec.digest

import java.io.IOException

import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.codec.digest.MessageDigestAlgorithms._

import scala.collection.immutable.ArraySeq

object Digest {

  def digest[A : SafeDigestible](algorithm: String)(a: A): ArraySeq.ofByte = {
    val utils = new DigestUtils(algorithm)

    new ArraySeq.ofByte(implicitly[SafeDigestible[A]].digest(utils, a))
  }

  def digestOrError[A : UnsafeDigestible](algorithm: String)(a: A): Either[IOException, ArraySeq.ofByte] = {
    val utils = new DigestUtils(algorithm)

    implicitly[UnsafeDigestible[A]].digest(utils, a).map(new ArraySeq.ofByte(_))
  }

  def md2[A : SafeDigestible](a: A): ArraySeq.ofByte    = digest(MD2)(a)
  def md5[A : SafeDigestible](a: A): ArraySeq.ofByte    = digest(MD5)(a)
  def sha1[A : SafeDigestible](a: A): ArraySeq.ofByte   = digest(SHA_1)(a)
  def sha224[A : SafeDigestible](a: A): ArraySeq.ofByte = digest(SHA_224)(a)
  def sha256[A : SafeDigestible](a: A): ArraySeq.ofByte = digest(SHA_256)(a)
  def sha384[A : SafeDigestible](a: A): ArraySeq.ofByte = digest(SHA_384)(a)
  def sha512[A : SafeDigestible](a: A): ArraySeq.ofByte = digest(SHA_512)(a)

  /**
    * This algorithm is available from Java 9 onwards
    */
  def sha3_224[A : SafeDigestible](a: A): ArraySeq.ofByte = digest(SHA3_224)(a)

  /**
    * This algorithm is available from Java 9 onwards
    */
  def sha3_256[A : SafeDigestible](a: A): ArraySeq.ofByte = digest(SHA3_256)(a)

  /**
    * This algorithm is available from Java 9 onwards
    */
  def sha3_384[A : SafeDigestible](a: A): ArraySeq.ofByte = digest(SHA3_384)(a)

  /**
    * This algorithm is available from Java 9 onwards
    */
  def sha3_512[A : SafeDigestible](a: A): ArraySeq.ofByte = digest(SHA3_512)(a)

  def md2OrError[A : UnsafeDigestible](a: A): Either[IOException, ArraySeq.ofByte]    = digestOrError(MD2)(a)
  def md5OrError[A : UnsafeDigestible](a: A): Either[IOException, ArraySeq.ofByte]    = digestOrError(MD5)(a)
  def sha1OrError[A : UnsafeDigestible](a: A): Either[IOException, ArraySeq.ofByte]   = digestOrError(SHA_1)(a)
  def sha224OrError[A : UnsafeDigestible](a: A): Either[IOException, ArraySeq.ofByte] = digestOrError(SHA_224)(a)
  def sha256OrError[A : UnsafeDigestible](a: A): Either[IOException, ArraySeq.ofByte] = digestOrError(SHA_256)(a)
  def sha384OrError[A : UnsafeDigestible](a: A): Either[IOException, ArraySeq.ofByte] = digestOrError(SHA_384)(a)
  def sha512OrError[A : UnsafeDigestible](a: A): Either[IOException, ArraySeq.ofByte] = digestOrError(SHA_512)(a)

  /**
    * This algorithm is available from Java 9 onwards
    */
  def sha3_224OrError[A : UnsafeDigestible](a: A): Either[IOException, ArraySeq.ofByte] = digestOrError(SHA3_224)(a)

  /**
    * This algorithm is available from Java 9 onwards
    */
  def sha3_256OrError[A : UnsafeDigestible](a: A): Either[IOException, ArraySeq.ofByte] = digestOrError(SHA3_256)(a)

  /**
    * This algorithm is available from Java 9 onwards
    */
  def sha3_384OrError[A : UnsafeDigestible](a: A): Either[IOException, ArraySeq.ofByte] = digestOrError(SHA3_384)(a)

  /**
    * This algorithm is available from Java 9 onwards
    */
  def sha3_512OrError[A : UnsafeDigestible](a: A): Either[IOException, ArraySeq.ofByte] = digestOrError(SHA3_512)(a)

  trait Syntax {

    implicit class SafeDigestibleOps[-A : SafeDigestible](a: A) {
      def md5: ArraySeq[Byte]    = Digest.md5(a)
      def sha1: ArraySeq[Byte]   = Digest.sha1(a)
      def sha256: ArraySeq[Byte] = Digest.sha256(a)
      def sha512: ArraySeq[Byte] = Digest.sha512(a)

      /**
        * This algorithm is available from Java 9 onwards
        */
      def sha3_256: ArraySeq[Byte] = Digest.sha3_256(a)

      /**
        * This algorithm is available from Java 9 onwards
        */
      def sha3_512: ArraySeq[Byte] = Digest.sha3_512(a)
    }

    implicit class UnsafeDigestibleOps[-A : UnsafeDigestible](a: A) {
      def md5OrError: Either[IOException, ArraySeq[Byte]]    = Digest.md5OrError(a)
      def sha1OrError: Either[IOException, ArraySeq[Byte]]   = Digest.sha1OrError(a)
      def sha256OrError: Either[IOException, ArraySeq[Byte]] = Digest.sha256OrError(a)
      def sha512OrError: Either[IOException, ArraySeq[Byte]] = Digest.sha512OrError(a)

      /**
        * This algorithm is available from Java 9 onwards
        */
      def sha3_256OrError: Either[IOException, ArraySeq[Byte]] = Digest.sha3_256OrError(a)

      /**
        * This algorithm is available from Java 9 onwards
        */
      def sha3_512OrError: Either[IOException, ArraySeq[Byte]] = Digest.sha3_512OrError(a)
    }

  }

}
