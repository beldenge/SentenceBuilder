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

package com.ciphertool.sentencebuilder.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexTest {
	private static Logger log = LoggerFactory.getLogger(RegexTest.class);

	/**
	 * This is a test for a potential future WordMapDao or WordListDao implementation which would select words based on
	 * a specified regular expression.
	 */
	@Test
	public void testRegex() {
		String goodWord = "teeth";
		String badWord1 = "teeeo";
		String badWord2 = "tteto";
		String badWord3 = "tetto";
		String badWord4 = "teete";
		/*
		 * We want the second letter to be different from the first letter, the third letter to match the second letter,
		 * the fourth letter to match the first letter, and the last letter to not match any other letters.
		 */
		String regex = "([a-z])(?!\\1)([a-z])\\2\\1(?!(\\1|\\2))[a-z]";
		log.info("Regex: " + regex);

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(goodWord);
		assertTrue(m.matches());

		assertFalse(m.reset(badWord1).matches());
		assertFalse(m.reset(badWord2).matches());
		assertFalse(m.reset(badWord3).matches());
		assertFalse(m.reset(badWord4).matches());
	}
}
