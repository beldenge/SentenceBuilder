/**
 * Copyright 2013 George Belden
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

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WordImportManager {
	private static Logger log = Logger.getLogger(WordListImporterImpl.class);
	private static BeanFactory factory;

	private static void setUp() {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans-sentence.xml");
		factory = context;
		log.info("Spring context created successfully!");
	}

	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
		setUp();

		WordListImporterImpl wordListImporter = (WordListImporterImpl) factory
				.getBean("wordListImporter");
		wordListImporter.importWordList();

		FrequencyListImporterImpl frequencyListImporter = (FrequencyListImporterImpl) factory
				.getBean("frequencyListImporter");
		frequencyListImporter.importFrequencyList();
	}
}
