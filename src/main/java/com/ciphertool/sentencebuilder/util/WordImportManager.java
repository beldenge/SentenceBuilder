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
