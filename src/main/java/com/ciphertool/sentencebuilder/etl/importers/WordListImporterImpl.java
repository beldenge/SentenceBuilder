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

public class WordListImporterImpl implements WordListImporter {

	private static Logger log = Logger.getLogger(WordListImporterImpl.class);

	private FileParser<Word> partOfSpeechFileParser;
	private WordDao wordDao;
	private int batchSize;
	private Integer rowCount = 0;

	@Override
	public void importWordList() {
		// Reset the count in case this method is called again
		rowCount = 0;

		long start = System.currentTimeMillis();

		try {
			List<Word> wordBatch = new ArrayList<Word>();

			List<Word> wordsFromFile = partOfSpeechFileParser.parseFile();

			log.info("Starting word list import...");

			for (Word word : wordsFromFile) {
				importWord(word, wordBatch);
			}

			/*
			 * Do inserts if the batch size wasn't reached before the end of the
			 * loop
			 */
			if (wordBatch.size() > 0) {
				boolean result = wordDao.insertBatch(wordBatch);

				if (result) {
					this.rowCount += wordBatch.size();
				}
			}
		} finally {
			log.info("Rows inserted: " + this.rowCount);
			log.info("Time elapsed: " + (System.currentTimeMillis() - start) + "ms");
		}
	}

	/**
	 * Imports a Word, only calling down to the persistence layer once the batch
	 * size is reached.
	 * 
	 * @param word
	 *            the Word to import
	 * @param wordBatch
	 *            the batch of Words maintained across loop iterations
	 */
	protected void importWord(Word word, List<Word> wordBatch) {
		if (word == null) {
			// Nothing to do
			return;
		}

		wordBatch.add(word);

		/*
		 * Since the above loop adds a word several times depending on how many
		 * parts of speech it is related to, the batch size may be exceeded by a
		 * handful, and this is fine.
		 */
		if (wordBatch.size() >= batchSize) {
			boolean result = wordDao.insertBatch(wordBatch);

			if (result) {
				this.rowCount += wordBatch.size();
			}

			wordBatch.clear();
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
		this.partOfSpeechFileParser = fileParser;
	}
}