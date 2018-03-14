package au.id.tmm.utilities.string

import com.google.common.base.Strings

object StringUtils {
  implicit class ImprovedString(val string: String) {

    /**
      * Returns true if this string contains the given string, ignoring case.
      */
    def containsIgnoreCase(subString: String): Boolean = string.toLowerCase.contains(subString.toLowerCase)

    /**
      * Returns true if this string does not contain the given string, ignoring case.
      */
    def doesNotContainIgnoreCase(subString: String): Boolean = !containsIgnoreCase(subString)

    /**
      * Returns true if this string contains any of the given strings, ignoring case.
      */
    def containsAnyIgnoreCase(subStrings: String*): Boolean = {
      subStrings.collectFirst {
        case subString if string.containsIgnoreCase(subString) => true
      } getOrElse false
    }

    /**
      * Returns true if this string contains the given sequence between word boundaries, ignoring case.
      */
    def containsWordIgnoreCase(word: String): Boolean = {
      string.matches("(?i).*\\b" + word + "\\b.*")
    }

    /**
      * Returns true if this string contains any of the given strings between word boundaries, ignoring case.
      */
    def containsAnyWordIgnoreCase(words: String*): Boolean = {
      words.collectFirst {
        case word if string.containsWordIgnoreCase(word) => true
      } getOrElse false
    }

    /**
      * Trims any whitespace from the end of this string.
      */
    def rtrim: String = {
      var i = string.length - 1

      while (i >= 0 && Character.isWhitespace(string.charAt(i))) {
        i = i - 1
      }

      string.substring(0, i + 1)
    }

    /**
      * Trims any whitespace from the start of this string.
      */
    def ltrim: String = {
      var i = 0

      while (i < string.length() && Character.isWhitespace(string.charAt(i))) {
        i = i + 1
      }

      string.substring(i)
    }

    /**
      * Appends the given string to the start of this string, and then again after each newline.
      *
      * If this string is empty, this method does nothing.
      */
    def indentWith(indentString: String): String =
      if (string.isEmpty) {
        ""
      } else {
        indentString + string.replace("\n", "\n" + indentString)
      }

    /**
      * Appends the given number of spaces to the start of this string, and then again after each newline.
      *
      * If this string is empty, this method does nothing.
      */
    def indentWithSpaces(numSpaces: Int): String = indentWith(Strings.repeat(" ", numSpaces))

    /**
      * Appends the given number of tabs the start of this string, and then again after each newline.
      *
      * If this string is empty, this method does nothing.
      */
    def indentWithTabs(numTabs: Int): String = indentWith(Strings.repeat("\t", numTabs))
  }
}
