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

public class FrequencyListImporterImpl implements FrequencyListImporter {
	private String fileName;
	private WordDao wordDao;
	private int batchSize;

	private static Logger log = Logger.getLogger(FrequencyListImporterImpl.class);

	/**
	 * @param args
	 */
	public void importFrequencyList() {
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
		int frequency = 1;
		int rowCount = 0;
		long start = System.currentTimeMillis();

		try {
			log.info("Starting frequency list import...");

			String nextWord = input.readLine();

			/*
			 * Skip to the second record because the first record contains
			 * columns names
			 */
			nextWord = input.readLine();

			List<Word> words;
			List<Word> wordBatch = new ArrayList<Word>();

			while (nextWord != null) {
				line = nextWord.split("\t");
				word = line[0];
				frequency = Integer.parseInt(line[1]);

				/*
				 * Unfortunately the frequency data has mixed case, so this
				 * query may not return the results we would expect. Rather than
				 * modify our logic here to account for this, it is better to
				 * fix the data.
				 */
				words = wordDao.findByWordString(word);

				// If word is not found, log at info level
				if (words == null || words.size() == 0) {
					log.debug("No frequency matches found in part_of_speech table for word: "
							+ word);
				} else {
					/*
					 * Loop over the list and update each word with the
					 * frequency
					 */
					for (Word w : words) {
						/*
						 * Don't update if the frequency weight from file is the
						 * same as what's already in the database. It's
						 * pointless.
						 */
						if (w.getFrequencyWeight() != frequency) {
							w.setFrequencyWeight(frequency);

							wordBatch.add(w);

							rowCount++;
						}
					}
					/*
					 * Since the above loop adds a word several times depending
					 * on how many parts of speech it is related to, the batch
					 * size may be exceeded by a handful, and this is fine.
					 */
					if (wordBatch.size() >= batchSize) {
						wordDao.updateBatch(wordBatch);

						wordBatch.clear();
					}
				}

				nextWord = input.readLine();
			}
		} catch (IOException ioe) {
			log.error("Caught IOException while reading next line from frequency list.", ioe);
		} finally {
			log.info("Rows updated: " + rowCount);
			log.info("Time elapsed: " + (System.currentTimeMillis() - start) + "ms");

			try {
				input.close();
			} catch (IOException e) {
				log.error("Unable to close BufferedReader while finishing frequency list import.",
						e);
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
