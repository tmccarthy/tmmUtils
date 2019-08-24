package au.id.tmm.utilities.codec.digest

import java.io.IOException

import au.id.tmm.utilities.codec.ScalaVersionDependentBytesRepresentation.ByteArray
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.codec.digest.MessageDigestAlgorithms._

object Digest {

  def digest[A : SafeDigestible](algorithm: String)(a: A): ByteArray = ByteArray.wrapUnsafe {
    val utils = new DigestUtils(algorithm)

    implicitly[SafeDigestible[A]].digest(utils, a)
  }

  def digestOrError[A : UnsafeDigestible](algorithm: String)(a: A): Either[IOException, ByteArray] = {
    val utils = new DigestUtils(algorithm)

    implicitly[UnsafeDigestible[A]].digest(utils, a).map(ByteArray.wrapUnsafe)
  }

  def md2[A : SafeDigestible](a: A): ByteArray    = digest(MD2)(a)
  def md5[A : SafeDigestible](a: A): ByteArray    = digest(MD5)(a)
  def sha1[A : SafeDigestible](a: A): ByteArray   = digest(SHA_1)(a)
  def sha224[A : SafeDigestible](a: A): ByteArray = digest(SHA_224)(a)
  def sha256[A : SafeDigestible](a: A): ByteArray = digest(SHA_256)(a)
  def sha384[A : SafeDigestible](a: A): ByteArray = digest(SHA_384)(a)
  def sha512[A : SafeDigestible](a: A): ByteArray = digest(SHA_512)(a)

  def md2OrError[A : UnsafeDigestible](a: A): Either[IOException, ByteArray]    = digestOrError(MD2)(a)
  def md5OrError[A : UnsafeDigestible](a: A): Either[IOException, ByteArray]    = digestOrError(MD5)(a)
  def sha1OrError[A : UnsafeDigestible](a: A): Either[IOException, ByteArray]   = digestOrError(SHA_1)(a)
  def sha224OrError[A : UnsafeDigestible](a: A): Either[IOException, ByteArray] = digestOrError(SHA_224)(a)
  def sha256OrError[A : UnsafeDigestible](a: A): Either[IOException, ByteArray] = digestOrError(SHA_256)(a)
  def sha384OrError[A : UnsafeDigestible](a: A): Either[IOException, ByteArray] = digestOrError(SHA_384)(a)
  def sha512OrError[A : UnsafeDigestible](a: A): Either[IOException, ByteArray] = digestOrError(SHA_512)(a)

  trait Syntax {

    implicit class SafeDigestibleOps[-A : SafeDigestible](a: A) {
      def md5: ByteArray    = Digest.md5(a)
      def sha1: ByteArray   = Digest.sha1(a)
      def sha256: ByteArray = Digest.sha256(a)
      def sha512: ByteArray = Digest.sha512(a)
    }

    implicit class UnsafeDigestibleOps[-A : UnsafeDigestible](a: A) {
      def md5OrError: Either[IOException, ByteArray]    = Digest.md5OrError(a)
      def sha1OrError: Either[IOException, ByteArray]   = Digest.sha1OrError(a)
      def sha256OrError: Either[IOException, ByteArray] = Digest.sha256OrError(a)
      def sha512OrError: Either[IOException, ByteArray] = Digest.sha512OrError(a)
    }

  }

}
