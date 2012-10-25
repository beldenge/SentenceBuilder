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
		for (i = 0; i < 1000; i++)
			log.info(sentenceHelper.generateRandomSentence());

		log.info("Took " + (System.currentTimeMillis() - start) + "ms to generate " + i
				+ " sentences.");
	}

	@Test
	public void testEnumTypeOf() {
		PartOfSpeech pos = PartOfSpeech.typeOf('h');
		assertEquals(pos, PartOfSpeech.valueOf("NOUN_PHRASE"));
	}

	@Test
	public void testWordMapDao() {
		/*
		 * We have to find the beanName from the interface type since the
		 * implementation is autowired
		 */
		String[] beanNames = context
				.getBeanNamesForType(com.ciphertool.sentencebuilder.dao.WordMapDao.class);

		WordMapDao wordMapDao = (WordMapDao) factory.getBean(beanNames[0]);

		for (PartOfSpeech key : wordMapDao.getWordMap().keySet()) {
			log.info(key + "\t" + wordMapDao.getWordMap().get(key).size());
		}
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
