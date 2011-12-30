package com.ciphertool.sentencebuilder.common;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class PartOfSpeechTest {
	
	private static Logger log = Logger.getLogger(PartOfSpeechTest.class);

	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testEnumValueOf() {
		log.info(PartOfSpeech.valueOf("Pronoun".toUpperCase()).getTag());
	}
}
