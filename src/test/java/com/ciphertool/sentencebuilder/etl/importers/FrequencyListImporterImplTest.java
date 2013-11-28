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

package com.ciphertool.sentencebuilder.etl.importers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.ciphertool.sentencebuilder.dao.WordDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.sentencebuilder.etl.parsers.FileParser;
import com.ciphertool.sentencebuilder.etl.parsers.FrequencyFileParser;

public class FrequencyListImporterImplTest {

	@Test
	public void testSetWordDao() {
		WordDao wordDaoToSet = mock(WordDao.class);
		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();
		frequencyListImporterImpl.setWordDao(wordDaoToSet);

		Field wordDaoField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "wordDao");
		ReflectionUtils.makeAccessible(wordDaoField);
		WordDao wordDaoFromObject = (WordDao) ReflectionUtils.getField(wordDaoField,
				frequencyListImporterImpl);

		assertSame(wordDaoToSet, wordDaoFromObject);
	}

	@Test
	public void testSetBatchSize() {
		int batchSizeToSet = 99;
		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();
		frequencyListImporterImpl.setBatchSize(batchSizeToSet);

		Field batchSizeField = ReflectionUtils.findField(FrequencyListImporterImpl.class,
				"batchSize");
		ReflectionUtils.makeAccessible(batchSizeField);
		int batchSizeFromObject = (int) ReflectionUtils.getField(batchSizeField,
				frequencyListImporterImpl);

		assertEquals(batchSizeToSet, batchSizeFromObject);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSetFileParser() {
		FileParser<Word> fileParserToSet = mock(FileParser.class);
		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();
		frequencyListImporterImpl.setFileParser(fileParserToSet);

		Field fileParserField = ReflectionUtils.findField(FrequencyListImporterImpl.class,
				"frequencyFileParser");
		ReflectionUtils.makeAccessible(fileParserField);
		FileParser<Word> fileParserFromObject = (FileParser<Word>) ReflectionUtils.getField(
				fileParserField, frequencyListImporterImpl);

		assertSame(fileParserToSet, fileParserFromObject);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testImportFrequencyList() {
		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();

		Field rowUpdateCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class,
				"rowUpdateCount");
		ReflectionUtils.makeAccessible(rowUpdateCountField);
		Integer rowUpdateCountFromObject = (Integer) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowUpdateCountFromObject.intValue());

		Field rowInsertCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class,
				"rowInsertCount");
		ReflectionUtils.makeAccessible(rowInsertCountField);
		Integer rowInsertCountFromObject = (Integer) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowInsertCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		int batchSizeToSet = 2;

		frequencyListImporterImpl.setWordDao(wordDaoMock);
		frequencyListImporterImpl.setBatchSize(batchSizeToSet);

		Word word1 = new Word(new WordId("george", 'N'), 100);
		Word word2 = new Word(new WordId("belden", 'N'), 200);
		Word word3 = new Word(new WordId("is", 'V'), 300);
		Word word4 = new Word(new WordId("awesome", 'A'), 400);
		List<Word> wordsToReturn = new ArrayList<Word>();
		wordsToReturn.add(word1);
		wordsToReturn.add(word2);
		wordsToReturn.add(word3);
		wordsToReturn.add(word4);
		FrequencyFileParser fileParserMock = mock(FrequencyFileParser.class);
		when(fileParserMock.parseFile()).thenReturn(wordsToReturn);

		frequencyListImporterImpl.setFileParser(fileParserMock);

		Word wordFromDatabase1 = new Word(new WordId("george", 'N'));
		Word wordFromDatabase2 = new Word(new WordId("belden", 'N'));

		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		when(wordDaoMock.updateBatch(anyListOf(Word.class))).thenReturn(true);
		when(wordDaoMock.findByWordString(anyString())).thenReturn(
				Arrays.asList(wordFromDatabase1), Arrays.asList(wordFromDatabase2), null, null);

		frequencyListImporterImpl.importFrequencyList();

		assertEquals(100, wordFromDatabase1.getFrequencyWeight());
		assertEquals(200, wordFromDatabase2.getFrequencyWeight());

		rowUpdateCountFromObject = (Integer) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);
		rowInsertCountFromObject = (Integer) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(2, rowUpdateCountFromObject.intValue());
		assertEquals(2, rowInsertCountFromObject.intValue());
		verify(wordDaoMock, times(1)).insertBatch(anyListOf(Word.class));
		verify(wordDaoMock, times(1)).updateBatch(anyListOf(Word.class));
		verify(wordDaoMock, times(4)).findByWordString(anyString());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testImportFrequencyList_LeftoversFromBatch() {
		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();

		Field rowUpdateCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class,
				"rowUpdateCount");
		ReflectionUtils.makeAccessible(rowUpdateCountField);
		Integer rowUpdateCountFromObject = (Integer) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowUpdateCountFromObject.intValue());

		Field rowInsertCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class,
				"rowInsertCount");
		ReflectionUtils.makeAccessible(rowInsertCountField);
		Integer rowInsertCountFromObject = (Integer) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowInsertCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		int batchSizeToSet = 2;

		frequencyListImporterImpl.setWordDao(wordDaoMock);
		frequencyListImporterImpl.setBatchSize(batchSizeToSet);

		Word word1 = new Word(new WordId("george", 'N'), 100);
		Word word2 = new Word(new WordId("belden", 'N'), 200);
		Word word3 = new Word(new WordId("is", 'V'), 300);
		Word word4 = new Word(new WordId("super", 'A'), 400);
		Word word5 = new Word(new WordId("seriously", 'A'), 500);
		Word word6 = new Word(new WordId("awesome", 'A'), 600);
		List<Word> wordsToReturn = new ArrayList<Word>();
		wordsToReturn.add(word1);
		wordsToReturn.add(word2);
		wordsToReturn.add(word3);
		wordsToReturn.add(word4);
		wordsToReturn.add(word5);
		wordsToReturn.add(word6);
		FrequencyFileParser fileParserMock = mock(FrequencyFileParser.class);
		when(fileParserMock.parseFile()).thenReturn(wordsToReturn);

		frequencyListImporterImpl.setFileParser(fileParserMock);

		Word wordFromDatabase1 = new Word(new WordId("george", 'N'));
		Word wordFromDatabase2 = new Word(new WordId("belden", 'N'));
		Word wordFromDatabase3 = new Word(new WordId("is", 'A'));

		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		when(wordDaoMock.updateBatch(anyListOf(Word.class))).thenReturn(true);
		when(wordDaoMock.findByWordString(anyString())).thenReturn(
				Arrays.asList(wordFromDatabase1), Arrays.asList(wordFromDatabase2),
				Arrays.asList(wordFromDatabase3), null, null, null);

		frequencyListImporterImpl.importFrequencyList();

		assertEquals(100, wordFromDatabase1.getFrequencyWeight());
		assertEquals(200, wordFromDatabase2.getFrequencyWeight());
		assertEquals(300, wordFromDatabase3.getFrequencyWeight());

		rowUpdateCountFromObject = (Integer) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);
		rowInsertCountFromObject = (Integer) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(3, rowUpdateCountFromObject.intValue());
		assertEquals(3, rowInsertCountFromObject.intValue());
		verify(wordDaoMock, times(2)).insertBatch(anyListOf(Word.class));
		verify(wordDaoMock, times(2)).updateBatch(anyListOf(Word.class));
		verify(wordDaoMock, times(6)).findByWordString(anyString());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testImportWord() {
		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();

		Field rowUpdateCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class,
				"rowUpdateCount");
		ReflectionUtils.makeAccessible(rowUpdateCountField);
		Integer rowUpdateCountFromObject = (Integer) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowUpdateCountFromObject.intValue());

		Field rowInsertCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class,
				"rowInsertCount");
		ReflectionUtils.makeAccessible(rowInsertCountField);
		Integer rowInsertCountFromObject = (Integer) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowInsertCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		when(wordDaoMock.updateBatch(anyListOf(Word.class))).thenReturn(true);

		frequencyListImporterImpl.setWordDao(wordDaoMock);
		frequencyListImporterImpl.setBatchSize(2);

		List<Word> insertBatch = new ArrayList<Word>();
		List<Word> updateBatch = new ArrayList<Word>();

		Word word1 = new Word(new WordId("george", 'N'), 100);
		Word word2 = new Word(new WordId("belden", 'N'), 200);
		Word word3 = new Word(new WordId("is", 'V'), 300);
		Word word4 = new Word(new WordId("awesome", 'A'), 400);
		Word wordFromDatabase1 = new Word(new WordId("george", 'N'));
		Word wordFromDatabase2 = new Word(new WordId("belden", 'N'));

		when(wordDaoMock.findByWordString(anyString())).thenReturn(
				Arrays.asList(wordFromDatabase1), Arrays.asList(wordFromDatabase2), null, null);

		frequencyListImporterImpl.importWord(word1, insertBatch, updateBatch);

		verify(wordDaoMock, never()).insertBatch(anyListOf(Word.class));
		verify(wordDaoMock, never()).updateBatch(anyListOf(Word.class));
		assertEquals(1, updateBatch.size());
		assertTrue(insertBatch.isEmpty());

		frequencyListImporterImpl.importWord(word2, insertBatch, updateBatch);

		verify(wordDaoMock, never()).insertBatch(anyListOf(Word.class));
		verify(wordDaoMock, times(1)).updateBatch(anyListOf(Word.class));
		assertTrue(updateBatch.isEmpty());
		assertTrue(insertBatch.isEmpty());

		frequencyListImporterImpl.importWord(word3, insertBatch, updateBatch);

		verify(wordDaoMock, never()).insertBatch(anyListOf(Word.class));
		verify(wordDaoMock, times(1)).updateBatch(anyListOf(Word.class));
		assertTrue(updateBatch.isEmpty());
		assertEquals(1, insertBatch.size());

		frequencyListImporterImpl.importWord(word4, insertBatch, updateBatch);

		verify(wordDaoMock, times(1)).insertBatch(anyListOf(Word.class));
		verify(wordDaoMock, times(1)).updateBatch(anyListOf(Word.class));
		assertTrue(updateBatch.isEmpty());
		assertTrue(insertBatch.isEmpty());

		rowUpdateCountFromObject = (Integer) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);
		rowInsertCountFromObject = (Integer) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(100, wordFromDatabase1.getFrequencyWeight());
		assertEquals(200, wordFromDatabase2.getFrequencyWeight());
		assertEquals(2, rowUpdateCountFromObject.intValue());
		assertEquals(2, rowInsertCountFromObject.intValue());
		verify(wordDaoMock, times(4)).findByWordString(anyString());
	}

	@Test
	public void testImportNullWord() {
		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();

		Field rowUpdateCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class,
				"rowUpdateCount");
		ReflectionUtils.makeAccessible(rowUpdateCountField);
		Integer rowUpdateCountFromObject = (Integer) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowUpdateCountFromObject.intValue());

		Field rowInsertCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class,
				"rowInsertCount");
		ReflectionUtils.makeAccessible(rowInsertCountField);
		Integer rowInsertCountFromObject = (Integer) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowInsertCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		when(wordDaoMock.updateBatch(anyListOf(Word.class))).thenReturn(true);

		frequencyListImporterImpl.setWordDao(wordDaoMock);
		frequencyListImporterImpl.setBatchSize(2);

		List<Word> insertBatch = new ArrayList<Word>();
		List<Word> updateBatch = new ArrayList<Word>();

		frequencyListImporterImpl.importWord(null, insertBatch, updateBatch);

		rowUpdateCountFromObject = (Integer) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);
		rowInsertCountFromObject = (Integer) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertTrue(updateBatch.isEmpty());
		assertTrue(insertBatch.isEmpty());
		assertEquals(0, rowUpdateCountFromObject.intValue());
		assertEquals(0, rowInsertCountFromObject.intValue());
		verifyZeroInteractions(wordDaoMock);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testImportWord_BatchSizeNotReached() {
		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();

		Field rowUpdateCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class,
				"rowUpdateCount");
		ReflectionUtils.makeAccessible(rowUpdateCountField);
		Integer rowUpdateCountFromObject = (Integer) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowUpdateCountFromObject.intValue());

		Field rowInsertCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class,
				"rowInsertCount");
		ReflectionUtils.makeAccessible(rowInsertCountField);
		Integer rowInsertCountFromObject = (Integer) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowInsertCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		when(wordDaoMock.updateBatch(anyListOf(Word.class))).thenReturn(true);

		frequencyListImporterImpl.setWordDao(wordDaoMock);
		frequencyListImporterImpl.setBatchSize(3);

		List<Word> insertBatch = new ArrayList<Word>();
		List<Word> updateBatch = new ArrayList<Word>();

		Word word1 = new Word(new WordId("george", 'N'), 100);
		Word word2 = new Word(new WordId("belden", 'N'), 200);
		Word word3 = new Word(new WordId("is", 'V'), 300);
		Word word4 = new Word(new WordId("awesome", 'A'), 400);
		Word wordFromDatabase1 = new Word(new WordId("george", 'N'));
		Word wordFromDatabase2 = new Word(new WordId("belden", 'N'));

		when(wordDaoMock.findByWordString(anyString())).thenReturn(
				Arrays.asList(wordFromDatabase1), Arrays.asList(wordFromDatabase2), null, null);

		frequencyListImporterImpl.importWord(word1, insertBatch, updateBatch);

		verify(wordDaoMock, never()).insertBatch(anyListOf(Word.class));
		verify(wordDaoMock, never()).updateBatch(anyListOf(Word.class));
		assertEquals(1, updateBatch.size());
		assertTrue(insertBatch.isEmpty());

		frequencyListImporterImpl.importWord(word2, insertBatch, updateBatch);

		verify(wordDaoMock, never()).insertBatch(anyListOf(Word.class));
		verify(wordDaoMock, never()).updateBatch(anyListOf(Word.class));
		assertEquals(2, updateBatch.size());
		assertTrue(insertBatch.isEmpty());

		frequencyListImporterImpl.importWord(word3, insertBatch, updateBatch);

		verify(wordDaoMock, never()).insertBatch(anyListOf(Word.class));
		verify(wordDaoMock, never()).updateBatch(anyListOf(Word.class));
		assertEquals(2, updateBatch.size());
		assertEquals(1, insertBatch.size());

		frequencyListImporterImpl.importWord(word4, insertBatch, updateBatch);

		verify(wordDaoMock, never()).insertBatch(anyListOf(Word.class));
		verify(wordDaoMock, never()).updateBatch(anyListOf(Word.class));
		assertEquals(2, updateBatch.size());
		assertEquals(2, insertBatch.size());

		rowUpdateCountFromObject = (Integer) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);
		rowInsertCountFromObject = (Integer) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(100, wordFromDatabase1.getFrequencyWeight());
		assertEquals(200, wordFromDatabase2.getFrequencyWeight());
		assertEquals(0, rowUpdateCountFromObject.intValue());
		assertEquals(0, rowInsertCountFromObject.intValue());
		verify(wordDaoMock, times(4)).findByWordString(anyString());
	}
}
