package au.id.tmm.utilities.files

import java.nio.file.NoSuchFileException

import au.id.tmm.utilities.TestingFiles
import au.id.tmm.utilities.files.FileUtils.ImprovedPath
import au.id.tmm.utilities.testing.ImprovedFlatSpec

import scala.util.Success

class FileUtilsSpec extends ImprovedFlatSpec with TestingFiles {

  behaviour of "the checksum method"

  it should "correctly give the checksum using SHA256" in {
    assert(Success(fileToTestDigest) === fileToTest.sha256Checksum)
  }

  it should "fail if the file doesn't exist" in {
    intercept[NoSuchFileException] {
      missingFile.sha256Checksum.get
    }
  }
}
