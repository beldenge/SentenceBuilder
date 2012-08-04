package com.ciphertool.sentencebuilder.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

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
	public List<Word> findAll() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Word> result = (List<Word>) session.createQuery("from Word").list();
		session.getTransaction().commit();
		session.close();

		return result;
	}

	/*
	 * This returns a list of all unique Words, irrespective of parts of speech.
	 */
	public List<Word> findAllUniqueWords() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Word> result = (List<Word>) session.createQuery(
				"select distinct new Word(id.word, frequencyWeight) from Word").list();
		session.getTransaction().commit();
		session.close();

		return result;
	}

	public List<Word> findByWordString(String word) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Word> words = (List<Word>) session.createQuery(
				"from Word where word = " + separator + wordParameter).setParameter(wordParameter,
				word).list();
		session.getTransaction().commit();
		session.close();

		return words;
	}

	public boolean insert(Word w) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(w);
		session.getTransaction().commit();
		session.close();
		return true;
	}

	public boolean insertBatch(List<Word> wordBatch) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		for (Word word : wordBatch) {
			session.save(word);
		}
		transaction.commit();
		session.close();
		return true;
	}

	public boolean update(Word w) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(w);
		session.getTransaction().commit();
		session.close();
		return true;
	}

	public boolean updateBatch(List<Word> wordBatch) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		for (Word word : wordBatch) {
			session.update(word);
		}
		transaction.commit();
		session.close();
		return true;
	}

	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
