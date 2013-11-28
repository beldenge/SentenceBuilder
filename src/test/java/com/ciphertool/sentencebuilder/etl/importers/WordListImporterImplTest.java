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
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.ciphertool.sentencebuilder.dao.WordDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.sentencebuilder.etl.parsers.FileParser;
import com.ciphertool.sentencebuilder.etl.parsers.PartOfSpeechFileParser;

public class WordListImporterImplTest {

	@Test
	public void testSetWordDao() {
		WordDao wordDaoToSet = mock(WordDao.class);
		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();
		wordListImporterImpl.setWordDao(wordDaoToSet);

		Field wordDaoField = ReflectionUtils.findField(WordListImporterImpl.class, "wordDao");
		ReflectionUtils.makeAccessible(wordDaoField);
		WordDao wordDaoFromObject = (WordDao) ReflectionUtils.getField(wordDaoField,
				wordListImporterImpl);

		assertSame(wordDaoToSet, wordDaoFromObject);
	}

	@Test
	public void testSetBatchSize() {
		int batchSizeToSet = 99;
		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();
		wordListImporterImpl.setBatchSize(batchSizeToSet);

		Field batchSizeField = ReflectionUtils.findField(WordListImporterImpl.class, "batchSize");
		ReflectionUtils.makeAccessible(batchSizeField);
		int batchSizeFromObject = (int) ReflectionUtils.getField(batchSizeField,
				wordListImporterImpl);

		assertEquals(batchSizeToSet, batchSizeFromObject);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSetFileParser() {
		FileParser<Word> fileParserToSet = mock(FileParser.class);
		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();
		wordListImporterImpl.setFileParser(fileParserToSet);

		Field fileParserField = ReflectionUtils.findField(WordListImporterImpl.class,
				"partOfSpeechFileParser");
		ReflectionUtils.makeAccessible(fileParserField);
		FileParser<Word> fileParserFromObject = (FileParser<Word>) ReflectionUtils.getField(
				fileParserField, wordListImporterImpl);

		assertSame(fileParserToSet, fileParserFromObject);
	}

	@Test
	public void testImportWordList() {
		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();

		Field rowCountField = ReflectionUtils.findField(WordListImporterImpl.class, "rowCount");
		ReflectionUtils.makeAccessible(rowCountField);
		Integer rowCountFromObject = (Integer) ReflectionUtils.getField(rowCountField,
				wordListImporterImpl);

		assertEquals(0, rowCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		int batchSizeToSet = 3;

		wordListImporterImpl.setWordDao(wordDaoMock);
		wordListImporterImpl.setBatchSize(batchSizeToSet);

		Word word1 = new Word(new WordId("george", 'N'));
		Word word2 = new Word(new WordId("elmer", 'N'));
		Word word3 = new Word(new WordId("belden", 'N'));
		List<Word> wordsToReturn = new ArrayList<Word>();
		wordsToReturn.add(word1);
		wordsToReturn.add(word2);
		wordsToReturn.add(word3);
		PartOfSpeechFileParser fileParserMock = mock(PartOfSpeechFileParser.class);
		when(fileParserMock.parseFile()).thenReturn(wordsToReturn);

		wordListImporterImpl.setFileParser(fileParserMock);

		wordListImporterImpl.importWordList();

		rowCountFromObject = (Integer) ReflectionUtils
				.getField(rowCountField, wordListImporterImpl);

		assertEquals(3, rowCountFromObject.intValue());
		verify(wordDaoMock, times(1)).insertBatch(anyListOf(Word.class));
	}

	@Test
	public void testImportWordList_LeftoversFromBatch() {
		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();

		Field rowCountField = ReflectionUtils.findField(WordListImporterImpl.class, "rowCount");
		ReflectionUtils.makeAccessible(rowCountField);
		Integer rowCountFromObject = (Integer) ReflectionUtils.getField(rowCountField,
				wordListImporterImpl);

		assertEquals(0, rowCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		int batchSizeToSet = 2;

		wordListImporterImpl.setWordDao(wordDaoMock);
		wordListImporterImpl.setBatchSize(batchSizeToSet);

		Word word1 = new Word(new WordId("george", 'N'));
		Word word2 = new Word(new WordId("elmer", 'N'));
		Word word3 = new Word(new WordId("belden", 'N'));
		List<Word> wordsToReturn = new ArrayList<Word>();
		wordsToReturn.add(word1);
		wordsToReturn.add(word2);
		wordsToReturn.add(word3);
		PartOfSpeechFileParser fileParserMock = mock(PartOfSpeechFileParser.class);
		when(fileParserMock.parseFile()).thenReturn(wordsToReturn);

		wordListImporterImpl.setFileParser(fileParserMock);

		wordListImporterImpl.importWordList();

		rowCountFromObject = (Integer) ReflectionUtils
				.getField(rowCountField, wordListImporterImpl);

		assertEquals(3, rowCountFromObject.intValue());
		verify(wordDaoMock, times(2)).insertBatch(anyListOf(Word.class));
	}

	@Test
	public void testImportWord() {
		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();

		Field rowCountField = ReflectionUtils.findField(WordListImporterImpl.class, "rowCount");
		ReflectionUtils.makeAccessible(rowCountField);
		Integer rowCountFromObject = (Integer) ReflectionUtils.getField(rowCountField,
				wordListImporterImpl);

		assertEquals(0, rowCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);

		wordListImporterImpl.setWordDao(wordDaoMock);
		wordListImporterImpl.setBatchSize(3);

		List<Word> wordBatch = new ArrayList<Word>();

		Word word1 = new Word(new WordId("george", 'N'));
		wordListImporterImpl.importWord(word1, wordBatch);

		verify(wordDaoMock, never()).insertBatch(anyListOf(Word.class));
		assertEquals(1, wordBatch.size());

		Word word2 = new Word(new WordId("elmer", 'N'));
		wordListImporterImpl.importWord(word2, wordBatch);

		verify(wordDaoMock, never()).insertBatch(anyListOf(Word.class));
		assertEquals(2, wordBatch.size());

		Word word3 = new Word(new WordId("belden", 'N'));
		wordListImporterImpl.importWord(word3, wordBatch);

		rowCountFromObject = (Integer) ReflectionUtils
				.getField(rowCountField, wordListImporterImpl);

		assertEquals(3, rowCountFromObject.intValue());
		verify(wordDaoMock, times(1)).insertBatch(same(wordBatch));
		assertTrue(wordBatch.isEmpty());
	}

	@Test
	public void testImportNullWord() {
		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();

		Field rowCountField = ReflectionUtils.findField(WordListImporterImpl.class, "rowCount");
		ReflectionUtils.makeAccessible(rowCountField);
		Integer rowCountFromObject = (Integer) ReflectionUtils.getField(rowCountField,
				wordListImporterImpl);

		assertEquals(0, rowCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);

		wordListImporterImpl.setWordDao(wordDaoMock);
		wordListImporterImpl.setBatchSize(3);

		List<Word> wordBatch = new ArrayList<Word>();
		wordListImporterImpl.importWord(null, wordBatch);

		rowCountFromObject = (Integer) ReflectionUtils
				.getField(rowCountField, wordListImporterImpl);

		assertEquals(0, rowCountFromObject.intValue());
		verifyZeroInteractions(wordDaoMock);
		assertTrue(wordBatch.isEmpty());
	}

	@Test
	public void testImportWord_BatchSizeNotReached() {
		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();

		Field rowCountField = ReflectionUtils.findField(WordListImporterImpl.class, "rowCount");
		ReflectionUtils.makeAccessible(rowCountField);
		Integer rowCountFromObject = (Integer) ReflectionUtils.getField(rowCountField,
				wordListImporterImpl);

		assertEquals(0, rowCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);

		wordListImporterImpl.setWordDao(wordDaoMock);
		wordListImporterImpl.setBatchSize(4);

		List<Word> wordBatch = new ArrayList<Word>();

		Word word1 = new Word(new WordId("george", 'N'));
		wordListImporterImpl.importWord(word1, wordBatch);

		verify(wordDaoMock, never()).insertBatch(anyListOf(Word.class));
		assertEquals(1, wordBatch.size());

		Word word2 = new Word(new WordId("elmer", 'N'));
		wordListImporterImpl.importWord(word2, wordBatch);

		verify(wordDaoMock, never()).insertBatch(anyListOf(Word.class));
		assertEquals(2, wordBatch.size());

		Word word3 = new Word(new WordId("belden", 'N'));
		wordListImporterImpl.importWord(word3, wordBatch);

		rowCountFromObject = (Integer) ReflectionUtils
				.getField(rowCountField, wordListImporterImpl);

		assertEquals(0, rowCountFromObject.intValue());
		verify(wordDaoMock, never()).insertBatch(anyListOf(Word.class));
		assertEquals(3, wordBatch.size());
		assertSame(word1, wordBatch.get(0));
		assertSame(word2, wordBatch.get(1));
		assertSame(word3, wordBatch.get(2));
	}
}
