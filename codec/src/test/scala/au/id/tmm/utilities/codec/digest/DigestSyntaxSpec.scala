package au.id.tmm.utilities.codec.digest

import java.io.{ByteArrayInputStream, InputStream}
import java.nio.ByteBuffer
import java.nio.file.Files

import au.id.tmm.utilities.codec.ScalaVersionDependentBytesRepresentation.ByteArray
import au.id.tmm.utilities.codec.binarycodecs._
import org.scalatest.FlatSpec

class DigestSyntaxSpec extends FlatSpec {

  private val string     = "hello world"
  private def array      = "hello world".getBytes
  private val byteArray  = ByteArray.wrapUnsafe(array)
  private val byteVector = array.toVector

  private def byteBuffer               = ByteBuffer.wrap(array)
  private def inputStream: InputStream = new ByteArrayInputStream(array)
  private def path                     = Files.write(Files.createTempFile(getClass.getName, "txt"), array)
  private def file                     = path.toFile

  behavior of "The syntax to compute a MD5 hash"

  private val expectedMd5Hash = hex"5eb63bbbe01eeed093cb22bb8f5acdc3"

  it should "work for a string" in assert(string.md5 === expectedMd5Hash)
  it should "work for a array" in assert(array.md5 === expectedMd5Hash)
  it should "work for a byteArray" in assert(byteArray.md5 === expectedMd5Hash)
  it should "work for a byteVector" in assert(byteVector.md5 === expectedMd5Hash)
  it should "work for a byteBuffer" in assert(byteBuffer.md5OrError === Right(expectedMd5Hash))
  it should "work for a inputStream" in assert(inputStream.md5OrError === Right(expectedMd5Hash))
  it should "work for a path" in assert(path.md5OrError === Right(expectedMd5Hash))
  it should "work for a file" in assert(file.md5OrError === Right(expectedMd5Hash))

  behavior of "The syntax to compute a SHA1 hash"

  private val expectedSha1Hash = hex"2aae6c35c94fcfb415dbe95f408b9ce91ee846ed"

  it should "work for a string" in assert(string.sha1 === expectedSha1Hash)
  it should "work for a array" in assert(array.sha1 === expectedSha1Hash)
  it should "work for a byteArray" in assert(byteArray.sha1 === expectedSha1Hash)
  it should "work for a byteVector" in assert(byteVector.sha1 === expectedSha1Hash)
  it should "work for a byteBuffer" in assert(byteBuffer.sha1OrError === Right(expectedSha1Hash))
  it should "work for a inputStream" in assert(inputStream.sha1OrError === Right(expectedSha1Hash))
  it should "work for a path" in assert(path.sha1OrError === Right(expectedSha1Hash))
  it should "work for a file" in assert(file.sha1OrError === Right(expectedSha1Hash))

  behavior of "The syntax to compute a SHA256 hash"

  private val expectedSha256Hash = hex"b94d27b9934d3e08a52e52d7da7dabfac484efe37a5380ee9088f7ace2efcde9"

  it should "work for a string" in assert(string.sha256 === expectedSha256Hash)
  it should "work for a array" in assert(array.sha256 === expectedSha256Hash)
  it should "work for a byteArray" in assert(byteArray.sha256 === expectedSha256Hash)
  it should "work for a byteVector" in assert(byteVector.sha256 === expectedSha256Hash)
  it should "work for a byteBuffer" in assert(byteBuffer.sha256OrError === Right(expectedSha256Hash))
  it should "work for a inputStream" in assert(inputStream.sha256OrError === Right(expectedSha256Hash))
  it should "work for a path" in assert(path.sha256OrError === Right(expectedSha256Hash))
  it should "work for a file" in assert(file.sha256OrError === Right(expectedSha256Hash))

  behavior of "The syntax to compute a SHA512 hash"

  private val expectedSha512Hash =
    hex"309ecc489c12d6eb4cc40f50c902f2b4d0ed77ee511a7c7a9bcd3ca86d4cd86f989dd35bc5ff499670da34255b45b0cfd830e81f605dcf7dc5542e93ae9cd76f"

  it should "work for a string" in assert(string.sha512 === expectedSha512Hash)
  it should "work for a array" in assert(array.sha512 === expectedSha512Hash)
  it should "work for a byteArray" in assert(byteArray.sha512 === expectedSha512Hash)
  it should "work for a byteVector" in assert(byteVector.sha512 === expectedSha512Hash)
  it should "work for a byteBuffer" in assert(byteBuffer.sha512OrError === Right(expectedSha512Hash))
  it should "work for a inputStream" in assert(inputStream.sha512OrError === Right(expectedSha512Hash))
  it should "work for a path" in assert(path.sha512OrError === Right(expectedSha512Hash))
  it should "work for a file" in assert(file.sha512OrError === Right(expectedSha512Hash))

}
