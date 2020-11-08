package au.id.tmm.utilities.codec.digest

import java.io.IOException

import org.apache.commons.codec.digest.DigestUtils

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

  def md2[A : SafeDigestible](a: A): MD2Digest                               = MD2Digest.digest(a)
  def md2OrError[A : UnsafeDigestible](a: A): Either[IOException, MD2Digest] = MD2Digest.digestOrError(a)

  def md5[A : SafeDigestible](a: A): MD5Digest                               = MD5Digest.digest(a)
  def md5OrError[A : UnsafeDigestible](a: A): Either[IOException, MD5Digest] = MD5Digest.digestOrError(a)

  def sha1[A : SafeDigestible](a: A): SHA1Digest                               = SHA1Digest.digest(a)
  def sha1OrError[A : UnsafeDigestible](a: A): Either[IOException, SHA1Digest] = SHA1Digest.digestOrError(a)

  def sha224[A : SafeDigestible](a: A): SHA224Digest                               = SHA224Digest.digest(a)
  def sha224OrError[A : UnsafeDigestible](a: A): Either[IOException, SHA224Digest] = SHA224Digest.digestOrError(a)

  def sha256[A : SafeDigestible](a: A): SHA256Digest                               = SHA256Digest.digest(a)
  def sha256OrError[A : UnsafeDigestible](a: A): Either[IOException, SHA256Digest] = SHA256Digest.digestOrError(a)

  def sha384[A : SafeDigestible](a: A): SHA384Digest                               = SHA384Digest.digest(a)
  def sha384OrError[A : UnsafeDigestible](a: A): Either[IOException, SHA384Digest] = SHA384Digest.digestOrError(a)

  def sha512[A : SafeDigestible](a: A): SHA512Digest                               = SHA512Digest.digest(a)
  def sha512OrError[A : UnsafeDigestible](a: A): Either[IOException, SHA512Digest] = SHA512Digest.digestOrError(a)

  /**
    * This algorithm is available from Java 9 onwards
    */
  def sha3_224[A : SafeDigestible](a: A): SHA3_224Digest = SHA3_224Digest.digest(a)

  /**
    * This algorithm is available from Java 9 onwards
    */
  def sha3_224OrError[A : UnsafeDigestible](a: A): Either[IOException, SHA3_224Digest] = SHA3_224Digest.digestOrError(a)

  /**
    * This algorithm is available from Java 9 onwards
    */
  def sha3_256[A : SafeDigestible](a: A): SHA3_256Digest = SHA3_256Digest.digest(a)

  /**
    * This algorithm is available from Java 9 onwards
    */
  def sha3_256OrError[A : UnsafeDigestible](a: A): Either[IOException, SHA3_256Digest] = SHA3_256Digest.digestOrError(a)

  /**
    * This algorithm is available from Java 9 onwards
    */
  def sha3_384[A : SafeDigestible](a: A): SHA3_384Digest = SHA3_384Digest.digest(a)

  /**
    * This algorithm is available from Java 9 onwards
    */
  def sha3_384OrError[A : UnsafeDigestible](a: A): Either[IOException, SHA3_384Digest] = SHA3_384Digest.digestOrError(a)

  /**
    * This algorithm is available from Java 9 onwards
    */
  def sha3_512[A : SafeDigestible](a: A): SHA3_512Digest = SHA3_512Digest.digest(a)

  /**
    * This algorithm is available from Java 9 onwards
    */
  def sha3_512OrError[A : UnsafeDigestible](a: A): Either[IOException, SHA3_512Digest] = SHA3_512Digest.digestOrError(a)

  trait Syntax {

    implicit class SafeDigestibleOps[-A : SafeDigestible](a: A) {
      def md5: MD5Digest       = Digest.md5(a)
      def sha1: SHA1Digest     = Digest.sha1(a)
      def sha256: SHA256Digest = Digest.sha256(a)
      def sha512: SHA512Digest = Digest.sha512(a)

      /**
        * This algorithm is available from Java 9 onwards
        */
      def sha3_256: SHA3_256Digest = Digest.sha3_256(a)

      /**
        * This algorithm is available from Java 9 onwards
        */
      def sha3_512: SHA3_512Digest = Digest.sha3_512(a)
    }

    implicit class UnsafeDigestibleOps[-A : UnsafeDigestible](a: A) {
      def md5OrError: Either[IOException, MD5Digest]       = Digest.md5OrError(a)
      def sha1OrError: Either[IOException, SHA1Digest]     = Digest.sha1OrError(a)
      def sha256OrError: Either[IOException, SHA256Digest] = Digest.sha256OrError(a)
      def sha512OrError: Either[IOException, SHA512Digest] = Digest.sha512OrError(a)

      /**
        * This algorithm is available from Java 9 onwards
        */
      def sha3_256OrError: Either[IOException, SHA3_256Digest] = Digest.sha3_256OrError(a)

      /**
        * This algorithm is available from Java 9 onwards
        */
      def sha3_512OrError: Either[IOException, SHA3_512Digest] = Digest.sha3_512OrError(a)
    }

  }

}
