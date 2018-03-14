package au.id.tmm.utilities.io

import java.nio.file.{Files, Path}

import au.id.tmm.utilities.hashing.{Digest, Digester}

import scala.io.Source
import scala.util.Try

object FileUtils {

  implicit class ImprovedPath(path: Path) {

    /**
      * Computes the SHA-256 checksum of this file.
      */
    def sha256Checksum: Try[Digest] = checksum("SHA-256")

    /**
      * Computes a checksum of this file using the given algorithm.
      * @param algorithm the checksum algorithm, as would be provided to [[java.security.MessageDigest#getInstance]]
      */
    def checksum(algorithm: String): Try[Digest] = Digester(algorithm, Files.newInputStream(path))
      .map(inputStream => while (inputStream.read() >= 0) {})
      .get()
      .map(_._1)

    /**
      * Calls [[scala.io.Source#fromFile]] on this path
      */
    def source(): Source = Source.fromFile(path.toFile)

  }
}
