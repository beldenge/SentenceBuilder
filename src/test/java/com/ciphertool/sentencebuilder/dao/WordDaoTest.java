package com.ciphertool.sentencebuilder.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.sentencebuilder.entities.Word;

public class WordDaoTest {
	private static Logger log = Logger.getLogger(WordDaoTest.class);
	private static BeanFactory factory;
	
	@BeforeClass
	public static void setUp() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans-test.xml");
		factory = context;
		log.info("Spring context created successfully!");
	}
	
	@Test
	public void testFindBywordString() {
		WordDao wordDao = (WordDao) factory.getBean("wordDao");
		String word = "zoom in";
		List<Word> words = wordDao.findByWordString(word);
		assertFalse(words.isEmpty());
		word = "zlxcvjpaowei2";
		words = wordDao.findByWordString(word);
		assertTrue(words.isEmpty());
	}
	
	@Test
	public void testUpdateWord() {
		WordDao wordDao = (WordDao) factory.getBean("wordDao");
		String word = "apple";
		List<Word> words = wordDao.findByWordString(word);
		assertFalse(words.isEmpty());
		log.info("Number of matches on " + word + ": " + words.size());
		
		long tempFrequency;
		
		for (Word w : words) {
			tempFrequency = w.getFrequencyWeight();
			
			//Update the Word
			w.setFrequencyWeight(50);
			wordDao.update(w);
			
			//Verify the update and then revert back
			assertEquals(w.getFrequencyWeight(), 50);
			
			//Revert the update
			w.setFrequencyWeight(tempFrequency);
			wordDao.update(w);
			
			//Verify once more
			assertEquals(w.getFrequencyWeight(), tempFrequency);
		}
	}
}
