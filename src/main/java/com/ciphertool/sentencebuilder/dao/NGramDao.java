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

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ciphertool.sentencebuilder.entities.NGram;

public class NGramDao {
	private Logger log = Logger.getLogger(getClass());

	private SessionFactory sessionFactory;
	private static final String SEPARATOR = ":";
	private static final String N_GRAM_PARAM = "nGram";
	private static final String NUM_WORDS_PARAM = "numWords";

	/**
	 * Returns a list of all NGrams.
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<NGram> findAll() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<NGram> result = (List<NGram>) session.createQuery("from NGram").list();

		return result;
	}

	/**
	 * Returns a list of all NGrams.
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<NGram> findAllByNumWords(int numWords) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<NGram> result = (List<NGram>) session.createQuery(
				"from NGram where numWords = " + SEPARATOR + NUM_WORDS_PARAM).setInteger(NUM_WORDS_PARAM, numWords)
				.list();

		return result;
	}

	/**
	 * Returns a list of top N NGrams.
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<NGram> findTopMostFrequent(int top) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<NGram> result = (List<NGram>) session.createQuery("from NGram order by frequencyWeight desc")
				.setMaxResults(top).list();

		return result;
	}

	/**
	 * Returns a list of top N NGrams.
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<NGram> findTopMostFrequentByNumWords(int numWords, int top) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<NGram> result = (List<NGram>) session.createQuery(
				"from NGram where numWords = " + SEPARATOR + NUM_WORDS_PARAM + " order by frequencyWeight desc")
				.setInteger(NUM_WORDS_PARAM, numWords).setMaxResults(top).list();

		return result;
	}

	/**
	 * Finds all occurrences of a particular NGram by its String value.
	 * 
	 * @param nGram
	 *            the String value of the nGram to find
	 * @return the List of matching NGrams
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<NGram> findByNGramString(String nGram) {
		if (nGram == null) {
			log.warn("Attempted to find NGram by null String.  Unable to continue, thus returning null.");

			return null;
		}

		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<NGram> nGrams = (List<NGram>) session.createQuery("from NGram where nGram = " + SEPARATOR + N_GRAM_PARAM)
				.setParameter(N_GRAM_PARAM, nGram).list();

		return nGrams;
	}

	/**
	 * Inserts a nGram.
	 * 
	 * @param nGram
	 *            the NGram to insert
	 * @return whether the insert was successful
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean insert(NGram nGram) {
		if (nGram == null) {
			log.warn("Attempted to insert null NGram.  Unable to continue, thus returning false.");

			return false;
		}

		Session session = sessionFactory.getCurrentSession();
		session.save(nGram);
		return true;
	}

	/**
	 * Inserts a List of NGrams in batch.
	 * 
	 * @param nGramBatch
	 *            the batch of NGrams to insert
	 * @return whether the insert was successful
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean insertBatch(List<NGram> nGramBatch) {
		if (nGramBatch == null || nGramBatch.isEmpty()) {
			log.warn("Attempted to insert NGrams in batch which was found to be null or empty.  Unable to continue, thus returning false.");

			return false;
		}

		Session session = sessionFactory.getCurrentSession();
		for (NGram nGram : nGramBatch) {
			session.save(nGram);
		}
		return true;
	}

	/**
	 * Updates a NGram.
	 * 
	 * @param nGram
	 *            the NGram to update
	 * @return whether the update was successful
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean update(NGram nGram) {
		if (nGram == null) {
			log.warn("Attempted to update null NGram.  Unable to continue, thus returning false.");

			return false;
		}

		Session session = sessionFactory.getCurrentSession();
		session.update(nGram);

		return true;
	}

	/**
	 * Updates a List of NGrams in batch.
	 * 
	 * @param nGramBatch
	 *            the batch of NGrams
	 * @return whether the update was successful
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean updateBatch(List<NGram> nGramBatch) {
		if (nGramBatch == null || nGramBatch.isEmpty()) {
			log.warn("Attempted to update NGrams in batch which was found to be null or empty.  Unable to continue, thus returning false.");

			return false;
		}

		Session session = sessionFactory.getCurrentSession();
		for (NGram nGram : nGramBatch) {
			session.update(nGram);
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
