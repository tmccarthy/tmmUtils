package au.id.tmm.utilities.codec.digest

import java.io.{File, IOException, InputStream}
import java.nio.ByteBuffer
import java.nio.file.Path

import org.apache.commons.codec.digest.DigestUtils

import scala.collection.immutable.ArraySeq

trait SafeDigestible[-A] {
  protected[digest] def digest(digestUtils: DigestUtils, a: A): Array[Byte]
}

object SafeDigestible {
  @inline def apply[A : SafeDigestible]: SafeDigestible[A] = implicitly[SafeDigestible[A]]

  implicit val forArray: SafeDigestible[Array[Byte]] = (utils, array) => utils.digest(array)
  implicit val forString: SafeDigestible[String]     = (utils, string) => utils.digest(string)
  implicit val forByteArray: SafeDigestible[ArraySeq[Byte]] = (utils, array) =>
    utils.digest(array.unsafeArray.asInstanceOf[Array[Byte]])
  implicit val forIterable: SafeDigestible[Iterable[Byte]] = (utils, iterable) => utils.digest(iterable.toArray)
}

trait UnsafeDigestible[-A] {
  protected def unsafeDigest(digestUtils: DigestUtils, a: A): Array[Byte]

  private[digest] def digest(digestUtils: DigestUtils, a: A): Either[IOException, Array[Byte]] =
    try Right(unsafeDigest(digestUtils, a))
    catch {
      case e: IOException => Left(e)
    }
}

object UnsafeDigestible {
  @inline def apply[A : UnsafeDigestible]: UnsafeDigestible[A] = implicitly[UnsafeDigestible[A]]

  implicit val forFile: UnsafeDigestible[File]               = (utils, file) => utils.digest(file)
  implicit val forPath: UnsafeDigestible[Path]               = (utils, path) => utils.digest(path.toFile)
  implicit val forInputStream: UnsafeDigestible[InputStream] = (utils, is) => utils.digest(is)
  implicit val forByteBuffer: UnsafeDigestible[ByteBuffer]   = (utils, buffer) => utils.digest(buffer)
}
