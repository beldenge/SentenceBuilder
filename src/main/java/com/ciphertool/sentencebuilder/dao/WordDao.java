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

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ciphertool.sentencebuilder.entities.Word;

@Component
public class WordDao {
	private Logger log = Logger.getLogger(getClass());

	private SessionFactory sessionFactory;
	private static final String separator = ":";
	private static final String wordParameter = "word";

	/**
	 * Returns a list of all Words, so words will be duplicated if they have
	 * multiple parts of speech.
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<Word> findAll() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Word> result = (List<Word>) session.createQuery("from Word").list();

		return result;
	}

	/**
	 * Returns a list of all unique Words, irrespective of parts of speech.
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<Word> findAllUniqueWords() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Word> result = (List<Word>) session.createQuery(
				"select distinct new Word(id.word, frequencyWeight) from Word").list();

		return result;
	}

	/**
	 * Finds all occurrences of a particular Word by its String value.
	 * 
	 * @param word
	 *            the String value of the word to find
	 * @return the List of matching Words
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<Word> findByWordString(String word) {
		if (word == null) {
			log.warn("Attempted to find Word by null String.  Unable to continue, thus returning null.");

			return null;
		}

		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Word> words = (List<Word>) session.createQuery(
				"from Word where word = " + separator + wordParameter).setParameter(wordParameter,
				word).list();

		return words;
	}

	/**
	 * Inserts a word.
	 * 
	 * @param word
	 *            the Word to insert
	 * @return whether the insert was successful
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean insert(Word word) {
		if (word == null) {
			log.warn("Attempted to insert null Word.  Unable to continue, thus returning false.");

			return false;
		}

		Session session = sessionFactory.getCurrentSession();
		session.save(word);
		return true;
	}

	/**
	 * Inserts a List of Words in batch.
	 * 
	 * @param wordBatch
	 *            the batch of Words to insert
	 * @return whether the insert was successful
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean insertBatch(List<Word> wordBatch) {
		if (wordBatch == null || wordBatch.isEmpty()) {
			log.warn("Attempted to insert Words in batch which was found to be null or empty.  Unable to continue, thus returning false.");

			return false;
		}

		Session session = sessionFactory.getCurrentSession();
		for (Word word : wordBatch) {
			session.save(word);
		}
		return true;
	}

	/**
	 * Updates a Word.
	 * 
	 * @param word
	 *            the Word to update
	 * @return whether the update was successful
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean update(Word word) {
		if (word == null) {
			log.warn("Attempted to update null Word.  Unable to continue, thus returning false.");

			return false;
		}

		Session session = sessionFactory.getCurrentSession();
		session.update(word);

		return true;
	}

	/**
	 * Updates a List of Words in batch.
	 * 
	 * @param wordBatch
	 *            the batch of Words
	 * @return whether the update was successful
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean updateBatch(List<Word> wordBatch) {
		if (wordBatch == null || wordBatch.isEmpty()) {
			log.warn("Attempted to update Words in batch which was found to be null or empty.  Unable to continue, thus returning false.");

			return false;
		}

		Session session = sessionFactory.getCurrentSession();
		for (Word word : wordBatch) {
			session.update(word);
		}
		return true;
	}

	/**
	 * @param sessionFactory
	 *            the SessionFactory to set
	 */
	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
