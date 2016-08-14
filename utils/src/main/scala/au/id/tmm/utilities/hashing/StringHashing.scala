package au.id.tmm.utilities.hashing

import java.io.ByteArrayInputStream
import java.nio.charset.Charset

object StringHashing {
  implicit class StringHashingImplicits(string: String) {
    def sha256checksum: Digest = sha256checksum(Charset.forName("UTF-8"))

    def sha256checksum(charset: Charset): Digest = checksum("SHA-256", charset)

    def checksum(algorithm: String, charset: Charset = Charset.forName("UTF-8")): Digest = {
      val bytes = string.getBytes(charset)

      val inputStream = new ByteArrayInputStream(bytes)

      Digester.apply(algorithm, inputStream)
        .map(inputStream => while (inputStream.read() >= 0) {})
        .get()
        .get
        ._1
    }
  }
}
