package au.id.tmm.utilities.io

import java.io.InputStream
import java.nio.file.Path
import java.util.zip.{ZipEntry, ZipFile}

object ZipFileUtils {

  implicit class ImprovedPath(path: Path) {
    def asZipFile: ZipFile = new ZipFile(path.toFile)
  }

  implicit class ImprovedZipFile(zipFile: ZipFile) {
    def entriesIterator: Iterator[ZipEntry] = {
      import collection.JavaConverters._

      zipFile.entries().asScala
    }

    def entryWithName(name: String): Option[ZipEntry] = Option(zipFile.getEntry(name))

    def openStreamOfEntry(entryName: String): Option[InputStream] = zipFile
      .entryWithName(entryName)
      .map(zipFile.getInputStream)
  }

}
