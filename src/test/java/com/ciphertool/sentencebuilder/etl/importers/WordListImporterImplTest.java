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

package com.ciphertool.sentencebuilder.etl.importers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.ReflectionUtils;

import com.ciphertool.sentencebuilder.dao.WordDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.enumerations.PartOfSpeechType;
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
		WordDao wordDaoFromObject = (WordDao) ReflectionUtils.getField(wordDaoField, wordListImporterImpl);

		assertSame(wordDaoToSet, wordDaoFromObject);
	}

	@Test
	public void testSetPersistenceBatchSize() {
		int persistenceBatchSizeToSet = 99;
		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();
		wordListImporterImpl.setPersistenceBatchSize(persistenceBatchSizeToSet);

		Field persistenceBatchSizeField = ReflectionUtils.findField(WordListImporterImpl.class, "persistenceBatchSize");
		ReflectionUtils.makeAccessible(persistenceBatchSizeField);
		int persistenceBatchSizeFromObject = (int) ReflectionUtils.getField(persistenceBatchSizeField,
				wordListImporterImpl);

		assertEquals(persistenceBatchSizeToSet, persistenceBatchSizeFromObject);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSetFileParser() {
		FileParser<Word> fileParserToSet = mock(FileParser.class);
		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();
		wordListImporterImpl.setFileParser(fileParserToSet);

		Field fileParserField = ReflectionUtils.findField(WordListImporterImpl.class, "partOfSpeechFileParser");
		ReflectionUtils.makeAccessible(fileParserField);
		FileParser<Word> fileParserFromObject = (FileParser<Word>) ReflectionUtils.getField(fileParserField,
				wordListImporterImpl);

		assertSame(fileParserToSet, fileParserFromObject);
	}

	@Test
	public void testSetConcurrencyBatchSize() {
		int concurrencyBatchSizeToSet = 250;
		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();
		wordListImporterImpl.setConcurrencyBatchSize(concurrencyBatchSizeToSet);

		Field concurrencyBatchSizeField = ReflectionUtils.findField(WordListImporterImpl.class, "concurrencyBatchSize");
		ReflectionUtils.makeAccessible(concurrencyBatchSizeField);
		int concurrencyBatchSizeFromObject = (int) ReflectionUtils.getField(concurrencyBatchSizeField,
				wordListImporterImpl);

		assertEquals(concurrencyBatchSizeToSet, concurrencyBatchSizeFromObject);
	}

	@Test
	public void testSetTaskExecutor() {
		TaskExecutor taskExecutorToSet = mock(TaskExecutor.class);
		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();
		wordListImporterImpl.setTaskExecutor(taskExecutorToSet);

		Field taskExecutorField = ReflectionUtils.findField(WordListImporterImpl.class, "taskExecutor");
		ReflectionUtils.makeAccessible(taskExecutorField);
		TaskExecutor taskExecutorFromObject = (TaskExecutor) ReflectionUtils.getField(taskExecutorField,
				wordListImporterImpl);

		assertEquals(taskExecutorToSet, taskExecutorFromObject);
	}

	@Test
	public void testSetFilename() {
		String[] fileNamesToSet = new String[] { "arbitraryFileName" };

		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();
		wordListImporterImpl.setFileNames(fileNamesToSet);

		Field fileNamesField = ReflectionUtils.findField(WordListImporterImpl.class, "fileNames");
		ReflectionUtils.makeAccessible(fileNamesField);
		String[] fileNamesFromObject = (String[]) ReflectionUtils.getField(fileNamesField, wordListImporterImpl);

		assertSame(fileNamesToSet, fileNamesFromObject);
	}

	@Test
	public void testImportWordList() {
		ThreadPoolTaskExecutor taskExecutorSpy = spy(new ThreadPoolTaskExecutor());
		taskExecutorSpy.setCorePoolSize(4);
		taskExecutorSpy.setMaxPoolSize(4);
		taskExecutorSpy.setQueueCapacity(100);
		taskExecutorSpy.setKeepAliveSeconds(1);
		taskExecutorSpy.setAllowCoreThreadTimeOut(true);
		taskExecutorSpy.initialize();

		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();
		wordListImporterImpl.setTaskExecutor(taskExecutorSpy);

		Field rowCountField = ReflectionUtils.findField(WordListImporterImpl.class, "rowCount");
		ReflectionUtils.makeAccessible(rowCountField);
		AtomicInteger rowCountFromObject = (AtomicInteger) ReflectionUtils
				.getField(rowCountField, wordListImporterImpl);

		assertEquals(0, rowCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		int persistenceBatchSizeToSet = 3;
		int concurrencyBatchSizeToSet = 3;

		wordListImporterImpl.setWordDao(wordDaoMock);
		wordListImporterImpl.setPersistenceBatchSize(persistenceBatchSizeToSet);
		wordListImporterImpl.setConcurrencyBatchSize(concurrencyBatchSizeToSet);

		Word word1 = new Word("george", PartOfSpeechType.NOUN);
		Word word2 = new Word("elmer", PartOfSpeechType.NOUN);
		Word word3 = new Word("belden", PartOfSpeechType.NOUN);
		List<Word> wordsToReturn = new ArrayList<Word>();
		wordsToReturn.add(word1);
		wordsToReturn.add(word2);
		wordsToReturn.add(word3);
		PartOfSpeechFileParser fileParserMock = mock(PartOfSpeechFileParser.class);
		when(fileParserMock.parseFile(anyString())).thenReturn(wordsToReturn);

		wordListImporterImpl.setFileParser(fileParserMock);

		wordListImporterImpl.setFileNames(new String[] { "arbitraryFileName" });
		wordListImporterImpl.importWordList();

		rowCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowCountField, wordListImporterImpl);

		assertEquals(3, rowCountFromObject.intValue());
		verify(wordDaoMock, times(1)).insertBatch(anyListOf(Word.class));
		verify(taskExecutorSpy, times(1)).execute(any(Runnable.class));
	}

	@Test
	public void testImportWordList_LeftoversFromBatch() {
		ThreadPoolTaskExecutor taskExecutorSpy = spy(new ThreadPoolTaskExecutor());
		taskExecutorSpy.setCorePoolSize(4);
		taskExecutorSpy.setMaxPoolSize(4);
		taskExecutorSpy.setQueueCapacity(100);
		taskExecutorSpy.setKeepAliveSeconds(1);
		taskExecutorSpy.setAllowCoreThreadTimeOut(true);
		taskExecutorSpy.initialize();

		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();
		wordListImporterImpl.setTaskExecutor(taskExecutorSpy);

		Field rowCountField = ReflectionUtils.findField(WordListImporterImpl.class, "rowCount");
		ReflectionUtils.makeAccessible(rowCountField);
		AtomicInteger rowCountFromObject = (AtomicInteger) ReflectionUtils
				.getField(rowCountField, wordListImporterImpl);

		assertEquals(0, rowCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		int persistenceBatchSizeToSet = 3;
		int concurrencyBatchSizeToSet = 2;

		wordListImporterImpl.setWordDao(wordDaoMock);
		wordListImporterImpl.setPersistenceBatchSize(persistenceBatchSizeToSet);
		wordListImporterImpl.setConcurrencyBatchSize(concurrencyBatchSizeToSet);

		Word word1 = new Word("george", PartOfSpeechType.NOUN);
		Word word2 = new Word("elmer", PartOfSpeechType.NOUN);
		Word word3 = new Word("belden", PartOfSpeechType.NOUN);
		List<Word> wordsToReturn = new ArrayList<Word>();
		wordsToReturn.add(word1);
		wordsToReturn.add(word2);
		wordsToReturn.add(word3);
		PartOfSpeechFileParser fileParserMock = mock(PartOfSpeechFileParser.class);
		when(fileParserMock.parseFile(anyString())).thenReturn(wordsToReturn);

		wordListImporterImpl.setFileParser(fileParserMock);

		wordListImporterImpl.setFileNames(new String[] { "arbitraryFileName" });
		wordListImporterImpl.importWordList();

		rowCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowCountField, wordListImporterImpl);

		assertEquals(3, rowCountFromObject.intValue());
		verify(wordDaoMock, times(2)).insertBatch(anyListOf(Word.class));
		verify(taskExecutorSpy, times(2)).execute(any(Runnable.class));
	}

	@Test
	public void testImportWord() {
		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();

		Field rowCountField = ReflectionUtils.findField(WordListImporterImpl.class, "rowCount");
		ReflectionUtils.makeAccessible(rowCountField);
		AtomicInteger rowCountFromObject = (AtomicInteger) ReflectionUtils
				.getField(rowCountField, wordListImporterImpl);

		assertEquals(0, rowCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);

		wordListImporterImpl.setWordDao(wordDaoMock);
		wordListImporterImpl.setPersistenceBatchSize(3);

		List<Word> wordBatch = new ArrayList<Word>();

		Word word1 = new Word("george", PartOfSpeechType.NOUN);
		wordListImporterImpl.importWord(word1, wordBatch);

		verify(wordDaoMock, never()).insertBatch(anyListOf(Word.class));
		assertEquals(1, wordBatch.size());

		Word word2 = new Word("elmer", PartOfSpeechType.NOUN);
		wordListImporterImpl.importWord(word2, wordBatch);

		verify(wordDaoMock, never()).insertBatch(anyListOf(Word.class));
		assertEquals(2, wordBatch.size());

		Word word3 = new Word("belden", PartOfSpeechType.NOUN);
		wordListImporterImpl.importWord(word3, wordBatch);

		rowCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowCountField, wordListImporterImpl);

		assertEquals(3, rowCountFromObject.intValue());
		verify(wordDaoMock, times(1)).insertBatch(same(wordBatch));
		assertTrue(wordBatch.isEmpty());
	}

	@Test
	public void testImportNullWord() {
		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();

		Field rowCountField = ReflectionUtils.findField(WordListImporterImpl.class, "rowCount");
		ReflectionUtils.makeAccessible(rowCountField);
		AtomicInteger rowCountFromObject = (AtomicInteger) ReflectionUtils
				.getField(rowCountField, wordListImporterImpl);

		assertEquals(0, rowCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);

		wordListImporterImpl.setWordDao(wordDaoMock);
		wordListImporterImpl.setPersistenceBatchSize(3);

		List<Word> wordBatch = new ArrayList<Word>();
		wordListImporterImpl.importWord(null, wordBatch);

		rowCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowCountField, wordListImporterImpl);

		assertEquals(0, rowCountFromObject.intValue());
		verifyZeroInteractions(wordDaoMock);
		assertTrue(wordBatch.isEmpty());
	}

	@Test
	public void testImportWord_BatchSizeNotReached() {
		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();

		Field rowCountField = ReflectionUtils.findField(WordListImporterImpl.class, "rowCount");
		ReflectionUtils.makeAccessible(rowCountField);
		AtomicInteger rowCountFromObject = (AtomicInteger) ReflectionUtils
				.getField(rowCountField, wordListImporterImpl);

		assertEquals(0, rowCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);

		wordListImporterImpl.setWordDao(wordDaoMock);
		wordListImporterImpl.setPersistenceBatchSize(4);

		List<Word> wordBatch = new ArrayList<Word>();

		Word word1 = new Word("george", PartOfSpeechType.NOUN);
		wordListImporterImpl.importWord(word1, wordBatch);

		verify(wordDaoMock, never()).insertBatch(anyListOf(Word.class));
		assertEquals(1, wordBatch.size());

		Word word2 = new Word("elmer", PartOfSpeechType.NOUN);
		wordListImporterImpl.importWord(word2, wordBatch);

		verify(wordDaoMock, never()).insertBatch(anyListOf(Word.class));
		assertEquals(2, wordBatch.size());

		Word word3 = new Word("belden", PartOfSpeechType.NOUN);
		wordListImporterImpl.importWord(word3, wordBatch);

		rowCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowCountField, wordListImporterImpl);

		assertEquals(0, rowCountFromObject.intValue());
		verify(wordDaoMock, never()).insertBatch(anyListOf(Word.class));
		assertEquals(3, wordBatch.size());
		assertSame(word1, wordBatch.get(0));
		assertSame(word2, wordBatch.get(1));
		assertSame(word3, wordBatch.get(2));
	}

	@Test
	public void testBatchWordImportTask() {
		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();

		Word word1 = new Word("george", PartOfSpeechType.NOUN);
		Word word2 = new Word("elmer", PartOfSpeechType.NOUN);
		Word word3 = new Word("belden", PartOfSpeechType.NOUN);
		List<Word> threadBatch = new ArrayList<Word>();
		threadBatch.add(word1);
		threadBatch.add(word2);
		threadBatch.add(word3);

		WordListImporterImpl.BatchWordImportTask batchWordImportTask = wordListImporterImpl.new BatchWordImportTask(
				threadBatch);

		Field rowCountField = ReflectionUtils.findField(WordListImporterImpl.class, "rowCount");
		ReflectionUtils.makeAccessible(rowCountField);
		AtomicInteger rowCountFromObject = (AtomicInteger) ReflectionUtils
				.getField(rowCountField, wordListImporterImpl);

		assertEquals(0, rowCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);

		int persistenceBatchSizeToSet = 3;
		int concurrencyBatchSizeToSet = 3;

		wordListImporterImpl.setWordDao(wordDaoMock);
		wordListImporterImpl.setPersistenceBatchSize(persistenceBatchSizeToSet);
		wordListImporterImpl.setConcurrencyBatchSize(concurrencyBatchSizeToSet);

		try {
			batchWordImportTask.call();
		} catch (Exception e) {
			fail(e.getMessage());
		}

		rowCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowCountField, wordListImporterImpl);

		assertEquals(3, rowCountFromObject.intValue());
		verify(wordDaoMock, times(1)).insertBatch(anyListOf(Word.class));
	}

	@Test
	public void testBatchWordImportTask_LeftoversFromBatch() {
		WordListImporterImpl wordListImporterImpl = new WordListImporterImpl();

		Word word1 = new Word("george", PartOfSpeechType.NOUN);
		Word word2 = new Word("elmer", PartOfSpeechType.NOUN);
		Word word3 = new Word("belden", PartOfSpeechType.NOUN);
		List<Word> threadBatch = new ArrayList<Word>();
		threadBatch.add(word1);
		threadBatch.add(word2);
		threadBatch.add(word3);

		WordListImporterImpl.BatchWordImportTask batchWordImportTask = wordListImporterImpl.new BatchWordImportTask(
				threadBatch);

		Field rowCountField = ReflectionUtils.findField(WordListImporterImpl.class, "rowCount");
		ReflectionUtils.makeAccessible(rowCountField);
		AtomicInteger rowCountFromObject = (AtomicInteger) ReflectionUtils
				.getField(rowCountField, wordListImporterImpl);

		assertEquals(0, rowCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);

		int persistenceBatchSizeToSet = 2;
		int concurrencyBatchSizeToSet = 3;

		wordListImporterImpl.setWordDao(wordDaoMock);
		wordListImporterImpl.setPersistenceBatchSize(persistenceBatchSizeToSet);
		wordListImporterImpl.setConcurrencyBatchSize(concurrencyBatchSizeToSet);

		try {
			batchWordImportTask.call();
		} catch (Exception e) {
			fail(e.getMessage());
		}

		rowCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowCountField, wordListImporterImpl);

		assertEquals(3, rowCountFromObject.intValue());
		verify(wordDaoMock, times(2)).insertBatch(anyListOf(Word.class));
	}
}
