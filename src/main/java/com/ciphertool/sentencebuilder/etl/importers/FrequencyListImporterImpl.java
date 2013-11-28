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

package com.ciphertool.sentencebuilder.etl.importers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.sentencebuilder.dao.WordDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.etl.parsers.FileParser;

public class FrequencyListImporterImpl implements FrequencyListImporter {

	private static Logger log = Logger.getLogger(FrequencyListImporterImpl.class);

	private FileParser<Word> frequencyFileParser;
	private WordDao wordDao;
	private int batchSize;
	private Integer rowUpdateCount = 0;
	private Integer rowInsertCount = 0;

	@Override
	public void importFrequencyList() {
		// Reset the counts in case this method is called again
		rowUpdateCount = 0;
		rowInsertCount = 0;

		long start = System.currentTimeMillis();

		try {
			List<Word> wordUpdateBatch = new ArrayList<Word>();
			List<Word> wordInsertBatch = new ArrayList<Word>();

			List<Word> wordsFromFile = frequencyFileParser.parseFile();

			log.info("Starting frequency list import...");

			for (Word word : wordsFromFile) {
				importWord(word, wordInsertBatch, wordUpdateBatch);
			}

			/*
			 * Do updates and inserts if the batch size wasn't reached before
			 * the end of the loop
			 */
			if (wordUpdateBatch.size() > 0) {
				boolean result = wordDao.updateBatch(wordUpdateBatch);

				if (result) {
					this.rowUpdateCount += wordUpdateBatch.size();
				}
			}

			if (wordInsertBatch.size() > 0) {
				boolean result = wordDao.insertBatch(wordInsertBatch);

				if (result) {
					this.rowInsertCount += wordInsertBatch.size();
				}
			}
		} finally {
			log.info("Rows updated: " + this.rowUpdateCount);
			log.info("Rows inserted: " + this.rowInsertCount);
			log.info("Time elapsed: " + (System.currentTimeMillis() - start) + "ms");
		}
	}

	/**
	 * Imports a Word, either adding it or updating it depending whether it
	 * already exists in the datastore or not. It should only call down to the
	 * persistence layer once the batch size is reached.
	 * 
	 * @param word
	 *            the next line to import
	 * @param wordInsertBatch
	 *            the batch of Words to insert, maintained across loop
	 *            iterations
	 * @param wordUpdateBatch
	 *            the batch of Words to update, maintained across loop
	 *            iterations
	 */
	protected void importWord(Word word, List<Word> wordInsertBatch, List<Word> wordUpdateBatch) {
		if (word == null) {
			// Nothing to do
			return;
		}

		/*
		 * Unfortunately the frequency data has mixed case, so this query may
		 * not return the results we would expect. Rather than modify our logic
		 * here to account for this, it is better to fix the data.
		 */
		List<Word> words = wordDao.findByWordString(word.getId().getWord());

		if (words == null || words.size() == 0) {
			log.debug("No frequency matches found in part_of_speech table for word: " + word
					+ ".  Inserting with a filler value for the part_of_speech column.");

			wordInsertBatch.add(word);

			if (wordInsertBatch.size() >= batchSize) {
				boolean result = wordDao.insertBatch(wordInsertBatch);

				if (result) {
					this.rowInsertCount += wordInsertBatch.size();
				}

				wordInsertBatch.clear();
			}
		} else {
			/*
			 * Loop over the list and update each word with the frequency
			 */
			for (Word w : words) {
				/*
				 * Don't update if the frequency weight from file is the same as
				 * what's already in the database. It's pointless.
				 */
				if (w.getFrequencyWeight() != word.getFrequencyWeight()) {
					w.setFrequencyWeight(word.getFrequencyWeight());

					wordUpdateBatch.add(w);
				}
			}

			/*
			 * Since the above loop adds a word several times depending on how
			 * many parts of speech it is related to, the batch size may be
			 * exceeded by a handful, and this is fine.
			 */
			if (wordUpdateBatch.size() >= batchSize) {
				boolean result = wordDao.updateBatch(wordUpdateBatch);

				if (result) {
					this.rowUpdateCount += wordUpdateBatch.size();
				}

				wordUpdateBatch.clear();
			}
		}
	}

	/**
	 * @param wordDao
	 *            the wordDao to set
	 */
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

	/**
	 * @param fileParser
	 *            the fileParser to set
	 */
	@Required
	public void setFileParser(FileParser<Word> fileParser) {
		this.frequencyFileParser = fileParser;
	}
}
