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

package com.ciphertool.sentencebuilder.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.sentencebuilder.etl.importers.FrequencyListImporter;
import com.ciphertool.sentencebuilder.etl.importers.FrequencyListImporterImpl;
import com.ciphertool.sentencebuilder.etl.importers.NGramListImporter;
import com.ciphertool.sentencebuilder.etl.importers.WordListImporter;
import com.ciphertool.sentencebuilder.etl.importers.WordListImporterImpl;

public class WordImportManager {
	private static Logger log = LoggerFactory.getLogger(WordListImporterImpl.class);

	private static BeanFactory beanFactory;
	private static FrequencyListImporter frequencyListImporter;
	private static WordListImporter wordListImporter;
	private static NGramListImporter nGramListImporter;

	/**
	 * Bootstraps the Spring application context.
	 */
	private static void setUp() {
		beanFactory = new ClassPathXmlApplicationContext("importManagerContext.xml");

		log.info("Spring application context created successfully!");

		wordListImporter = (WordListImporterImpl) beanFactory.getBean("wordListImporter");

		frequencyListImporter = (FrequencyListImporterImpl) beanFactory.getBean("frequencyListImporter");

		nGramListImporter = (NGramListImporter) beanFactory.getBean("nGramListImporter");
	}

	/**
	 * Main entry point for the word import tools.
	 * 
	 * @param args
	 *            the optional, unused command-line arguments
	 */
	public static void main(String[] args) {
		setUp();

		wordListImporter.importWordList();

		frequencyListImporter.importFrequencyList();

		nGramListImporter.importNGramList();
	}
}
