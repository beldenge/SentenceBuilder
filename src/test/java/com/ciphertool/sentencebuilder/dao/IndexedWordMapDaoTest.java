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

import com.ciphertool.sentencebuilder.common.PartOfSpeech;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;

public class IndexedWordMapDaoTest {
	private static WordDao wordDaoMock;
	private static IndexedWordMapDao indexedWordMapDao;
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

		word1 = new Word(new WordId("I", 'N'), 15);
		wordsToReturn.add(word1);
		word2 = new Word(new WordId("am", 'p'), 14);
		wordsToReturn.add(word2);
		word3 = new Word(new WordId("the", 'h'), 13);
		wordsToReturn.add(word3);
		word4 = new Word(new WordId("best", 'V'), 12);
		wordsToReturn.add(word4);
		word5 = new Word(new WordId("homie", 't'), 11);
		wordsToReturn.add(word5);
		word6 = new Word(new WordId("hollah", 'i'), 10);
		wordsToReturn.add(word6);
		word7 = new Word(new WordId("seventy", 'A'), 9);
		wordsToReturn.add(word7);
		word8 = new Word(new WordId("trillion", 'v'), 8);
		wordsToReturn.add(word8);
		word9 = new Word(new WordId("benjamins", 'C'), 7);
		wordsToReturn.add(word9);
		word10 = new Word(new WordId("investment", 'P'), 6);
		wordsToReturn.add(word10);

		when(wordDaoMock.findAll()).thenReturn(wordsToReturn);

		indexedWordMapDao = new IndexedWordMapDao(wordDaoMock);

