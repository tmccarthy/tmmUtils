package au.id.tmm.utilities.files

import java.nio.file.{NoSuchFileException, Path, Paths}

import au.id.tmm.utilities.encoding.EncodingUtils.StringConversions
import au.id.tmm.utilities.files.FileUtils.ImprovedPath
import au.id.tmm.utilities.testing.ImprovedFlatSpec

import scala.util.Success

class FileUtilsSpec extends ImprovedFlatSpec {

  val fileToTest: Path = Paths.get(getClass.getResource("/soliloquy.txt").toURI)
  val missingFile: Path = fileToTest.resolveSibling("missingFile.txt")

  behaviour of "the checksum method"

  it should "correctly give the checksum using SHA256" in {
    val expectedChecksum = "e37f03dc379fe6baa26298120b0f0a32b8bd366e510ce08c9fa350bf97e99e4f".fromHex

    assert(Success(expectedChecksum) === fileToTest.sha256Checksum)
  }

  it should "fail if the file doesn't exist" in {
    intercept[NoSuchFileException] {
      missingFile.sha256Checksum.get
    }
  }
}
