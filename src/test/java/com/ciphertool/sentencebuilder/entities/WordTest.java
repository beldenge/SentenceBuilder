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

import com.ciphertool.sentencebuilder.common.PartOfSpeechType;

public class WordTest {
	@Test
	public void testBusinessKeyConstructor() {
		WordId wordIdToSet = new WordId("arbitraryWord", PartOfSpeechType.NOUN);
		Word word = new Word(wordIdToSet);

		assertSame(wordIdToSet, word.getId());
	}

	@Test
	public void testFullArgsConstructor() {
		WordId wordIdToSet = new WordId("arbitraryWord", PartOfSpeechType.NOUN);
		int frequencyWeightToSet = 50;
		Word word = new Word(wordIdToSet, frequencyWeightToSet);

		assertSame(wordIdToSet, word.getId());
		assertEquals(frequencyWeightToSet, word.getFrequencyWeight());
	}

	@Test
	public void testDatabaseArgsConstructor() {
		String wordStringToSet = "arbitraryWord";
		int frequencyWeightToSet = 50;
		Word word = new Word(wordStringToSet, frequencyWeightToSet);

		assertEquals(wordStringToSet, word.getId().getWord());
		assertNull(word.getId().getPartOfSpeech());
		assertEquals(frequencyWeightToSet, word.getFrequencyWeight());
	}

	@Test
	public void testSetId() {
		WordId wordIdToSet = new WordId("arbitraryWord", PartOfSpeechType.NOUN);
		Word word = new Word();
		word.setId(wordIdToSet);

		assertSame(wordIdToSet, word.getId());
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
		WordId baseWordId = new WordId("arbitraryWord", PartOfSpeechType.NOUN);
		int baseFrequencyWeight = 50;

		Word base = new Word();
		base.setId(baseWordId);
		base.setFrequencyWeight(baseFrequencyWeight);

		Word wordEqualToBase = new Word();
		wordEqualToBase.setId(baseWordId);
		wordEqualToBase.setFrequencyWeight(baseFrequencyWeight);

		assertEquals(base, wordEqualToBase);

		Word wordWithDifferentId = new Word();
		wordWithDifferentId.setId(new WordId("aDifferentWord", PartOfSpeechType.NOUN));
		wordWithDifferentId.setFrequencyWeight(baseFrequencyWeight);

		assertFalse(wordWithDifferentId.equals(base));

		Word wordWithDifferentFrequencyWeight = new Word();
		wordWithDifferentFrequencyWeight.setId(baseWordId);
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
