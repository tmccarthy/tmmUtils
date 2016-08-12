package au.id.tmm.utilities.hashing

import java.nio.file.{Files, NoSuchFileException}

import au.id.tmm.utilities.TestingFiles
import au.id.tmm.utilities.testing.ImprovedFlatSpec

class DigesterSpec extends ImprovedFlatSpec with TestingFiles {

  behaviour of "a SHA 256 digester"

  it should "produce the correct digest" in {
    val result = Digester.sha256(Files.newInputStream(fileToTest))
      .map(inputStream => while (inputStream.read() >= 0) {})
      .get()

    assert(result.get._1 === fileToTestDigest)
  }

  it should "return the exception if one is encountered" in {
    val result = Digester.sha256(Files.newInputStream(missingFile))
      .map(inputStream => while (inputStream.read() >= 0) {})
      .get()

    intercept[NoSuchFileException] {
      result.get
    }
  }
}
