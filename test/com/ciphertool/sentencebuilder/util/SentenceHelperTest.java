package com.ciphertool.sentencebuilder.util;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.JAXBException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.sentencebuilder.beans.Word;
import com.ciphertool.sentencebuilder.common.PartOfSpeech;
import com.ciphertool.sentencebuilder.dao.WordListDAO;

public class SentenceHelperTest {

	private static BeanFactory factory;
	
	@BeforeClass
	public static void setUp() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans-test.xml");
		factory = context;
		System.out.println("Spring context created successfully!");
	}
	
	@Test
	public void testMakeSentence() throws JAXBException {
		SentenceHelper sentenceHelper = (SentenceHelper) factory.getBean("sentenceHelper");
		System.out.println(sentenceHelper.generateRandomSentence());
	}
	
	@Test
	public void testEnumTypeOf() {
		PartOfSpeech pos = PartOfSpeech.typeOf('h');
		assertEquals(pos,PartOfSpeech.valueOf("NOUN_PHRASE"));
	}
	
	//@Test
	public void testWordListDAO () {
		//this test will obviously change if a different word list is used or if the storage method is changed
		WordListDAO wordListDAO = new WordListDAO();
		ArrayList<Word> wordList = (ArrayList<Word>) wordListDAO.findAll();
		assertEquals(wordList.size(), 322471);
		HashMap<PartOfSpeech, ArrayList<Word>> wordMap = wordListDAO.sortByPartOfSpeech(wordList);
		for (PartOfSpeech key : wordMap.keySet()) {
			System.out.println(key + "\t" + wordMap.get(key).size());
		}
	}	
}
