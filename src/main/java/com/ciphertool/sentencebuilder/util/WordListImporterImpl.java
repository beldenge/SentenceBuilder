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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.sentencebuilder.dao.WordDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;

public class WordListImporterImpl implements WordListImporter {
	private String fileName;
	private WordDao wordDao;
	private int batchSize;

	private static Logger log = Logger.getLogger(WordListImporterImpl.class);

	/**
	 * @param args
	 */
	public void importWordList() {
		BufferedReader input = null;
		try {
			input = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			log.error("File: " + fileName + " not found.");
			e.printStackTrace();

			return;
		}
		String[] line = null;
		String word = null;
		char[] partOfSpeech = null;
		String nextWord = null;
		int rowCount = 0;
		long start = System.currentTimeMillis();

		try {
			log.info("Starting word list import...");
			nextWord = input.readLine();

			List<Word> wordBatch = new ArrayList<Word>();

			while (nextWord != null) {
				/*
				 * The fields are tab-delimited in the file.
				 */
				line = nextWord.split("\t");

				word = line[0];

				partOfSpeech = line[1].toCharArray();

				for (int i = 0; i < partOfSpeech.length; i++) {
					/*
					 * The pipe character is just informational in the word
					 * list, so we don't add it as a part of speech.
					 */
					if (partOfSpeech[i] != '|') {
						wordBatch.add(new Word(new WordId(word, partOfSpeech[i]), 1));

						rowCount++;
					}
				}
				/*
				 * Since the above loop adds a word several times depending on
				 * how many parts of speech it is related to, the batch size may
				 * be exceeded by a handful, and this is fine.
				 */
				if (wordBatch.size() >= batchSize) {
					wordDao.insertBatch(wordBatch);

					wordBatch.clear();
				}

				nextWord = input.readLine();
			}
		} catch (IOException ioe) {
			log.error("Caught IOException while reading next line from word list.", ioe);
		} finally {
			log.info("Rows inserted: " + rowCount);
			log.info("Time elapsed: " + (System.currentTimeMillis() - start) + "ms");

			try {
				input.close();
			} catch (IOException e) {
				log.error("Unable to close BufferedReader while finishing word list import.", e);
			}
		}
	}

	@Required
	public void setFileName(String file) {
		fileName = file;
	}

	@Autowired
	public void setWordDao(WordDao wordDao) {
		this.wordDao = wordDao;
	}

	/**
	 * @param batchSize
	 *            the batchSize to set
	 */
	@Required
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
}