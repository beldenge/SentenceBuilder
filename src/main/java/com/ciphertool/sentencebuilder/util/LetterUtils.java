package com.ciphertool.sentencebuilder.util;

public class LetterUtils {

	/**
	 * Gets a random ASCII value for all lower case letters and returns the
	 * appropriate char.
	 * 
	 * @return
	 */
	public static char getRandomLetter() {
		// Get a random number between 1 and 26 for a letter in the alphabet. 97
		// is the ascii decimal offset.
		int randomIndex = (int) (Math.random() * 26) + 97;

		return getLetterByAsciiValue(randomIndex);
	}

	/**
	 * Casts the ASCII value to a char. The asciiValue must be in decimal
	 * format.
	 * 
	 * @param asciiValue
	 * @return
	 */
	public static char getLetterByAsciiValue(int asciiValue) {
		return (char) asciiValue;
	}
}
