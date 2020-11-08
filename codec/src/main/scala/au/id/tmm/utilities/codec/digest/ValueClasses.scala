package au.id.tmm.utilities.codec.digest

import java.io.IOException

import org.apache.commons.codec.digest.MessageDigestAlgorithms

import scala.collection.immutable.ArraySeq

final case class MD2Digest(asBytes: ArraySeq.ofByte)      extends AnyVal
final case class MD5Digest(asBytes: ArraySeq.ofByte)      extends AnyVal
final case class SHA1Digest(asBytes: ArraySeq.ofByte)     extends AnyVal
final case class SHA224Digest(asBytes: ArraySeq.ofByte)   extends AnyVal
final case class SHA256Digest(asBytes: ArraySeq.ofByte)   extends AnyVal
final case class SHA384Digest(asBytes: ArraySeq.ofByte)   extends AnyVal
final case class SHA512Digest(asBytes: ArraySeq.ofByte)   extends AnyVal
final case class SHA3_224Digest(asBytes: ArraySeq.ofByte) extends AnyVal
final case class SHA3_256Digest(asBytes: ArraySeq.ofByte) extends AnyVal
final case class SHA3_384Digest(asBytes: ArraySeq.ofByte) extends AnyVal
final case class SHA3_512Digest(asBytes: ArraySeq.ofByte) extends AnyVal

abstract class DigestValueClassCompanion[D] private[digest] (make: ArraySeq.ofByte => D, algorithm: String) {
  def digest[A : SafeDigestible](a: A): D = make(Digest.digest[A](algorithm)(a))
  def digestOrError[A : UnsafeDigestible](a: A): Either[IOException, D] =
    Digest.digestOrError[A](algorithm)(a).map(make)
}

// scalafmt: { maxColumn = 300 }
object MD2Digest      extends DigestValueClassCompanion[MD2Digest](new MD2Digest(_), MessageDigestAlgorithms.MD2)
object MD5Digest      extends DigestValueClassCompanion[MD5Digest](new MD5Digest(_), MessageDigestAlgorithms.MD5)
object SHA1Digest     extends DigestValueClassCompanion[SHA1Digest](new SHA1Digest(_), MessageDigestAlgorithms.SHA_1)
object SHA224Digest   extends DigestValueClassCompanion[SHA224Digest](new SHA224Digest(_), MessageDigestAlgorithms.SHA_224)
object SHA256Digest   extends DigestValueClassCompanion[SHA256Digest](new SHA256Digest(_), MessageDigestAlgorithms.SHA_256)
object SHA384Digest   extends DigestValueClassCompanion[SHA384Digest](new SHA384Digest(_), MessageDigestAlgorithms.SHA3_384)
object SHA512Digest   extends DigestValueClassCompanion[SHA512Digest](new SHA512Digest(_), MessageDigestAlgorithms.SHA_512)
object SHA3_224Digest extends DigestValueClassCompanion[SHA3_224Digest](new SHA3_224Digest(_), MessageDigestAlgorithms.SHA3_224)
object SHA3_256Digest extends DigestValueClassCompanion[SHA3_256Digest](new SHA3_256Digest(_), MessageDigestAlgorithms.SHA3_256)
object SHA3_384Digest extends DigestValueClassCompanion[SHA3_384Digest](new SHA3_384Digest(_), MessageDigestAlgorithms.SHA3_384)
object SHA3_512Digest extends DigestValueClassCompanion[SHA3_512Digest](new SHA3_512Digest(_), MessageDigestAlgorithms.SHA3_512)
