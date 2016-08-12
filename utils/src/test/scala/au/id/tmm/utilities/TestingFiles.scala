package au.id.tmm.utilities

import java.nio.file.{Path, Paths}

import au.id.tmm.utilities.hashing.Digest

trait TestingFiles {
  val fileToTest: Path = Paths.get(getClass.getResource("/soliloquy.txt").toURI)
  val fileToTestDigest: Digest = Digest("e37f03dc379fe6baa26298120b0f0a32b8bd366e510ce08c9fa350bf97e99e4f")

  val missingFile: Path = fileToTest.resolveSibling("missingFile.txt")
}
