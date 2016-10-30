package au.id.tmm.utilities.testing

import java.nio.file.Files

class NeedsCleanDirectorySpec extends ImprovedFlatSpec with NeedsCleanDirectory {

  "the clean directory" should "be clean" in {
    assert(Files.list(cleanDirectory).count() === 0)
  }

}
