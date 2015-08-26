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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.sentencebuilder.common.PartOfSpeechType;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;

public class BasicWordMapDaoTest {
	private static WordDao wordDaoMock;
	private static BasicWordMapDao basicWordMapDao;
	private static List<Word> wordsToReturn = new ArrayList<Word>();
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

		word1 = new Word(new WordId("I", PartOfSpeechType.NOUN));
		wordsToReturn.add(word1);
		word2 = new Word(new WordId("am", PartOfSpeechType.PLURAL));
		wordsToReturn.add(word2);
		word3 = new Word(new WordId("the", PartOfSpeechType.NOUN_PHRASE));
		wordsToReturn.add(word3);
		word4 = new Word(new WordId("best", PartOfSpeechType.VERB_PARTICIPLE));
		wordsToReturn.add(word4);
		word5 = new Word(new WordId("homie", PartOfSpeechType.VERB_TRANSITIVE));
		wordsToReturn.add(word5);
		word6 = new Word(new WordId("hollah", PartOfSpeechType.VERB_INTRANSITIVE));
		wordsToReturn.add(word6);
		word7 = new Word(new WordId("seventy", PartOfSpeechType.ADJECTIVE));
		wordsToReturn.add(word7);
		word8 = new Word(new WordId("trillion", PartOfSpeechType.ADVERB));
		wordsToReturn.add(word8);
		word9 = new Word(new WordId("benjamins", PartOfSpeechType.CONJUNCTION));
		wordsToReturn.add(word9);
		word10 = new Word(new WordId("investment", PartOfSpeechType.PREPOSITION));
		wordsToReturn.add(word10);

		when(wordDaoMock.findAll()).thenReturn(wordsToReturn);

		basicWordMapDao = new BasicWordMapDao(wordDaoMock, -1);

		verify(wordDaoMock, times(1)).findAll();
	}

	@Test
	public void testConstructor() {
		reset(wordDaoMock);
		when(wordDaoMock.findAll()).thenReturn(wordsToReturn);

		BasicWordMapDao basicWordMapDao = new BasicWordMapDao(wordDaoMock, -1);

		assertEquals(10, basicWordMapDao.getPartOfSpeechWordMap().size());
		assertEquals(10, basicWordMapDao.getLengthWordMap().size());

		boolean wordFoundByPartOfSpeech;
		boolean wordFoundByLength;
		for (Word word : wordsToReturn) {
			wordFoundByPartOfSpeech = false;
			wordFoundByLength = false;

			for (ArrayList<Word> wordsByPartOfSpeech : basicWordMapDao.getPartOfSpeechWordMap().values()) {
				if (wordsByPartOfSpeech.contains(word)) {
					wordFoundByPartOfSpeech = true;
				}
			}
			for (ArrayList<Word> wordsByLength : basicWordMapDao.getLengthWordMap().values()) {
				if (wordsByLength.contains(word)) {
					wordFoundByLength = true;
				}
			}
			assertTrue(wordFoundByPartOfSpeech);
			assertTrue(wordFoundByLength);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithNullWordMap() {
		new BasicWordMapDao(null, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithZeroTop() {
		new BasicWordMapDao(wordDaoMock, 0);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testPartOfSpeechWordMapUnmodifiable() {
		Map<PartOfSpeechType, ArrayList<Word>> partOfSpeechWordMap = basicWordMapDao.getPartOfSpeechWordMap();
		partOfSpeechWordMap.remove(0); // should throw exception
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testLengthWordMapUnmodifiable() {
		Map<PartOfSpeechType, ArrayList<Word>> lengthWordMap = basicWordMapDao.getPartOfSpeechWordMap();
		lengthWordMap.remove(0); // should throw exception
	}

	@Test
	public void testFindByPartOfSpeech() {
		Word wordToTest = basicWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeechType.NOUN);
		assertEquals(word1, wordToTest);
		wordToTest = basicWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeechType.PLURAL);
		assertEquals(word2, wordToTest);
		wordToTest = basicWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeechType.NOUN_PHRASE);
		assertEquals(word3, wordToTest);
		wordToTest = basicWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeechType.VERB_PARTICIPLE);
		assertEquals(word4, wordToTest);
		wordToTest = basicWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeechType.VERB_TRANSITIVE);
		assertEquals(word5, wordToTest);
		wordToTest = basicWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeechType.VERB_INTRANSITIVE);
		assertEquals(word6, wordToTest);
		wordToTest = basicWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeechType.ADJECTIVE);
		assertEquals(word7, wordToTest);
		wordToTest = basicWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeechType.ADVERB);
		assertEquals(word8, wordToTest);
		wordToTest = basicWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeechType.CONJUNCTION);
		assertEquals(word9, wordToTest);
		wordToTest = basicWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeechType.PREPOSITION);
		assertEquals(word10, wordToTest);
	}

	@Test
	public void testFindByLength() {
		Word wordToTest = basicWordMapDao.findRandomWordByLength(1);
		assertEquals(word1, wordToTest);
		wordToTest = basicWordMapDao.findRandomWordByLength(2);
		assertEquals(word2, wordToTest);
		wordToTest = basicWordMapDao.findRandomWordByLength(3);
		assertEquals(word3, wordToTest);
		wordToTest = basicWordMapDao.findRandomWordByLength(4);
		assertEquals(word4, wordToTest);
		wordToTest = basicWordMapDao.findRandomWordByLength(5);
		assertEquals(word5, wordToTest);
		wordToTest = basicWordMapDao.findRandomWordByLength(6);
		assertEquals(word6, wordToTest);
		wordToTest = basicWordMapDao.findRandomWordByLength(7);
		assertEquals(word7, wordToTest);
		wordToTest = basicWordMapDao.findRandomWordByLength(8);
		assertEquals(word8, wordToTest);
		wordToTest = basicWordMapDao.findRandomWordByLength(9);
		assertEquals(word9, wordToTest);
		wordToTest = basicWordMapDao.findRandomWordByLength(10);
		assertEquals(word10, wordToTest);
	}

	@Test
	public void testMapByPartOfSpeech() {
		Map<PartOfSpeechType, ArrayList<Word>> partOfSpeechWordMap = BasicWordMapDao.mapByPartOfSpeech(wordsToReturn);

		assertEquals(10, partOfSpeechWordMap.size());

		for (Word word : wordsToReturn) {
			assertTrue(partOfSpeechWordMap.get(word.getId().getPartOfSpeech()).contains(word));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMapByPartOfSpeechNullParameter() {
		BasicWordMapDao.mapByPartOfSpeech(null);
	}

	@Test
	public void testMapByWordLength() {
		Map<Integer, ArrayList<Word>> lengthWordMap = BasicWordMapDao.mapByWordLength(wordsToReturn);

		assertEquals(10, lengthWordMap.size());

		for (Word word : wordsToReturn) {
			assertTrue(lengthWordMap.get(word.getId().getWord().length()).contains(word));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMapByWordLengthNullParameter() {
		BasicWordMapDao.mapByWordLength(null);
	}
}
