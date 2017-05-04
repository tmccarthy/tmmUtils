package au.id.tmm.utilities.hashing

import java.io.InputStream
import java.security.{DigestInputStream, MessageDigest}

import resource._

import scala.util.{Failure, Success, Try}

class Digester[A] private(checksumAlgorithm: String,
                          inputStream: => InputStream,
                          mapFunction: InputStream => A) {

  def map[B](mapFunction: A => B): Digester[B] =
    new Digester[B](checksumAlgorithm, inputStream, stream => mapFunction(this.mapFunction(stream)))

  def get(): Try[(Digest, A)] = Try {

    val digest = MessageDigest.getInstance(checksumAlgorithm)

    val outcome = managed(inputStream)
      .flatMap(managedInputStream => managed(new DigestInputStream(managedInputStream, digest)))
      .map(mapFunction)
      .either

    outcome.either match {
      case Right(result) => Success((Digest(digest.digest()), result))
      case Left(exceptions) => Failure(exceptions.last)
    }
  }.flatten
}

object Digester {
  def apply(algorithm: String, inputStream: => InputStream): Digester[InputStream] =
    new Digester[InputStream](algorithm, inputStream, x => x)

  def sha256(inputStream: => InputStream): Digester[InputStream] = Digester("SHA-256", inputStream)
}