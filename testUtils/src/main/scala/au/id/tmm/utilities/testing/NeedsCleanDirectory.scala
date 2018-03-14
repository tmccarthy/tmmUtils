package au.id.tmm.utilities.testing

import java.nio.file.{Files, Path}

import org.apache.commons.io.FileUtils
import org.scalatest.{BeforeAndAfterEach, OneInstancePerTest, Suite}

/**
  * A trait that creates a temporary directory, cleaning it before each test execution.
  */
trait NeedsCleanDirectory extends BeforeAndAfterEach { this: Suite with OneInstancePerTest =>

  /**
    * A temporary directory that is cleaned before each test execution.
    */
  val cleanDirectory: Path = Files.createTempDirectory("cleanDir")

  override def beforeEach(): Unit = {
    FileUtils.deleteDirectory(cleanDirectory.toFile)
    Files.createDirectory(cleanDirectory)
    super.beforeEach()
  }
}
