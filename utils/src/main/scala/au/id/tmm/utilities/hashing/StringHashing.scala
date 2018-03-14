package au.id.tmm.utilities.hashing

import java.io.ByteArrayInputStream
import java.nio.charset.Charset

object StringHashing {
  implicit class StringHashingImplicits(string: String) {

    /**
      * Computes the SHA-256 checksum of this string, assuming a UTF-8 encoding.
      */
    def sha256checksum: Digest = sha256checksum(Charset.forName("UTF-8"))

    /**
      * Computes the SHA-256 checksum of this string, assuming the given encoding.
      */
    def sha256checksum(charset: Charset): Digest = checksum("SHA-256", charset)

    /**
      * Computes the checksum of this string using the given algorithm, assuming the given encoding.
      * @param algorithm the checksum algorithm, as would be provided to [[java.security.MessageDigest#getInstance]]
      * @param charset   the encoding for the string
      */
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
