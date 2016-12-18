package au.id.tmm.utilities.io

import java.nio.file.{Files, Path}

import au.id.tmm.utilities.hashing.{Digest, Digester}

import scala.io.Source
import scala.util.Try

object FileUtils {

  implicit class ImprovedPath(path: Path) {

    def sha256Checksum: Try[Digest] = checksum("SHA-256")

    def checksum(algorithm: String): Try[Digest] = Digester(algorithm, Files.newInputStream(path))
      .map(inputStream => while (inputStream.read() >= 0) {})
      .get()
      .map(_._1)

    def source(): Source = Source.fromFile(path.toFile)

  }
}
