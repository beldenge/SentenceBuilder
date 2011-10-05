package com.ciphertool.sentencebuilder.common;

import org.junit.Before;
import org.junit.Test;

public class PartOfSpeechTest {

	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testEnumValueOf() {
		System.out.println(PartOfSpeech.valueOf("Pronoun".toUpperCase()).getTag());
	}
	
}
