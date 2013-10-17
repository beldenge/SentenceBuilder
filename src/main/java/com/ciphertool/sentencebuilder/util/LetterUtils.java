/**
 * Copyright 2013 George Belden
 * 
 * This file is part of SentenceBuilder.
 * 
 * SentenceBuilder is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * SentenceBuilder is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SentenceBuilder. If not, see <http://www.gnu.org/licenses/>.
 */

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
