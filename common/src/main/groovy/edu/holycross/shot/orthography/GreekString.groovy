package edu.holycross.shot.orthography

import edu.unc.epidoc.transcoder.TransCoder
import java.text.Normalizer
import java.text.Normalizer.Form


import edu.holycross.shot.phonology.Phonology

/**
 * A class for working with text in Greek.  Instances may
 * be initialized with any encoding of Greek that Hugh Cayless'
 * transcoder recognizes;  static methods expect ascii strings.
 * Valid Greek characters are limited to the small set that can be
 * used to construct valid morphologically parseable lexical tokens, plus punctuation characters.
 * If your representation of Greek text includes other characaters, such
 * as numeric characters, or more exotic kinds of punctuatio, you will
 * have to strip those out before creating a GreekString object.
 */
class GreekString {

  // Temporary constructs for debugging:
  Integer SILENT = 0
  Integer WARN =  1
  Integer DEBUG = 2
  Integer VERBOSE = 3

  Integer debugLevel = 0


  /** Immutable set of punctuation characters. */
  static punctuation = [
    ".",
    ";",
    ",",
    ":"
  ]

  static punctuationRE = ~/[\.;,:]+/

  /** Ascii marker for upper case in epidoc transcoder. */
  static String asterisk = "*"

  /** The string in ascii form.*/
  String greekString

  /** Constructor verifies that srcSring, supplied in an identified
   * system for encoding Greek, contains only valid characters
   * for a GreekString's underlying ascii representation.
   * @param srcString Greek string, in ascii.
   * @param System for mapping Greek onto Unicode.  String value may
   * be any of the values for "sourceEncoding" supported by the
   * epidoc transcoder, such as "Unicode".
   * @throws Exception if not all characters in srcString are valid.
   */
  GreekString(String srcString, String greekMapping)  {
    TransCoder xcoder = new TransCoder()
    xcoder.setParser(greekMapping)
    xcoder.setConverter("BetaCode")

    Integer count = 0
    String asciiString = xcoder.getString(srcString.toLowerCase()).toLowerCase()
    asciiString = asciiString.replaceAll("s1","s")
    if (debugLevel > 0) { System.err.println "Analyze " + srcString + " as " + greekMapping + " (len ${asciiString} = " + asciiString.length() + ")" }

    while (count < asciiString.length()) {
      if (!(isValidChar(asciiString.substring(count,count+1)))) {
	System.err.println "Error parsing ${asciiString}: failed on ${asciiString.substring(count,count+1)} (char ${count})"
	System.err.println "GreekString:constructor with ${greekMapping} invalid character at position ${count}:  '" + asciiString.substring(count,count+1) + "'"
	throw new Exception("GreekString:constructor with ${greekMapping} invalid character at position ${count}:  '" + asciiString.substring(count,count+1) + "'")
      }
      count++
    }
    this.greekString = asciiString

  }

  GreekString(String srcString, String greekMapping, boolean ignoreInvalid)  {
    TransCoder xcoder = new TransCoder()
    xcoder.setParser(greekMapping)
    xcoder.setConverter("BetaCode")

    Integer count = 0
    String asciiString = xcoder.getString(srcString).toLowerCase()
    asciiString = asciiString.replaceAll("s1","s")
    this.greekString = asciiString
  }

  /** Constructor verifies that srcSring contains only valid characters
   * for ascii representation.
   * @param srcString Greek string, in ascii.
   * @throws Exception if not all characters in srcString are valid.
   */
  GreekString(String srcString)
  throws Exception {
    Integer count = 0
    String asciiString = srcString.toLowerCase()
    while (count < asciiString.length() ) {
      if (!(isValidChar(asciiString.substring(count,count+1)))) {
	System.err.println "Error parsing ${asciiString}: failed on ${asciiString.substring(count,count+1)} (char ${count})"
	throw new Exception("GreekString: invalid characer ${asciiString.substring(count,count+1)}")
      }
      count++
    }
    this.greekString = asciiString
  }



  /* ************************************************************************************* */
  //
  //  ************  METHODS IDENTIFYING CHARACTER CLASSES   *****************************


  /** Determines if a one-character long string is a valid GreekString
   * character.
   * @param ch String to check.
   * @returns true if character is valid, otherwise false.
   */
  static boolean isValidChar(String ch) {
    Integer debug = 0
    if (debug> 0) {
      System.err.println "GreekString: check " + ch
    }
    if (
      (GreekString.isAlphabetic(ch))
      || (GreekString.isAccentOrBreathing(ch))
      || (GreekString.isQuantity(ch))
      || (GreekString.isPunctuation(ch))
      || (GreekString.isWhiteSpace(ch))
      || (ch == Phonology.diaeresis)
      || (ch == Phonology.elision)
    )  {
      if (debug > 0) { System.err.println "${ch} is OK!" }
      return true
    } else {
      System.err.println("GreekString: invalid ascii character ${ch}")
      return false
    }
  }

  /** Determines if a one-character long string is an alphabetic
   * character.
   * @param ch String to check.
   * @returns true if character is alphabetic, otherwise false.
   */
  static boolean isAlphabetic(String ch) {
    if (
      (GreekString.isConsonant(ch))
      || (GreekString.isVowel(ch))
    ) {
      return true
    } else  {
      return false
    }
  }

