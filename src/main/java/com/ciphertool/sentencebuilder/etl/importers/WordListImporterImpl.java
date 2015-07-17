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

package com.ciphertool.sentencebuilder.etl.importers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.task.TaskExecutor;

import com.ciphertool.sentencebuilder.dao.WordDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.etl.parsers.FileParser;

public class WordListImporterImpl implements WordListImporter {

	private static Logger log = Logger.getLogger(WordListImporterImpl.class);

	private TaskExecutor taskExecutor;
	private FileParser<Word> partOfSpeechFileParser;
	private WordDao wordDao;
	private int persistenceBatchSize;
	private AtomicInteger rowCount = new AtomicInteger(0);
	private int concurrencyBatchSize;

	@Override
	public void importWordList() {
		// Reset the count in case this method is called again
		rowCount.set(0);

		long start = System.currentTimeMillis();

		try {
			List<Word> wordsFromFile = partOfSpeechFileParser.parseFile();

			log.info("Starting word list import...");

			List<FutureTask<Void>> futureTasks = new ArrayList<FutureTask<Void>>();
			FutureTask<Void> futureTask = null;
			List<Word> threadedWordBatch = new ArrayList<Word>();
			for (Word word : wordsFromFile) {
				threadedWordBatch.add(word);

				if (threadedWordBatch.size() >= this.concurrencyBatchSize) {
					List<Word> nextThreadedWordBatch = new ArrayList<Word>();
					int originalSize = threadedWordBatch.size();

					for (int i = 0; i < originalSize; i++) {
						/*
						 * It's faster to remove from the end of the List because no elements need to shift
						 */
						nextThreadedWordBatch.add(threadedWordBatch.remove(threadedWordBatch.size() - 1));
					}

					futureTask = new FutureTask<Void>(new BatchWordImportTask(nextThreadedWordBatch));
					futureTasks.add(futureTask);
					this.taskExecutor.execute(futureTask);
				}
			}

			/*
			 * Start one last task if there are any leftover Words from file that did not reach the batch size.
			 */
			if (threadedWordBatch.size() > 0) {
				/*
				 * It's safe to use the threadedWordBatch now, instead of copying into a temporaryList, because this is
				 * the last thread to run.
				 */
				futureTask = new FutureTask<Void>(new BatchWordImportTask(threadedWordBatch));
				futureTasks.add(futureTask);
				this.taskExecutor.execute(futureTask);
			}

			for (FutureTask<Void> future : futureTasks) {
				try {
					future.get();
				} catch (InterruptedException ie) {
					log.error("Caught InterruptedException while waiting for BatchWordImportTask ", ie);
				} catch (ExecutionException ee) {
					log.error("Caught ExecutionException while waiting for BatchWordImportTask ", ee);
				}
			}
		} finally {
			log.info("Rows inserted: " + this.rowCount);
			log.info("Time elapsed: " + (System.currentTimeMillis() - start) + "ms");
		}
	}

	/**
	 * A concurrent task for performing a crossover of two parent Chromosomes, producing one child Chromosome.
	 */
	protected class BatchWordImportTask implements Callable<Void> {

		private List<Word> words;

		public BatchWordImportTask(List<Word> words) {
			this.words = words;
		}

		@Override
		public Void call() throws Exception {
			List<Word> wordBatch = new ArrayList<Word>();

			for (Word word : this.words) {
				importWord(word, wordBatch);
			}

			/*
			 * Insert one last batch if there are any leftover Words that did not reach the batch size.
			 */
			if (wordBatch.size() > 0) {
				boolean result = wordDao.insertBatch(wordBatch);

				if (result) {
					rowCount.addAndGet(wordBatch.size());
				}
			}

			return null;
		}
	}

	/**
	 * Imports a Word, only calling down to the persistence layer once the batch size is reached.
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
		 * Since the above loop adds a word several times depending on how many parts of speech it is related to, the
		 * batch size may be exceeded by a handful, and this is fine.
		 */
		if (wordBatch.size() >= this.persistenceBatchSize) {
			boolean result = this.wordDao.insertBatch(wordBatch);

			if (result) {
				this.rowCount.addAndGet(wordBatch.size());
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
	 * @param persistenceBatchSize
	 *            the persistenceBatchSize to set
	 */
	@Required
	public void setPersistenceBatchSize(int persistenceBatchSize) {
		this.persistenceBatchSize = persistenceBatchSize;
	}

	/**
	 * @param fileParser
	 *            the fileParser to set
	 */
	@Required
	public void setFileParser(FileParser<Word> fileParser) {
		this.partOfSpeechFileParser = fileParser;
	}

	/**
	 * @param concurrencyBatchSize
	 *            the concurrencyBatchSize to set
	 */
	@Required
	public void setConcurrencyBatchSize(int concurrencyBatchSize) {
		this.concurrencyBatchSize = concurrencyBatchSize;
	}

	/**
	 * @param taskExecutor
	 *            the taskExecutor to set
	 */
	@Required
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
}