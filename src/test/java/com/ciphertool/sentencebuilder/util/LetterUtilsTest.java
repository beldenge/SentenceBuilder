package com.ciphertool.sentencebuilder.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;

public class LetterUtilsTest {
	private static Logger log = Logger.getLogger(LetterUtilsTest.class);

	@Test
	public void testGetCharacterByAsciiValue() {
		assertEquals(LetterUtils.getLetterByAsciiValue(97), 'a');
		assertEquals(LetterUtils.getLetterByAsciiValue(122), 'z');
	}

	@Test
	public void testGetRandomCharacter() {
		char c;
		for (int i = 0; i < 100; i++) {
			c = LetterUtils.getRandomLetter();
			assertTrue(String.valueOf(c).matches("[a-z]"));
			log.info(c);
		}
	}
}