  /** Determines if a one-character long string is an accent
   * or breathing character.
   * @param ch String to check.
   * @returns true if character is accent or breathing, otherwise false.
   */
  static boolean isAccentOrBreathing(ch) {
    if (
      (Phonology.isBreathing(ch))
      || (Phonology.isAccent(ch))
    ) {
      return true
    } else  {
      return false
    }
  }


  /** Determines if a one-character long string is an accent.
   * @param ch String to check.
   * @returns true if character is accent, otherwise false.
   */
  static boolean isAccent(ch) {
    return (Phonology.isAccent(ch))
  }


  /** Determines if a one-character long string is a breathing.
   * @param ch String to check.
   * @returns true if character is a breathing, otherwise false.
   */
  static boolean isBreathing(ch) {
    return (Phonology.isBreathing(ch))
  }


  /** Determines if a single-character String
   * is a consonant.
   * @param ch Character to examine.
   * @returns True if ch is a consonant.
   */
  static boolean isConsonant (String ch) {
    return Phonology.isConsonant(ch)
  }


  /** Determines if a single-character String
   * is a vowel.
   * @param ch Character to examine.
   * @returns True if ch is a vowel.
   */
  static boolean isVowel (String ch) {
    return Phonology.isVowel(ch)
  }


  /** Determines if a single-character String
   * is a vowel-quantity character.
   * @param ch Character to examine.
   * @returns True if ch is a vowel quantity character.
   */
  static boolean isQuantity (String ch) {
    return Phonology.isQuantity(ch)
  }


  /** Determines if a String is a diphthong.
   * @param s String to examine.
   * @returns True if s is a diphthong.
   */
  static boolean isDiphthong (String s) {
    return Phonology.isDiphthong(s)
  }

  /** Determines if a one-character long string is diaeresis.
   * @param ch String to check.
   * @returns true if character is diaeresis, otherwise false.
   */
  static boolean isDiaeresis(ch) {
    return (ch == Phonology.diaeresis)
  }

  /** Determines if a single-character String
   * is a punctuation character.
   * @param ch Character to examine.
   * @returns True if ch is a punctuation character.
   */
  static boolean isPunctuation (String ch) {
    return punctuation.contains(ch)
  }



  static boolean isWhiteSpace (String ch) {
    return Character.isWhitespace(ch as char)
    //punctuation.contains(ch)
  }


  //
  //  ************  END METHODS IDENTIFYING CHARACTER CLASSES   **************************
  /* ************************************************************************************* */







  /** Determines if a StringBuffer contains a vowel character.
   * @param buff StringBuffer to examine.
   * @returns True if buff contains a vowel.
   */
  static boolean containsVowel (StringBuffer buff) {
    return containsVowel(buff.toString())
  }

  /** Determines if a String contains a vowel character.
   * @param s String to examine.
   * @returns True if s contains a vowel.
   */
  static boolean containsVowel (String s) {
    boolean vowelSeen = false
    s.each { ch ->
      if (isVowel(ch)) {
	vowelSeen = true
      }
    }
    return vowelSeen
  }



  /** Removes accent characters from a String.
   * @param s String to modify.
   * @returns The string with all accent characters removed.
   */
  static String stripAccents(String s) {
    StringBuffer stripped = new StringBuffer()
    s.each { ch ->
      if (! isAccent(ch)) {
	stripped.append(ch)
      }
    }
    return stripped.toString()
  }


  ArrayList tokenize() {
    return GreekString.tokenize(this)
  }

  /** Splits a GreekString object into an ordered
  * list of white-space-delimited GreekStrings.
  * @param s The GreekString to split up.
  * @returns List of GreekStrings.
  */
  static ArrayList tokenize(GreekString s) {
    ArrayList tokens = []
    System.err.println "Tokenize source string " + s
    System.err.println "Splits to " + s.greekString.split(/\s+/)
    s.greekString.split(/\s+/).each {
      String token = it.toString().replaceAll(punctuationRE, '')
      tokens.add(new GreekString(token))
    }
    return tokens
  }


  /** Overrides default implementation of toString.
   * @returns ASCII-only version of a Greek word.
   */
  String toString() {
    return this.greekString
  }


  /** Overrides default implementation of toString.
   * @param asUnicode True if output should be Unicode
   * in NFC form.
   * @returns ASCII-only version of a Greek word.
   */
  String toString(boolean asUnicode) {
    if (asUnicode) {
      TransCoder xcoder = new TransCoder()
      xcoder.setParser("BetaCode")
      xcoder.setConverter("UnicodeC")
      String u = xcoder.getString(this.greekString)
      u = Normalizer.normalize(u, Form.NFC)
      if (debugLevel > 1) {
	System.err.println "Before check, normalized " + u
      }
      // Override epidoc mapping of high stop
      // and Greek question mark:
      u  = u.replaceAll(/\u00B7/,"\u0387")
      u = u.replaceAll(/\u003B/,"\u037E")
      if (debugLevel > 1) {
	System.err.println "After check " + u
      }

      return u
    } else {
      return this.greekString
    }
  }

}
