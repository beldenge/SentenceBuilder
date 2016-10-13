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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.ciphertool.sentencebuilder.etl.parsers.FrequencyFileParser;

public class FrequencyListImporterImplTest {

	@Test
	public void testSetWordDao() {
		WordDao wordDaoToSet = mock(WordDao.class);
		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();
		frequencyListImporterImpl.setWordDao(wordDaoToSet);

		Field wordDaoField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "wordDao");
		ReflectionUtils.makeAccessible(wordDaoField);
		WordDao wordDaoFromObject = (WordDao) ReflectionUtils.getField(wordDaoField, frequencyListImporterImpl);

		assertSame(wordDaoToSet, wordDaoFromObject);
	}

	@Test
	public void testSetPersistenceBatchSize() {
		int persistenceBatchSizeToSet = 99;
		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();
		frequencyListImporterImpl.setPersistenceBatchSize(persistenceBatchSizeToSet);

		Field persistenceBatchSizeField = ReflectionUtils.findField(FrequencyListImporterImpl.class,
				"persistenceBatchSize");
		ReflectionUtils.makeAccessible(persistenceBatchSizeField);
		int persistenceBatchSizeFromObject = (int) ReflectionUtils.getField(persistenceBatchSizeField,
				frequencyListImporterImpl);

		assertEquals(persistenceBatchSizeToSet, persistenceBatchSizeFromObject);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSetFileParser() {
		FileParser<Word> fileParserToSet = mock(FileParser.class);
		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();
		frequencyListImporterImpl.setFileParser(fileParserToSet);

		Field fileParserField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "frequencyFileParser");
		ReflectionUtils.makeAccessible(fileParserField);
		FileParser<Word> fileParserFromObject = (FileParser<Word>) ReflectionUtils.getField(fileParserField,
				frequencyListImporterImpl);

		assertSame(fileParserToSet, fileParserFromObject);
	}

	@Test
	public void testSetConcurrencyBatchSize() {
		int concurrencyBatchSizeToSet = 250;
		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();
		frequencyListImporterImpl.setConcurrencyBatchSize(concurrencyBatchSizeToSet);

		Field concurrencyBatchSizeField = ReflectionUtils.findField(FrequencyListImporterImpl.class,
				"concurrencyBatchSize");
		ReflectionUtils.makeAccessible(concurrencyBatchSizeField);
		int concurrencyBatchSizeFromObject = (int) ReflectionUtils.getField(concurrencyBatchSizeField,
				frequencyListImporterImpl);

		assertEquals(concurrencyBatchSizeToSet, concurrencyBatchSizeFromObject);
	}

	@Test
	public void testSetTaskExecutor() {
		TaskExecutor taskExecutorToSet = mock(TaskExecutor.class);
		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();
		frequencyListImporterImpl.setTaskExecutor(taskExecutorToSet);

		Field taskExecutorField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "taskExecutor");
		ReflectionUtils.makeAccessible(taskExecutorField);
		TaskExecutor taskExecutorFromObject = (TaskExecutor) ReflectionUtils.getField(taskExecutorField,
				frequencyListImporterImpl);

		assertEquals(taskExecutorToSet, taskExecutorFromObject);
	}

	@Test
	public void testSetFilename() {
		String[] fileNamesToSet = new String[] { "arbitraryFileName" };

		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();
		frequencyListImporterImpl.setFileNames(fileNamesToSet);

		Field fileNamesField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "fileNames");
		ReflectionUtils.makeAccessible(fileNamesField);
		String[] fileNamesFromObject = (String[]) ReflectionUtils.getField(fileNamesField, frequencyListImporterImpl);

		assertSame(fileNamesToSet, fileNamesFromObject);
	}

	@Test
	public void testImportFrequencyList() {
		ThreadPoolTaskExecutor taskExecutorSpy = spy(new ThreadPoolTaskExecutor());
		taskExecutorSpy.setCorePoolSize(4);
		taskExecutorSpy.setMaxPoolSize(4);
		taskExecutorSpy.setQueueCapacity(100);
		taskExecutorSpy.setKeepAliveSeconds(1);
		taskExecutorSpy.setAllowCoreThreadTimeOut(true);
		taskExecutorSpy.initialize();

		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();
		frequencyListImporterImpl.setTaskExecutor(taskExecutorSpy);

		Field rowUpdateCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "rowUpdateCount");
		ReflectionUtils.makeAccessible(rowUpdateCountField);
		AtomicInteger rowUpdateCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowUpdateCountFromObject.intValue());

		Field rowInsertCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "rowInsertCount");
		ReflectionUtils.makeAccessible(rowInsertCountField);
		AtomicInteger rowInsertCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowInsertCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		int persistenceBatchSizeToSet = 2;
		int concurrencyBatchSizeToSet = 2;

		frequencyListImporterImpl.setWordDao(wordDaoMock);
		frequencyListImporterImpl.setPersistenceBatchSize(persistenceBatchSizeToSet);
		frequencyListImporterImpl.setConcurrencyBatchSize(concurrencyBatchSizeToSet);

		Word word1 = new Word("george", PartOfSpeechType.NOUN, 100);
		Word word2 = new Word("belden", PartOfSpeechType.NOUN, 200);
		Word word3 = new Word("is", PartOfSpeechType.VERB_PARTICIPLE, 300);
		Word word4 = new Word("awesome", PartOfSpeechType.ADJECTIVE, 400);
		List<Word> wordsToReturn = new ArrayList<Word>();
		wordsToReturn.add(word1);
		wordsToReturn.add(word2);
		wordsToReturn.add(word3);
		wordsToReturn.add(word4);
		FrequencyFileParser fileParserMock = mock(FrequencyFileParser.class);
		when(fileParserMock.parseFile(anyString())).thenReturn(wordsToReturn);

		frequencyListImporterImpl.setFileParser(fileParserMock);

		Word wordFromDatabase1 = new Word("george", PartOfSpeechType.NOUN);
		Word wordFromDatabase2 = new Word("belden", PartOfSpeechType.NOUN);

		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		when(wordDaoMock.updateBatch(anyListOf(Word.class))).thenReturn(true);
		when(wordDaoMock.findByWordString(eq("george"))).thenReturn(Arrays.asList(wordFromDatabase1));
		when(wordDaoMock.findByWordString(eq("belden"))).thenReturn(Arrays.asList(wordFromDatabase2));
		when(wordDaoMock.findByWordString(eq("is"))).thenReturn(null);
		when(wordDaoMock.findByWordString(eq("awesome"))).thenReturn(null);

		frequencyListImporterImpl.setFileNames(new String[] { "arbitraryFileName" });
		frequencyListImporterImpl.importFrequencyList();

		assertEquals(100, wordFromDatabase1.getFrequencyWeight());
		assertEquals(200, wordFromDatabase2.getFrequencyWeight());

		rowUpdateCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);
		rowInsertCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(2, rowUpdateCountFromObject.intValue());
		assertEquals(2, rowInsertCountFromObject.intValue());
		verify(wordDaoMock, times(1)).insertBatch(anyListOf(Word.class));
		verify(wordDaoMock, times(1)).updateBatch(anyListOf(Word.class));
		verify(wordDaoMock, times(4)).findByWordString(anyString());
		verify(taskExecutorSpy, times(2)).execute(any(Runnable.class));
	}

	@Test
	public void testImportFrequencyList_LeftoversFromBatch() {
		ThreadPoolTaskExecutor taskExecutorSpy = spy(new ThreadPoolTaskExecutor());
		taskExecutorSpy.setCorePoolSize(4);
		taskExecutorSpy.setMaxPoolSize(4);
		taskExecutorSpy.setQueueCapacity(100);
		taskExecutorSpy.setKeepAliveSeconds(1);
		taskExecutorSpy.setAllowCoreThreadTimeOut(true);
		taskExecutorSpy.initialize();

		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();
		frequencyListImporterImpl.setTaskExecutor(taskExecutorSpy);

		Field rowUpdateCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "rowUpdateCount");
		ReflectionUtils.makeAccessible(rowUpdateCountField);
		AtomicInteger rowUpdateCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowUpdateCountFromObject.intValue());

		Field rowInsertCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "rowInsertCount");
		ReflectionUtils.makeAccessible(rowInsertCountField);
		AtomicInteger rowInsertCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowInsertCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		int persistenceBatchSizeToSet = 3;
		int concurrencyBatchSizeToSet = 2;

		frequencyListImporterImpl.setWordDao(wordDaoMock);
		frequencyListImporterImpl.setPersistenceBatchSize(persistenceBatchSizeToSet);
		frequencyListImporterImpl.setConcurrencyBatchSize(concurrencyBatchSizeToSet);

		Word word1 = new Word("george", PartOfSpeechType.NOUN, 100);
		Word word2 = new Word("belden", PartOfSpeechType.NOUN, 200);
		Word word3 = new Word("is", PartOfSpeechType.VERB_PARTICIPLE, 300);
		Word word4 = new Word("super", PartOfSpeechType.ADJECTIVE, 400);
		Word word5 = new Word("awesome", PartOfSpeechType.ADJECTIVE, 500);
		List<Word> wordsToReturn = new ArrayList<Word>();
		wordsToReturn.add(word1);
		wordsToReturn.add(word2);
		wordsToReturn.add(word3);
		wordsToReturn.add(word4);
		wordsToReturn.add(word5);
		FrequencyFileParser fileParserMock = mock(FrequencyFileParser.class);
		when(fileParserMock.parseFile(anyString())).thenReturn(wordsToReturn);

		frequencyListImporterImpl.setFileParser(fileParserMock);

		Word wordFromDatabase1 = new Word("george", PartOfSpeechType.NOUN);
		Word wordFromDatabase2 = new Word("belden", PartOfSpeechType.NOUN);
		Word wordFromDatabase3 = new Word("is", PartOfSpeechType.ADJECTIVE);

		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		when(wordDaoMock.updateBatch(anyListOf(Word.class))).thenReturn(true);
		when(wordDaoMock.findByWordString(eq("george"))).thenReturn(Arrays.asList(wordFromDatabase1));
		when(wordDaoMock.findByWordString(eq("belden"))).thenReturn(Arrays.asList(wordFromDatabase2));
		when(wordDaoMock.findByWordString(eq("is"))).thenReturn(

		Arrays.asList(wordFromDatabase3));
		when(wordDaoMock.findByWordString(eq("super"))).thenReturn(null);
		when(wordDaoMock.findByWordString(eq("seriously"))).thenReturn(null);
		when(wordDaoMock.findByWordString(eq("awesome"))).thenReturn(null);

		frequencyListImporterImpl.setFileNames(new String[] { "arbitraryFileName" });
		frequencyListImporterImpl.importFrequencyList();

		assertEquals(100, wordFromDatabase1.getFrequencyWeight());
		assertEquals(200, wordFromDatabase2.getFrequencyWeight());
		assertEquals(300, wordFromDatabase3.getFrequencyWeight());

		rowUpdateCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);
		rowInsertCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(3, rowUpdateCountFromObject.intValue());
		assertEquals(2, rowInsertCountFromObject.intValue());
		verify(wordDaoMock, times(2)).insertBatch(anyListOf(Word.class));
		verify(wordDaoMock, times(2)).updateBatch(anyListOf(Word.class));
		verify(wordDaoMock, times(5)).findByWordString(anyString());
		verify(taskExecutorSpy, times(3)).execute(any(Runnable.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testImportWord() {
		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();

		Field rowUpdateCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "rowUpdateCount");
		ReflectionUtils.makeAccessible(rowUpdateCountField);
		AtomicInteger rowUpdateCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowUpdateCountFromObject.intValue());

		Field rowInsertCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "rowInsertCount");
		ReflectionUtils.makeAccessible(rowInsertCountField);
		AtomicInteger rowInsertCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowInsertCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		when(wordDaoMock.updateBatch(anyListOf(Word.class))).thenReturn(true);

		frequencyListImporterImpl.setWordDao(wordDaoMock);
		frequencyListImporterImpl.setPersistenceBatchSize(2);

		List<Word> insertBatch = new ArrayList<Word>();
		List<Word> updateBatch = new ArrayList<Word>();

		Word word1 = new Word("george", PartOfSpeechType.NOUN, 100);
		Word word2 = new Word("belden", PartOfSpeechType.NOUN, 200);
		Word word3 = new Word("is", PartOfSpeechType.VERB_PARTICIPLE, 300);
		Word word4 = new Word("awesome", PartOfSpeechType.ADJECTIVE, 400);
		Word wordFromDatabase1 = new Word("george", PartOfSpeechType.NOUN);
		Word wordFromDatabase2 = new Word("belden", PartOfSpeechType.NOUN);

		when(wordDaoMock.findByWordString(anyString())).thenReturn(Arrays.asList(wordFromDatabase1),
				Arrays.asList(wordFromDatabase2), null, null);

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

		rowUpdateCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);
		rowInsertCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowInsertCountField,
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

		Field rowUpdateCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "rowUpdateCount");
		ReflectionUtils.makeAccessible(rowUpdateCountField);
		AtomicInteger rowUpdateCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowUpdateCountFromObject.intValue());

		Field rowInsertCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "rowInsertCount");
		ReflectionUtils.makeAccessible(rowInsertCountField);
		AtomicInteger rowInsertCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowInsertCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		when(wordDaoMock.updateBatch(anyListOf(Word.class))).thenReturn(true);

		frequencyListImporterImpl.setWordDao(wordDaoMock);
		frequencyListImporterImpl.setPersistenceBatchSize(2);

		List<Word> insertBatch = new ArrayList<Word>();
		List<Word> updateBatch = new ArrayList<Word>();

		frequencyListImporterImpl.importWord(null, insertBatch, updateBatch);

		rowUpdateCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);
		rowInsertCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertTrue(updateBatch.isEmpty());
		assertTrue(insertBatch.isEmpty());
		assertEquals(0, rowUpdateCountFromObject.intValue());
		assertEquals(0, rowInsertCountFromObject.intValue());
		verifyZeroInteractions(wordDaoMock);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testImportWord_BatchSizeNotReached() {
		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();

		Field rowUpdateCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "rowUpdateCount");
		ReflectionUtils.makeAccessible(rowUpdateCountField);
		AtomicInteger rowUpdateCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowUpdateCountFromObject.intValue());

		Field rowInsertCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "rowInsertCount");
		ReflectionUtils.makeAccessible(rowInsertCountField);
		AtomicInteger rowInsertCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowInsertCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		when(wordDaoMock.updateBatch(anyListOf(Word.class))).thenReturn(true);

		frequencyListImporterImpl.setWordDao(wordDaoMock);
		frequencyListImporterImpl.setPersistenceBatchSize(3);

		List<Word> insertBatch = new ArrayList<Word>();
		List<Word> updateBatch = new ArrayList<Word>();

		Word word1 = new Word("george", PartOfSpeechType.NOUN, 100);
		Word word2 = new Word("belden", PartOfSpeechType.NOUN, 200);
		Word word3 = new Word("is", PartOfSpeechType.VERB_PARTICIPLE, 300);
		Word word4 = new Word("awesome", PartOfSpeechType.ADJECTIVE, 400);
		Word wordFromDatabase1 = new Word("george", PartOfSpeechType.NOUN);
		Word wordFromDatabase2 = new Word("belden", PartOfSpeechType.NOUN);

		when(wordDaoMock.findByWordString(anyString())).thenReturn(Arrays.asList(wordFromDatabase1),
				Arrays.asList(wordFromDatabase2), null, null);

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

		rowUpdateCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);
		rowInsertCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(100, wordFromDatabase1.getFrequencyWeight());
		assertEquals(200, wordFromDatabase2.getFrequencyWeight());
		assertEquals(0, rowUpdateCountFromObject.intValue());
		assertEquals(0, rowInsertCountFromObject.intValue());
		verify(wordDaoMock, times(4)).findByWordString(anyString());
	}

	@Test
	public void testBatchWordImportTask() {
		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();

		Word word1 = new Word("george", PartOfSpeechType.NOUN, 100);
		Word word2 = new Word("belden", PartOfSpeechType.NOUN, 200);
		Word word3 = new Word("is", PartOfSpeechType.VERB_PARTICIPLE, 300);
		Word word4 = new Word("awesome", PartOfSpeechType.ADJECTIVE, 400);
		List<Word> threadBatch = new ArrayList<Word>();
		threadBatch.add(word1);
		threadBatch.add(word2);
		threadBatch.add(word3);
		threadBatch.add(word4);

		FrequencyListImporterImpl.BatchWordImportTask batchWordImportTask = frequencyListImporterImpl.new BatchWordImportTask(
				threadBatch);

		Field rowUpdateCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "rowUpdateCount");
		ReflectionUtils.makeAccessible(rowUpdateCountField);
		AtomicInteger rowUpdateCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowUpdateCountFromObject.intValue());

		Field rowInsertCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "rowInsertCount");
		ReflectionUtils.makeAccessible(rowInsertCountField);
		AtomicInteger rowInsertCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowInsertCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		int persistenceBatchSizeToSet = 2;
		int concurrencyBatchSizeToSet = 2;

		frequencyListImporterImpl.setWordDao(wordDaoMock);
		frequencyListImporterImpl.setPersistenceBatchSize(persistenceBatchSizeToSet);
		frequencyListImporterImpl.setConcurrencyBatchSize(concurrencyBatchSizeToSet);

		Word wordFromDatabase1 = new Word("george", PartOfSpeechType.NOUN);
		Word wordFromDatabase2 = new Word("belden", PartOfSpeechType.NOUN);

		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		when(wordDaoMock.updateBatch(anyListOf(Word.class))).thenReturn(true);
		when(wordDaoMock.findByWordString(eq("george"))).thenReturn(Arrays.asList(wordFromDatabase1));
		when(wordDaoMock.findByWordString(eq("belden"))).thenReturn(Arrays.asList(wordFromDatabase2));
		when(wordDaoMock.findByWordString(eq("is"))).thenReturn(null);
		when(wordDaoMock.findByWordString(eq("awesome"))).thenReturn(null);

		try {
			batchWordImportTask.call();
		} catch (Exception e) {
			fail(e.getMessage());
		}

		assertEquals(100, wordFromDatabase1.getFrequencyWeight());
		assertEquals(200, wordFromDatabase2.getFrequencyWeight());

		rowUpdateCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);
		rowInsertCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(2, rowUpdateCountFromObject.intValue());
		assertEquals(2, rowInsertCountFromObject.intValue());
		verify(wordDaoMock, times(1)).insertBatch(anyListOf(Word.class));
		verify(wordDaoMock, times(1)).updateBatch(anyListOf(Word.class));
		verify(wordDaoMock, times(4)).findByWordString(anyString());
	}

	@Test
	public void testBatchWordImportTask_LeftoversFromBatch() {
		FrequencyListImporterImpl frequencyListImporterImpl = new FrequencyListImporterImpl();

		Word word1 = new Word("george", PartOfSpeechType.NOUN, 100);
		Word word2 = new Word("belden", PartOfSpeechType.NOUN, 200);
		Word word3 = new Word("is", PartOfSpeechType.VERB_PARTICIPLE, 300);
		Word word4 = new Word("super", PartOfSpeechType.ADJECTIVE, 400);
		Word word5 = new Word("seriously", PartOfSpeechType.ADJECTIVE, 500);
		Word word6 = new Word("awesome", PartOfSpeechType.ADJECTIVE, 600);
		List<Word> threadBatch = new ArrayList<Word>();
		threadBatch.add(word1);
		threadBatch.add(word2);
		threadBatch.add(word3);
		threadBatch.add(word4);
		threadBatch.add(word5);
		threadBatch.add(word6);

		FrequencyListImporterImpl.BatchWordImportTask batchWordImportTask = frequencyListImporterImpl.new BatchWordImportTask(
				threadBatch);

		Field rowUpdateCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "rowUpdateCount");
		ReflectionUtils.makeAccessible(rowUpdateCountField);
		AtomicInteger rowUpdateCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowUpdateCountFromObject.intValue());

		Field rowInsertCountField = ReflectionUtils.findField(FrequencyListImporterImpl.class, "rowInsertCount");
		ReflectionUtils.makeAccessible(rowInsertCountField);
		AtomicInteger rowInsertCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(0, rowInsertCountFromObject.intValue());

		WordDao wordDaoMock = mock(WordDao.class);
		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		int persistenceBatchSizeToSet = 2;
		int concurrencyBatchSizeToSet = 3;

		frequencyListImporterImpl.setWordDao(wordDaoMock);
		frequencyListImporterImpl.setPersistenceBatchSize(persistenceBatchSizeToSet);
		frequencyListImporterImpl.setConcurrencyBatchSize(concurrencyBatchSizeToSet);

		Word wordFromDatabase1 = new Word("george", PartOfSpeechType.NOUN);
		Word wordFromDatabase2 = new Word("belden", PartOfSpeechType.NOUN);
		Word wordFromDatabase3 = new Word("is", PartOfSpeechType.ADJECTIVE);

		when(wordDaoMock.insertBatch(anyListOf(Word.class))).thenReturn(true);
		when(wordDaoMock.updateBatch(anyListOf(Word.class))).thenReturn(true);
		when(wordDaoMock.findByWordString(eq("george"))).thenReturn(Arrays.asList(wordFromDatabase1));
		when(wordDaoMock.findByWordString(eq("belden"))).thenReturn(Arrays.asList(wordFromDatabase2));
		when(wordDaoMock.findByWordString(eq("is"))).thenReturn(

		Arrays.asList(wordFromDatabase3));
		when(wordDaoMock.findByWordString(eq("super"))).thenReturn(null);
		when(wordDaoMock.findByWordString(eq("seriously"))).thenReturn(null);
		when(wordDaoMock.findByWordString(eq("awesome"))).thenReturn(null);

		try {
			batchWordImportTask.call();
		} catch (Exception e) {
			fail(e.getMessage());
		}

		assertEquals(100, wordFromDatabase1.getFrequencyWeight());
		assertEquals(200, wordFromDatabase2.getFrequencyWeight());
		assertEquals(300, wordFromDatabase3.getFrequencyWeight());

		rowUpdateCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowUpdateCountField,
				frequencyListImporterImpl);
		rowInsertCountFromObject = (AtomicInteger) ReflectionUtils.getField(rowInsertCountField,
				frequencyListImporterImpl);

		assertEquals(3, rowUpdateCountFromObject.intValue());
		assertEquals(3, rowInsertCountFromObject.intValue());
		verify(wordDaoMock, times(2)).insertBatch(anyListOf(Word.class));
		verify(wordDaoMock, times(2)).updateBatch(anyListOf(Word.class));
		verify(wordDaoMock, times(6)).findByWordString(anyString());
	}
}
