package au.id.tmm.utilities.files

import java.nio.file.{Files, Path}
import java.security.{DigestInputStream, MessageDigest}

import resource._

import scala.collection.mutable
import scala.util.Try

object FileUtils {

  implicit class ImprovedPath(path: Path) {

    def sha256Checksum: Try[mutable.WrappedArray[Byte]] = checksum("SHA-256")

    def checksum(algorithm: String): Try[mutable.WrappedArray[Byte]] = Try {
      val messageDigest = MessageDigest.getInstance(algorithm)

      val managedDigestInputStream = managed(Files.newInputStream(path))
        .flatMap(inputStream => managed(new DigestInputStream(inputStream, messageDigest)))

      val digestResult: ExtractableManagedResource[Array[Byte]] = managedDigestInputStream
        .map(digestInputStream => {
          while (digestInputStream.read() >= 0) {}

          messageDigest.digest()
        })

      digestResult.either match {
        case Left(exceptions) => throw exceptions.last
        case Right(result) => result
      }
    }
  }

}
