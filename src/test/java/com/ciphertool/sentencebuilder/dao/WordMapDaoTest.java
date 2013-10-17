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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.sentencebuilder.common.PartOfSpeech;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;

public class WordMapDaoTest {
	private static WordDao wordDaoMock;
	private static WordMapDao basicWordMapDao;
	private static WordMapDao indexedWordMapDao;

	private static Word word1;
	private static Word word2;
	private static Word word3;
	private static Word word4;
	private static Word word5;
	private static Word word6;
	private static Word word7;
	private static Word word8;
	private static Word word9;
	private static Word word10;

	@BeforeClass
	public static void setUp() {
		wordDaoMock = mock(WordDao.class);

		List<Word> wordsToReturn = new ArrayList<Word>();
		word1 = new Word(new WordId("I", 'N'));
		wordsToReturn.add(word1);
		word2 = new Word(new WordId("am", 'p'));
		wordsToReturn.add(word2);
		word3 = new Word(new WordId("the", 'h'));
		wordsToReturn.add(word3);
		word4 = new Word(new WordId("best", 'V'));
		wordsToReturn.add(word4);
		word5 = new Word(new WordId("homie", 't'));
		wordsToReturn.add(word5);
		word6 = new Word(new WordId("hollah", 'i'));
		wordsToReturn.add(word6);
		word7 = new Word(new WordId("seventy", 'A'));
		wordsToReturn.add(word7);
		word8 = new Word(new WordId("trillion", 'v'));
		wordsToReturn.add(word8);
		word9 = new Word(new WordId("benjamins", 'C'));
		wordsToReturn.add(word9);
		word10 = new Word(new WordId("investment", 'P'));
		wordsToReturn.add(word10);

		when(wordDaoMock.findAll()).thenReturn(wordsToReturn);

		basicWordMapDao = new BasicWordMapDao(wordDaoMock);
		indexedWordMapDao = new IndexedWordMapDao(wordDaoMock);
	}

	@Test
	public void testFindByPartOfSpeechForBasicWordMapDao() {
		testFindByPartOfSpeech(basicWordMapDao);
	}

	@Test
	public void testFindByLengthForBasicWordMapDao() {
		testFindByLength(basicWordMapDao);
	}

	@Test
	public void testFindByPartOfSpeechForIndexedWordMapDao() {
		testFindByPartOfSpeech(indexedWordMapDao);
	}

	@Test
	public void testFindByLengthForIndexedWordMapDao() {
		testFindByLength(indexedWordMapDao);
	}

	private void testFindByPartOfSpeech(WordMapDao wordMapDao) {
		Word wordToTest = basicWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeech.NOUN);
		assertEquals(word1, wordToTest);

		wordToTest = basicWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeech.NOUN_PHRASE);
		assertEquals(word3, wordToTest);

		wordToTest = basicWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeech.VERB_TRANSITIVE);
		assertEquals(word5, wordToTest);

		wordToTest = basicWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeech.ADJECTIVE);
		assertEquals(word7, wordToTest);

		wordToTest = basicWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeech.CONJUNCTION);
		assertEquals(word9, wordToTest);
	}

	private void testFindByLength(WordMapDao wordMapDao) {
		Word wordToTest = basicWordMapDao.findRandomWordByLength(2);
		assertEquals(word2, wordToTest);

		wordToTest = basicWordMapDao.findRandomWordByLength(4);
		assertEquals(word4, wordToTest);

		wordToTest = basicWordMapDao.findRandomWordByLength(6);
		assertEquals(word6, wordToTest);

		wordToTest = basicWordMapDao.findRandomWordByLength(8);
		assertEquals(word8, wordToTest);

		wordToTest = basicWordMapDao.findRandomWordByLength(10);
		assertEquals(word10, wordToTest);
	}
}
