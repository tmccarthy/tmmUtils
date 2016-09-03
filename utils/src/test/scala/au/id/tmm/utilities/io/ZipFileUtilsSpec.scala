package au.id.tmm.utilities.io

import java.nio.file.Paths
import java.util.zip.ZipFile

import au.id.tmm.utilities.io.ZipFileUtils.{ImprovedPath, ImprovedZipFile}
import au.id.tmm.utilities.testing.ImprovedFlatSpec

import scala.io.Source

class ZipFileUtilsSpec extends ImprovedFlatSpec {
  val zipFilePath = Paths.get(getClass.getResource("/Archive.zip").toURI)
  lazy val zipFile = new ZipFile(zipFilePath.toFile)

  "asZipFile" should "open the file as a zip file" in {
    val zipFile = zipFilePath.asZipFile

    assert(zipFile.getEntry("file1.txt") !== null)
  }

  "entriesIterator" should "iterate through all the entries" in {
    val entriesIterator = zipFile.entriesIterator

    val entryNames = entriesIterator
      .map(_.getName)
      .toList

    assert(entryNames === List("file1.txt", "file2.txt", "file3.txt"))
  }

  "entryWithName" should "return the entry if it is in the file" in {
    val foundEntry = zipFile.entryWithName("file1.txt")

    assert(foundEntry.isDefined)
    assert(foundEntry.get.getName === "file1.txt")
  }

  "entryWithName" should "return None if the entry isn't in the file" in {
    val entry = zipFile.entryWithName("asdf")

    assert(entry === None)
  }

  "openStreamOfEntry" should "return the InputStream of the given entry" in {
    val stream = zipFile.openStreamOfEntry("file3.txt")

    val contents = Source.fromInputStream(stream.get).mkString

    assert(contents === "file3")
  }

  it should "return None if the entry is missing" in {
    val stream = zipFile.openStreamOfEntry("asdf")

    assert(stream === None)
  }
}