		verify(wordDaoMock, times(1)).findAll();
	}

	@Test
	public void testConstructor() {
		reset(wordDaoMock);
		when(wordDaoMock.findAll()).thenReturn(wordsToReturn);

		IndexedWordMapDao IndexedWordMapDao = new IndexedWordMapDao(wordDaoMock);

		assertEquals(10, IndexedWordMapDao.getPartOfSpeechWordMap().size());
		assertEquals(10, IndexedWordMapDao.getLengthWordMap().size());

		boolean wordFoundByPartOfSpeech;
		boolean wordFoundByLength;
		for (Word word : wordsToReturn) {
			wordFoundByPartOfSpeech = false;
			wordFoundByLength = false;

			for (ArrayList<Word> wordsByPartOfSpeech : IndexedWordMapDao.getPartOfSpeechWordMap()
					.values()) {
				if (wordsByPartOfSpeech.contains(word)) {
					wordFoundByPartOfSpeech = true;
				}
			}
			for (ArrayList<Word> wordsByLength : IndexedWordMapDao.getLengthWordMap().values()) {
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
		new IndexedWordMapDao(null);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testPartOfSpeechWordMapUnmodifiable() {
		Map<PartOfSpeech, ArrayList<Word>> partOfSpeechWordMap = indexedWordMapDao
				.getPartOfSpeechWordMap();
		partOfSpeechWordMap.remove(0); // should throw exception
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testLengthWordMapUnmodifiable() {
		Map<PartOfSpeech, ArrayList<Word>> lengthWordMap = indexedWordMapDao
				.getPartOfSpeechWordMap();
		lengthWordMap.remove(0); // should throw exception
	}

	@Test
	public void testFindByPartOfSpeech() {
		Word wordToTest = indexedWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeech.NOUN);
		assertEquals(word1, wordToTest);
		wordToTest = indexedWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeech.PLURAL);
		assertEquals(word2, wordToTest);
		wordToTest = indexedWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeech.NOUN_PHRASE);
		assertEquals(word3, wordToTest);
		wordToTest = indexedWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeech.VERB_PARTICIPLE);
		assertEquals(word4, wordToTest);
		wordToTest = indexedWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeech.VERB_TRANSITIVE);
		assertEquals(word5, wordToTest);
		wordToTest = indexedWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeech.VERB_INTRANSITIVE);
		assertEquals(word6, wordToTest);
		wordToTest = indexedWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeech.ADJECTIVE);
		assertEquals(word7, wordToTest);
		wordToTest = indexedWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeech.ADVERB);
		assertEquals(word8, wordToTest);
		wordToTest = indexedWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeech.CONJUNCTION);
		assertEquals(word9, wordToTest);
		wordToTest = indexedWordMapDao.findRandomWordByPartOfSpeech(PartOfSpeech.PREPOSITION);
		assertEquals(word10, wordToTest);
	}

	@Test
	public void testFindByLength() {
		Word wordToTest = indexedWordMapDao.findRandomWordByLength(1);
		assertEquals(word1, wordToTest);
		wordToTest = indexedWordMapDao.findRandomWordByLength(2);
		assertEquals(word2, wordToTest);
		wordToTest = indexedWordMapDao.findRandomWordByLength(3);
		assertEquals(word3, wordToTest);
		wordToTest = indexedWordMapDao.findRandomWordByLength(4);
		assertEquals(word4, wordToTest);
		wordToTest = indexedWordMapDao.findRandomWordByLength(5);
		assertEquals(word5, wordToTest);
		wordToTest = indexedWordMapDao.findRandomWordByLength(6);
		assertEquals(word6, wordToTest);
		wordToTest = indexedWordMapDao.findRandomWordByLength(7);
		assertEquals(word7, wordToTest);
		wordToTest = indexedWordMapDao.findRandomWordByLength(8);
		assertEquals(word8, wordToTest);
		wordToTest = indexedWordMapDao.findRandomWordByLength(9);
		assertEquals(word9, wordToTest);
		wordToTest = indexedWordMapDao.findRandomWordByLength(10);
		assertEquals(word10, wordToTest);
	}

	@Test
	public void testMapByPartOfSpeech() {
		Map<PartOfSpeech, ArrayList<Word>> partOfSpeechWordMap = IndexedWordMapDao
				.mapByPartOfSpeech(wordsToReturn);

		assertEquals(10, partOfSpeechWordMap.size());

		for (Word word : wordsToReturn) {
			assertTrue(partOfSpeechWordMap.get(
					PartOfSpeech.getValueFromSymbol(word.getId().getPartOfSpeech())).contains(word));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMapByPartOfSpeechNullParameter() {
		IndexedWordMapDao.mapByPartOfSpeech(null);
	}

	@Test
	public void testMapByWordLength() {
		Map<Integer, ArrayList<Word>> lengthWordMap = IndexedWordMapDao
				.mapByWordLength(wordsToReturn);

		assertEquals(10, lengthWordMap.size());

		for (Word word : wordsToReturn) {
			assertTrue(lengthWordMap.get(word.getId().getWord().length()).contains(word));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMapByWordLengthNullParameter() {
		IndexedWordMapDao.mapByWordLength(null);
	}

	@Test
	public void testBuildIndexedFrequencyMapByPartOfSpeech() {
		Map<PartOfSpeech, ArrayList<Word>> partOfSpeechWordMap = IndexedWordMapDao
				.mapByPartOfSpeech(wordsToReturn);

		Map<PartOfSpeech, int[]> partOfSpeechIndexedMap = IndexedWordMapDao
				.buildIndexedFrequencyMapByPartOfSpeech(partOfSpeechWordMap);

		assertEquals(10, partOfSpeechIndexedMap.size());

		int indicesFound;
		for (Word word : wordsToReturn) {
			indicesFound = 0;

			int index = partOfSpeechWordMap.get(
					PartOfSpeech.getValueFromSymbol(word.getId().getPartOfSpeech())).indexOf(word);

			for (int wordsByPartOfSpeech : partOfSpeechIndexedMap.get(PartOfSpeech
					.getValueFromSymbol(word.getId().getPartOfSpeech()))) {
				if (index == wordsByPartOfSpeech) {
					indicesFound++;
				}
			}

			assertEquals(word.getFrequencyWeight(), indicesFound);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildIndexedFrequencyMapByPartOfSpeechNullParameter() {
		IndexedWordMapDao.buildIndexedFrequencyMapByPartOfSpeech(null);
	}

	@Test
	public void testBuildIndexedFrequencyMapByLength() {
		Map<Integer, ArrayList<Word>> lengthWordMap = IndexedWordMapDao
				.mapByWordLength(wordsToReturn);

		Map<Integer, int[]> lengthIndexedMap = IndexedWordMapDao
				.buildIndexedFrequencyMapByLength(lengthWordMap);

		assertEquals(10, lengthIndexedMap.size());

		int indicesFound;
		for (Word word : wordsToReturn) {
			indicesFound = 0;

			int index = lengthWordMap.get(word.getId().getWord().length()).indexOf(word);

			for (int wordsByPartOfSpeech : lengthIndexedMap.get(word.getId().getWord().length())) {
				if (index == wordsByPartOfSpeech) {
					indicesFound++;
				}
			}

			assertEquals(word.getFrequencyWeight(), indicesFound);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildIndexedFrequencyMapByLengthNullParameter() {
		IndexedWordMapDao.buildIndexedFrequencyMapByLength(null);
	}
}
