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

public class UniqueWordListDaoTest {
	private static List<Word> wordsToReturn = new ArrayList<Word>();
	private static Word word1;
	private static Word word2;
	private static Word word3;

	@BeforeClass
	public static void setUp() {
		word1 = new Word(new WordId("programming", 'N'));
		wordsToReturn.add(word1);
		word2 = new Word(new WordId("is", 'p'));
		wordsToReturn.add(word2);
		word3 = new Word(new WordId("awesome", 'h'));
		wordsToReturn.add(word3);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testConstructor() {
		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.findAllUniqueWords()).thenReturn(wordsToReturn);

		UniqueWordListDao uniqueWordListDao = new UniqueWordListDao(wordDaoMock);

		Field wordListField = ReflectionUtils.findField(UniqueWordListDao.class, "wordList");
		ReflectionUtils.makeAccessible(wordListField);
		List<Word> wordListFromObject = (List<Word>) ReflectionUtils.getField(wordListField,
				uniqueWordListDao);

		assertEquals(3, wordListFromObject.size());
		assertTrue(wordListFromObject.containsAll(wordsToReturn));
		assertTrue(wordsToReturn.containsAll(wordListFromObject));
		verify(wordDaoMock, times(1)).findAllUniqueWords();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithNullWordMap() {
		new UniqueWordListDao(null);
	}

	@Test
	public void testFindRandomWord() {
		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.findAllUniqueWords()).thenReturn(wordsToReturn);

		UniqueWordListDao uniqueWordListDao = new UniqueWordListDao(wordDaoMock);

		Word randomWord = uniqueWordListDao.findRandomWord();

		assertNotNull(randomWord);
		assertTrue(wordsToReturn.contains(randomWord));
		verify(wordDaoMock, times(1)).findAllUniqueWords();
	}
}
