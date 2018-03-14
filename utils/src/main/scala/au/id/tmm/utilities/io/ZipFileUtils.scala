package au.id.tmm.utilities.io

import java.io.InputStream
import java.nio.file.Path
import java.util.zip.{ZipEntry, ZipFile}

object ZipFileUtils {

  implicit class ImprovedPath(path: Path) {

    /**
      * This file as a [[java.util.zip.ZipEntry]]
      */
    def asZipFile: ZipFile = new ZipFile(path.toFile)

  }

  implicit class ImprovedZipFile(zipFile: ZipFile) {

    /**
      * An iterator over the `ZipEntry` records within this file.
      * @return
      */
    def entriesIterator: Iterator[ZipEntry] = {
      import collection.JavaConverters._

      zipFile.entries().asScala
    }

    /**
      * The `ZipEntry` with the given name
      */
    def entryWithName(name: String): Option[ZipEntry] = Option(zipFile.getEntry(name))

    /**
      * Opens an `InputStream` for the zip entry with the given name
      */
    def openStreamOfEntry(entryName: String): Option[InputStream] = zipFile
      .entryWithName(entryName)
      .map(zipFile.getInputStream)
  }

}
