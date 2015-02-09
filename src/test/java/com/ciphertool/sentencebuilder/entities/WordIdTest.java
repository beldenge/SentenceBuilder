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

import org.junit.Test;

import com.ciphertool.sentencebuilder.common.PartOfSpeechType;

public class WordIdTest {
	@Test
	public void testConstructor() {
		String wordStringToSet = "arbitraryWord";
		PartOfSpeechType partOfSpeechToSet = PartOfSpeechType.NOUN;
		WordId wordId = new WordId(wordStringToSet, partOfSpeechToSet);

		assertEquals(wordStringToSet, wordId.getWord());
		assertEquals(partOfSpeechToSet, wordId.getPartOfSpeech());
	}

	@Test
	public void testSetWord() {
		String wordStringToSet = "arbitraryWord";
		WordId wordId = new WordId();
		wordId.setWord(wordStringToSet);

		assertEquals(wordStringToSet, wordId.getWord());
	}

	@Test
	public void testSetPartOfSpeech() {
		PartOfSpeechType partOfSpeechToSet = PartOfSpeechType.NOUN;
		WordId wordId = new WordId();
		wordId.setPartOfSpeech(partOfSpeechToSet);

		assertEquals(partOfSpeechToSet, wordId.getPartOfSpeech());
	}

	@Test
	public void testEquals() {
		String baseWordString = "arbitraryWord";
		PartOfSpeechType basePartOfSpeech = PartOfSpeechType.NOUN;

		WordId base = new WordId();
		base.setWord(baseWordString);
		base.setPartOfSpeech(basePartOfSpeech);

		WordId wordIdEqualToBase = new WordId();
		wordIdEqualToBase.setWord(baseWordString);
		wordIdEqualToBase.setPartOfSpeech(basePartOfSpeech);

		assertEquals(base, wordIdEqualToBase);

		WordId wordIdWithDifferentWordString = new WordId();
		wordIdWithDifferentWordString.setWord("aDifferentWord");
		wordIdWithDifferentWordString.setPartOfSpeech(basePartOfSpeech);

		assertFalse(wordIdWithDifferentWordString.equals(base));

		WordId wordIdWithDifferentCaseWordString = new WordId();
		wordIdWithDifferentCaseWordString.setWord("ARBITRARYword");
		wordIdWithDifferentCaseWordString.setPartOfSpeech(basePartOfSpeech);

		assertEquals(base, wordIdWithDifferentCaseWordString);

		WordId wordIdWithDifferentPartOfSpeech = new WordId();
		wordIdWithDifferentPartOfSpeech.setWord(baseWordString);
		wordIdWithDifferentPartOfSpeech.setPartOfSpeech(PartOfSpeechType.ADJECTIVE);

		assertFalse(wordIdWithDifferentPartOfSpeech.equals(base));

		WordId wordIdWithNullPropertiesA = new WordId();
		WordId wordIdWithNullPropertiesB = new WordId();
		assertEquals(wordIdWithNullPropertiesA, wordIdWithNullPropertiesB);
	}
}
