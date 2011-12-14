package com.ciphertool.sentencebuilder.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import com.ciphertool.sentencebuilder.entities.Word;

@Component
public class WordDao {
	private SessionFactory sessionFactory;

	public List<Word> findAll() {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Word> result = (List<Word>) session.createQuery( "from Word" ).list();
	    session.getTransaction().commit();
	    session.close();
	    
		return result;
	}
	
	public List<Word> findByWordString(String word) {
		
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Word> words = (List<Word>) session.createQuery( "from Word where word = ?" )
					.setString(0, word)
					.list();
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
		//remember to add the frequency weight somehow; for now just let it default to 1
	}
	
	public boolean update(Word w) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(w);
		session.getTransaction().commit();
		session.close();
		return true;
		//remember to add the frequency weight somehow; for now just let it default to 1
	}

	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
