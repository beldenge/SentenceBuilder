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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.ciphertool.sentencebuilder.common.PartOfSpeechType;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;

public class WordDaoTest {
	private static WordDao wordDao;
	private static Session sessionMock;
	private static Query queryMock;
	private static SessionFactory sessionFactoryMock;
	private static Logger logMock;

	@BeforeClass
	public static void setUp() {
		queryMock = mock(Query.class);
		sessionMock = mock(Session.class);
		logMock = mock(Logger.class);
		sessionFactoryMock = mock(SessionFactory.class);

		wordDao = new WordDao();
		wordDao.setSessionFactory(sessionFactoryMock);

		Field logField = ReflectionUtils.findField(WordDao.class, "log");
		ReflectionUtils.makeAccessible(logField);
		ReflectionUtils.setField(logField, wordDao, logMock);
	}

	@Before
	public void resetMocks() {
		reset(queryMock);
		reset(sessionMock);
		reset(sessionFactoryMock);
		reset(logMock);

		when(sessionMock.createQuery(anyString())).thenReturn(queryMock);
		when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);
	}

	@Test
	public void testSetSessionFactory() {
		SessionFactory sessionFactoryToSet = mock(SessionFactory.class);
		WordDao wordDao = new WordDao();
		wordDao.setSessionFactory(sessionFactoryToSet);

		Field sessionFactoryField = ReflectionUtils.findField(WordDao.class, "sessionFactory");
		ReflectionUtils.makeAccessible(sessionFactoryField);
		SessionFactory sessionFactoryFromObject = (SessionFactory) ReflectionUtils.getField(
				sessionFactoryField, wordDao);

		assertSame(sessionFactoryToSet, sessionFactoryFromObject);
	}

	@Test
	public void testFindAll() {
		List<Word> wordList = new ArrayList<Word>();
		Word word1 = new Word(new WordId("programming", PartOfSpeechType.NOUN));
		wordList.add(word1);
		Word word2 = new Word(new WordId("is", PartOfSpeechType.VERB_PARTICIPLE));
		wordList.add(word2);
		Word word3 = new Word(new WordId("awesome", PartOfSpeechType.ADJECTIVE));
		wordList.add(word3);

		when(queryMock.list()).thenReturn(wordList);

		List<Word> wordsReturned = wordDao.findAll();

		assertSame(wordList, wordsReturned);
		verify(sessionFactoryMock, times(1)).getCurrentSession();
		verify(sessionMock, times(1)).createQuery(anyString());
		verify(queryMock, times(1)).list();
	}

	@Test
	public void testFindAllUniqueWords() {
		List<Word> wordList = new ArrayList<Word>();
		Word word1 = new Word(new WordId("programming", PartOfSpeechType.NOUN));
		wordList.add(word1);
		Word word2 = new Word(new WordId("is", PartOfSpeechType.VERB_PARTICIPLE));
		wordList.add(word2);
		Word word3 = new Word(new WordId("awesome", PartOfSpeechType.ADJECTIVE));
		wordList.add(word3);

		when(queryMock.list()).thenReturn(wordList);

		List<Word> wordsReturned = wordDao.findAllUniqueWords();

		assertSame(wordList, wordsReturned);
		verify(sessionFactoryMock, times(1)).getCurrentSession();
		verify(sessionMock, times(1)).createQuery(anyString());
		verify(queryMock, times(1)).list();
	}

	@Test
	public void testFindByWordString() {
		String wordString = "stuff";

		List<Word> wordList = new ArrayList<Word>();
		Word word1 = new Word(new WordId(wordString, PartOfSpeechType.NOUN));
		wordList.add(word1);
		Word word2 = new Word(new WordId(wordString, PartOfSpeechType.VERB_PARTICIPLE));
		wordList.add(word2);
		Word word3 = new Word(new WordId(wordString, PartOfSpeechType.ADJECTIVE));
		wordList.add(word3);

		when(queryMock.list()).thenReturn(wordList);
		when(queryMock.setParameter(anyString(), same(wordString))).thenReturn(queryMock);

		List<Word> wordsReturned = wordDao.findByWordString(wordString);

		assertSame(wordList, wordsReturned);
		verify(sessionFactoryMock, times(1)).getCurrentSession();
		verify(sessionMock, times(1)).createQuery(anyString());
		verify(queryMock, times(1)).setParameter(anyString(), same(wordString));
		verify(queryMock, times(1)).list();
		verify(logMock, never()).warn(anyString());
	}

	@Test
	public void testFindByWordStringNullValue() {
		List<Word> wordsReturned = wordDao.findByWordString(null);

		assertNull(wordsReturned);
		verifyZeroInteractions(sessionFactoryMock);
		verifyZeroInteractions(sessionMock);
		verifyZeroInteractions(queryMock);
		verify(logMock, times(1)).warn(anyString());
	}

	@Test
	public void testInsert() {
		Word wordToInsert = new Word(new WordId("stuff", PartOfSpeechType.NOUN));

		boolean result = wordDao.insert(wordToInsert);

		assertTrue(result);
		verify(sessionFactoryMock, times(1)).getCurrentSession();
		verify(sessionMock, times(1)).save(same(wordToInsert));
		verify(logMock, never()).warn(anyString());
	}

	@Test
	public void testInsertNull() {
		boolean result = wordDao.insert(null);

		assertFalse(result);
		verifyZeroInteractions(sessionFactoryMock);
		verifyZeroInteractions(sessionMock);
		verifyZeroInteractions(queryMock);
		verify(logMock, times(1)).warn(anyString());
	}

	@Test
	public void testInsertBatch() {
		List<Word> wordList = new ArrayList<Word>();
		Word word1 = new Word(new WordId("programming", PartOfSpeechType.NOUN));
		wordList.add(word1);
		Word word2 = new Word(new WordId("is", PartOfSpeechType.VERB_PARTICIPLE));
		wordList.add(word2);
		Word word3 = new Word(new WordId("awesome", PartOfSpeechType.ADJECTIVE));
		wordList.add(word3);

		boolean result = wordDao.insertBatch(wordList);

		assertTrue(result);
		verify(sessionFactoryMock, times(1)).getCurrentSession();
		verify(sessionMock, times(1)).save(same(word1));
		verify(sessionMock, times(1)).save(same(word2));
		verify(sessionMock, times(1)).save(same(word3));
		verify(logMock, never()).warn(anyString());
	}

	@Test
	public void testInsertBatchNull() {
		boolean result = wordDao.insertBatch(null);

		assertFalse(result);
		verifyZeroInteractions(sessionFactoryMock);
		verifyZeroInteractions(sessionMock);
		verifyZeroInteractions(queryMock);
		verify(logMock, times(1)).warn(anyString());
	}

	@Test
	public void testUpdate() {
		Word wordToInsert = new Word(new WordId("stuff", PartOfSpeechType.NOUN));

		boolean result = wordDao.update(wordToInsert);

		assertTrue(result);
		verify(sessionFactoryMock, times(1)).getCurrentSession();
		verify(sessionMock, times(1)).update(same(wordToInsert));
		verify(logMock, never()).warn(anyString());
	}

	@Test
	public void testUpdateNull() {
		boolean result = wordDao.update(null);

		assertFalse(result);
		verifyZeroInteractions(sessionFactoryMock);
		verifyZeroInteractions(sessionMock);
		verifyZeroInteractions(queryMock);
		verify(logMock, times(1)).warn(anyString());
	}

	@Test
	public void testUpdateBatch() {
		List<Word> wordList = new ArrayList<Word>();
		Word word1 = new Word(new WordId("programming", PartOfSpeechType.NOUN));
		wordList.add(word1);
		Word word2 = new Word(new WordId("is", PartOfSpeechType.VERB_PARTICIPLE));
		wordList.add(word2);
		Word word3 = new Word(new WordId("awesome", PartOfSpeechType.ADJECTIVE));
		wordList.add(word3);

		boolean result = wordDao.updateBatch(wordList);

		assertTrue(result);
		verify(sessionFactoryMock, times(1)).getCurrentSession();
		verify(sessionMock, times(1)).update(same(word1));
		verify(sessionMock, times(1)).update(same(word2));
		verify(sessionMock, times(1)).update(same(word3));
		verify(logMock, never()).warn(anyString());
	}

	@Test
	public void testUpdateBatchNull() {
		boolean result = wordDao.updateBatch(null);

		assertFalse(result);
		verifyZeroInteractions(sessionFactoryMock);
		verifyZeroInteractions(sessionMock);
		verifyZeroInteractions(queryMock);
		verify(logMock, times(1)).warn(anyString());
	}
}
