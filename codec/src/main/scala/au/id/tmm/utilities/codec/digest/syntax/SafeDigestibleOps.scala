package au.id.tmm.utilities.codec.digest.syntax

import au.id.tmm.utilities.codec.digest.{SafeDigestible, _}

final class SafeDigestibleOps[-A : SafeDigestible] private (a: A) {

  def md2: MD2Digest           = MD2Digest.digest(a)
  def md5: MD5Digest           = MD5Digest.digest(a)
  def sha1: SHA1Digest         = SHA1Digest.digest(a)
  def sha224: SHA224Digest     = SHA224Digest.digest(a)
  def sha256: SHA256Digest     = SHA256Digest.digest(a)
  def sha384: SHA384Digest     = SHA384Digest.digest(a)
  def sha512: SHA512Digest     = SHA512Digest.digest(a)
  def sha3_224: SHA3_224Digest = SHA3_224Digest.digest(a)
  def sha3_256: SHA3_256Digest = SHA3_256Digest.digest(a)
  def sha3_384: SHA3_384Digest = SHA3_384Digest.digest(a)
  def sha3_512: SHA3_512Digest = SHA3_512Digest.digest(a)

}

object SafeDigestibleOps {
  trait Syntax {
    implicit def toTmmUtilsCodecSafeDigestibleOps[A: SafeDigestible](a: A): SafeDigestibleOps[A] = new SafeDigestibleOps[A](a)
  }
}
