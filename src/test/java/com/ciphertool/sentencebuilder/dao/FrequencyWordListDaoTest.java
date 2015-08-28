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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.sentencebuilder.enumerations.PartOfSpeechType;

public class FrequencyWordListDaoTest {
	private static List<Word> wordsToReturn = new ArrayList<Word>();
	private static Word word1;
	private static Word word2;
	private static Word word3;

	@BeforeClass
	public static void setUp() {
		word1 = new Word(new WordId("programming", PartOfSpeechType.NOUN), 5);
		wordsToReturn.add(word1);
		word2 = new Word(new WordId("is", PartOfSpeechType.PLURAL), 1);
		wordsToReturn.add(word2);
		word3 = new Word(new WordId("awesome", PartOfSpeechType.NOUN_PHRASE), 0);
		wordsToReturn.add(word3);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testConstructor() {
		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.findAllUniqueWords()).thenReturn(wordsToReturn);

		FrequencyWordListDao frequencyWordListDao = new FrequencyWordListDao(wordDaoMock, -1);

		Field wordListField = ReflectionUtils.findField(FrequencyWordListDao.class, "wordList");
		ReflectionUtils.makeAccessible(wordListField);
		List<Word> wordListFromObject = (List<Word>) ReflectionUtils.getField(wordListField, frequencyWordListDao);

		assertEquals(7, wordListFromObject.size());
		assertTrue(wordListFromObject.containsAll(wordsToReturn));
		assertTrue(wordsToReturn.containsAll(wordListFromObject));
		assertEquals(5, countOccurrences(word1, wordListFromObject));
		assertEquals(1, countOccurrences(word2, wordListFromObject));
		assertEquals(1, countOccurrences(word3, wordListFromObject));
		verify(wordDaoMock, times(1)).findAllUniqueWords();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithNullWordMap() {
		new FrequencyWordListDao(null, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithZeroTop() {
		new FrequencyWordListDao(mock(WordDao.class), 0);
	}

	@Test
	public void testFindRandomWord() {
		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.findAllUniqueWords()).thenReturn(wordsToReturn);

		FrequencyWordListDao frequencyWordListDao = new FrequencyWordListDao(wordDaoMock, -1);

		Word randomWord = frequencyWordListDao.findRandomWord();

		assertNotNull(randomWord);
		assertTrue(wordsToReturn.contains(randomWord));
		verify(wordDaoMock, times(1)).findAllUniqueWords();
	}

	private static int countOccurrences(Word wordToCount, List<Word> words) {
		int occurrences = 0;

		for (Word word : words) {
			if (word.equals(wordToCount)) {
				occurrences++;
			}
		}

		return occurrences;
	}
}
