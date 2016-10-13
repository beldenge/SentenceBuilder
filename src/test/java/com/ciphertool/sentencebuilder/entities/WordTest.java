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

package com.ciphertool.sentencebuilder.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.ciphertool.sentencebuilder.enumerations.PartOfSpeechType;

public class WordTest {
	@Test
	public void testBusinessKeyConstructor() {
		String wordStringToSet = "arbitraryWord";
		Word word = new Word(wordStringToSet, PartOfSpeechType.NOUN);

		assertSame(wordStringToSet, word.getWord());
		assertSame(PartOfSpeechType.NOUN, word.getPartOfSpeech());
	}

	@Test
	public void testFullArgsConstructor() {
		String wordStringToSet = "arbitraryWord";
		int frequencyWeightToSet = 50;
		Word word = new Word(wordStringToSet, PartOfSpeechType.NOUN, frequencyWeightToSet);

		assertSame(wordStringToSet, word.getWord());
		assertSame(PartOfSpeechType.NOUN, word.getPartOfSpeech());
		assertEquals(frequencyWeightToSet, word.getFrequencyWeight());
	}

	@Test
	public void testDatabaseArgsConstructor() {
		String wordStringToSet = "arbitraryWord";
		int frequencyWeightToSet = 50;
		Word word = new Word(wordStringToSet, frequencyWeightToSet);

		assertEquals(wordStringToSet, word.getWord());
		assertNull(word.getPartOfSpeech());
		assertEquals(frequencyWeightToSet, word.getFrequencyWeight());
	}

	@Test
	public void testSetWord() {
		String wordStringToSet = "arbitraryWord";
		Word word = new Word();
		word.setWord(wordStringToSet);

		assertSame(wordStringToSet, word.getWord());
	}

	@Test
	public void testSetPartOfSpeech() {
		Word word = new Word();
		word.setPartOfSpeech(PartOfSpeechType.NOUN);

		assertSame(PartOfSpeechType.NOUN, word.getPartOfSpeech());
	}
	
	@Test
	public void testSetFrequencyWeight() {
		int frequencyWeightToSet = 50;
		Word word = new Word();
		word.setFrequencyWeight(frequencyWeightToSet);

		assertEquals(frequencyWeightToSet, word.getFrequencyWeight());
	}

	@Test
	public void testEquals() {
		String wordStringToSet = "arbitraryWord";
		int baseFrequencyWeight = 50;

		Word base = new Word();
		base.setWord(wordStringToSet);
		base.setPartOfSpeech(PartOfSpeechType.NOUN);
		base.setFrequencyWeight(baseFrequencyWeight);

		Word wordEqualToBase = new Word();
		wordEqualToBase.setWord(wordStringToSet);
		wordEqualToBase.setPartOfSpeech(PartOfSpeechType.NOUN);
		wordEqualToBase.setFrequencyWeight(baseFrequencyWeight);

		assertEquals(base, wordEqualToBase);

		Word wordWithDifferentId = new Word();
		wordWithDifferentId.setWord("aDifferentWord");
		wordWithDifferentId.setPartOfSpeech(PartOfSpeechType.NOUN);
		wordWithDifferentId.setFrequencyWeight(baseFrequencyWeight);

		assertFalse(wordWithDifferentId.equals(base));

		Word wordWithDifferentFrequencyWeight = new Word();
		wordWithDifferentFrequencyWeight.setWord(wordStringToSet);
		wordWithDifferentFrequencyWeight.setPartOfSpeech(PartOfSpeechType.NOUN);
		wordWithDifferentFrequencyWeight.setFrequencyWeight(baseFrequencyWeight);
		/*
		 * Even if the frequency weight is different, the Words should evaluate as equal
		 */
		assertEquals(base, wordWithDifferentFrequencyWeight);

		Word wordWithNullPropertiesA = new Word();
		Word wordWithNullPropertiesB = new Word();
		assertEquals(wordWithNullPropertiesA, wordWithNullPropertiesB);
	}
}
