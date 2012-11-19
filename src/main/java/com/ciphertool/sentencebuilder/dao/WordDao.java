/**
 * Copyright 2012 George Belden
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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ciphertool.sentencebuilder.entities.Word;

@Component
public class WordDao {
	private SessionFactory sessionFactory;
	private static final String separator = ":";
	private static final String wordParameter = "word";

	/*
	 * This returns a list of all Words, so words will be duplicated if they
	 * have multiple parts of speech.
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<Word> findAll() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Word> result = (List<Word>) session.createQuery("from Word").list();

		return result;
	}

	/*
	 * This returns a list of all unique Words, irrespective of parts of speech.
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<Word> findAllUniqueWords() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Word> result = (List<Word>) session.createQuery(
				"select distinct new Word(id.word, frequencyWeight) from Word").list();

		return result;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<Word> findByWordString(String word) {

		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Word> words = (List<Word>) session.createQuery(
				"from Word where word = " + separator + wordParameter).setParameter(wordParameter,
				word).list();

		return words;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean insert(Word w) {
		Session session = sessionFactory.getCurrentSession();
		session.save(w);
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean insertBatch(List<Word> wordBatch) {
		Session session = sessionFactory.getCurrentSession();
		for (Word word : wordBatch) {
			session.save(word);
		}
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean update(Word w) {
		Session session = sessionFactory.getCurrentSession();
		session.update(w);

		return true;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean updateBatch(List<Word> wordBatch) {
		Session session = sessionFactory.getCurrentSession();
		for (Word word : wordBatch) {
			session.update(word);
		}
		return true;
	}

	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
