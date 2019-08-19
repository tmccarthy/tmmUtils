package au.id.tmm.utilities.testing

import java.nio.file.Files

import org.scalatest.{FlatSpec, OneInstancePerTest}

class NeedsCleanDirectorySpec extends FlatSpec with OneInstancePerTest with NeedsCleanDirectory {

  "the clean directory" should "be clean" in {
    assert(Files.list(cleanDirectory).count() === 0)
  }

}
