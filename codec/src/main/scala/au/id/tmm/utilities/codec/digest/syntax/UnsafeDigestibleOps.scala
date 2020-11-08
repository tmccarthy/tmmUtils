package au.id.tmm.utilities.codec.digest.syntax

import java.io.IOException

import au.id.tmm.utilities.codec.digest._

final class UnsafeDigestibleOps[-A : UnsafeDigestible] private (a: A) {

  def md2OrError: Either[IOException, MD2Digest]           = MD2Digest.digestOrError(a)
  def md5OrError: Either[IOException, MD5Digest]           = MD5Digest.digestOrError(a)
  def sha1OrError: Either[IOException, SHA1Digest]         = SHA1Digest.digestOrError(a)
  def sha224OrError: Either[IOException, SHA224Digest]     = SHA224Digest.digestOrError(a)
  def sha256OrError: Either[IOException, SHA256Digest]     = SHA256Digest.digestOrError(a)
  def sha384OrError: Either[IOException, SHA384Digest]     = SHA384Digest.digestOrError(a)
  def sha512OrError: Either[IOException, SHA512Digest]     = SHA512Digest.digestOrError(a)
  def sha3_224OrError: Either[IOException, SHA3_224Digest] = SHA3_224Digest.digestOrError(a)
  def sha3_256OrError: Either[IOException, SHA3_256Digest] = SHA3_256Digest.digestOrError(a)
  def sha3_384OrError: Either[IOException, SHA3_384Digest] = SHA3_384Digest.digestOrError(a)
  def sha3_512OrError: Either[IOException, SHA3_512Digest] = SHA3_512Digest.digestOrError(a)

}

object UnsafeDigestibleOps {
  trait Syntax {
    implicit def toTmmUtilsCodecUnsafeDigestibleOps[A : UnsafeDigestible](a: A): UnsafeDigestibleOps[A] =
      new UnsafeDigestibleOps[A](a)
  }
}
