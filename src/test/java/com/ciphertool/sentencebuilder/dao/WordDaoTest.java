package com.ciphertool.sentencebuilder.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.sentencebuilder.entities.Word;

public class WordDaoTest {
	private static Logger log = Logger.getLogger(WordDaoTest.class);
	private static ApplicationContext context;
	private static BeanFactory factory;
	private static WordDao wordDao;

	@BeforeClass
	public static void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("beans-sentence.xml");
		factory = context;
		log.info("Spring context created successfully!");

		wordDao = (WordDao) factory.getBean("wordDao");
	}

	@Test
	public void testFindByWordString() {
		String word = "zoom in";
		List<Word> words = wordDao.findByWordString(word);
		assertFalse(words.isEmpty());
		word = "zlxcvjpaowei2";
		words = wordDao.findByWordString(word);
		assertTrue(words.isEmpty());
	}

	@Test
	public void testUpdateWord() {
		String word = "apple";
		List<Word> words = wordDao.findByWordString(word);
		assertFalse(words.isEmpty());
		log.info("Number of matches on " + word + ": " + words.size());

		int tempFrequency;

		for (Word w : words) {
			tempFrequency = w.getFrequencyWeight();

			// Update the Word
			w.setFrequencyWeight(50);
			wordDao.update(w);

			// Verify the update and then revert back
			assertEquals(w.getFrequencyWeight(), 50);

			// Revert the update
			w.setFrequencyWeight(tempFrequency);
			wordDao.update(w);

			// Verify once more
			assertEquals(w.getFrequencyWeight(), tempFrequency);
		}
	}

	@Test
	public void testFindAllUniqueWords() {
		List<Word> words = wordDao.findAllUniqueWords();

		assertFalse(words.isEmpty());

		log.info("Unique words found: " + words.size());
	}

	@Test
	public void testRegex() {
		String goodWord = "teeth";
		String badWord1 = "teeeo";
		String badWord2 = "tteto";
		String badWord3 = "tetto";
		String badWord4 = "teete";
		/*
		 * We want the second letter to be different from the first letter, the
		 * third letter to match the second letter, the fourth letter to match
		 * the first letter, and the last letter to not match any other letters.
		 */
		String regex = "([a-z])(?!\\1)([a-z])\\2\\1(?!(\\1|\\2))[a-z]";
		log.info("Regex: " + regex);

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(goodWord);
		assertTrue(m.matches());

		assertFalse(m.reset(badWord1).matches());
		assertFalse(m.reset(badWord2).matches());
		assertFalse(m.reset(badWord3).matches());
		assertFalse(m.reset(badWord4).matches());
	}

	/**
	 * Without setting these to null, the humongous wordMap will not be garbage
	 * collected and subsequent unit tests may encounter an out of memory
	 * exception
	 */
	@AfterClass
	public static void cleanUp() {
		context = null;
		factory = null;
	}
}
