/**
 * Copyright 2015 George Belden
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LetterUtilsTest {

	@Test
	public void testGetCharacterByAsciiValue() {
		assertEquals(LetterUtils.getLetterByAsciiValue(97), 'a');
		assertEquals(LetterUtils.getLetterByAsciiValue(122), 'z');
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCharacterByAsciiValueTooLow() {
		LetterUtils.getLetterByAsciiValue(96);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCharacterByAsciiValueTooHigh() {
		LetterUtils.getLetterByAsciiValue(123);
	}

	@Test
	public void testGetRandomCharacter() {
		char c;
		for (int i = 0; i < 100; i++) {
			c = LetterUtils.getRandomLetter();
			assertTrue(String.valueOf(c).matches("[a-z]"));
		}
	}
}
