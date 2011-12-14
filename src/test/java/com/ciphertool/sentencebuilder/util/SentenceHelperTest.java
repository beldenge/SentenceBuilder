package com.ciphertool.sentencebuilder.util;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.sentencebuilder.common.PartOfSpeech;
import com.ciphertool.sentencebuilder.dao.WordDao;
import com.ciphertool.sentencebuilder.dao.WordMapDao;
import com.ciphertool.sentencebuilder.entities.Word;

public class SentenceHelperTest {

	private static Logger log = Logger.getLogger(SentenceHelperTest.class);
	private static BeanFactory factory;
	
	@BeforeClass
	public static void setUp() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans-test.xml");
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
		//this test will obviously change if a different word list is used or if the storage method is changed
		WordDao wordDao = (WordDao) factory.getBean("wordDao");
		ArrayList<Word> wordList = (ArrayList<Word>) wordDao.findAll();
		assertEquals(wordList.size(), 322471);
		
		WordMapDao wordMapDao = (WordMapDao) factory.getBean("wordMapDao");
		HashMap<PartOfSpeech, ArrayList<Word>> wordMap = wordMapDao.mapByPartOfSpeech(wordList);
		for (PartOfSpeech key : wordMap.keySet()) {
			log.info(key + "\t" + wordMap.get(key).size());
		}
	}
}
