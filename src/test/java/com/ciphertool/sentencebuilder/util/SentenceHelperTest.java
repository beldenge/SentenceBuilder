package com.ciphertool.sentencebuilder.util;


import static org.junit.Assert.assertEquals;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.sentencebuilder.common.PartOfSpeech;
import com.ciphertool.sentencebuilder.dao.WordMapDao;

public class SentenceHelperTest {

	private static Logger log = Logger.getLogger(SentenceHelperTest.class);
	private static ApplicationContext context;
	private static BeanFactory factory;
	
	@BeforeClass
	public static void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("beans-sentence.xml");
		factory = context;
		log.info("Spring context created successfully!");
	}
	
	@Test
	public void testMakeSentence() throws JAXBException {
		SentenceHelper sentenceHelper = (SentenceHelper) factory.getBean("sentenceHelper");
		
		long start = System.currentTimeMillis();
		
		int i;
		for (i = 0; i < 100; i ++)
			log.info(sentenceHelper.generateRandomSentence());
		
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to generate " + i + " sentences.");
	}
	
	@Test
	public void testEnumTypeOf() {
		PartOfSpeech pos = PartOfSpeech.typeOf('h');
		assertEquals(pos,PartOfSpeech.valueOf("NOUN_PHRASE"));
	}
	
	@Test
	public void testWordMapDao () {
		WordMapDao wordMapDao = (WordMapDao) factory.getBean("wordMapDao");
		
		for (PartOfSpeech key : wordMapDao.getWordMap().keySet()) {
			log.info(key + "\t" +wordMapDao.getWordMap().get(key).size());
		}
	}
	
	/**
	 * Without setting these to null, the humongous wordMap will not be garbage collected and subsequent unit tests may encounter an out of memory exception
	 */
	@AfterClass
	public static void cleanUp() {
		context = null;
		factory = null;
	}
}
