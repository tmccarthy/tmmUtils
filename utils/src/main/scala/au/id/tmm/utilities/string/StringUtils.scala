package au.id.tmm.utilities.string

object StringUtils {
  implicit class ImprovedString(val string: String) {
    def containsIgnoreCase(subString: String): Boolean = string.toLowerCase.contains(subString.toLowerCase)

    def doesNotContainIgnoreCase(subString: String): Boolean = !containsIgnoreCase(subString)

    def containsAnyIgnoreCase(subStrings: String*): Boolean = {
      subStrings.collectFirst {
        case subString if string.containsIgnoreCase(subString) => true
      } getOrElse false
    }

    def containsWordIgnoreCase(word: String): Boolean = {
      string.matches("(?i).*\\b" + word + "\\b.*")
    }

    def containsAnyWordIgnoreCase(words: String*): Boolean = {
      words.collectFirst {
        case word if string.containsWordIgnoreCase(word) => true
      } getOrElse false
    }

    def rtrim: String = {
      var i = string.length - 1

      while (i >= 0 && Character.isWhitespace(string.charAt(i))) {
        i = i - 1
      }

      string.substring(0, i + 1)
    }

    def ltrim: String = {
      var i = 0

      while (i < string.length() && Character.isWhitespace(string.charAt(i))) {
        i = i + 1
      }

      string.substring(i)
    }
  }
}
