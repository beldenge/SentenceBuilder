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

public class FrequencyListImporterImpl implements FrequencyListImporter {

	private static Logger log = Logger.getLogger(FrequencyListImporterImpl.class);

	private TaskExecutor taskExecutor;
	private FileParser<Word> frequencyFileParser;
	private WordDao wordDao;
	private int persistenceBatchSize;
	private AtomicInteger rowUpdateCount = new AtomicInteger(0);
	private AtomicInteger rowInsertCount = new AtomicInteger(0);
	private int concurrencyBatchSize;

	@Override
	public void importFrequencyList() {
		// Reset the counts in case this method is called again
		rowUpdateCount.set(0);
		rowInsertCount.set(0);

		long start = System.currentTimeMillis();

		try {

			List<Word> wordsFromFile = frequencyFileParser.parseFile();

			log.info("Starting frequency list import...");

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
						 * It's faster to remove from the end of the List
						 * because no elements need to shift
						 */
						nextThreadedWordBatch.add(threadedWordBatch
								.remove(threadedWordBatch.size() - 1));
					}

					futureTask = new FutureTask<Void>(
							new BatchWordImportTask(nextThreadedWordBatch));
					futureTasks.add(futureTask);
					this.taskExecutor.execute(futureTask);
				}
			}

			/*
			 * Start one last task if there are any leftover Words from file
			 * that did not reach the batch size.
			 */
			if (threadedWordBatch.size() > 0) {
				/*
				 * It's safe to use the threadedWordBatch now, instead of
				 * copying into a temporaryList, because this is the last thread
				 * to run.
				 */
				futureTask = new FutureTask<Void>(new BatchWordImportTask(threadedWordBatch));
				futureTasks.add(futureTask);
				this.taskExecutor.execute(futureTask);
			}

			for (FutureTask<Void> future : futureTasks) {
				try {
					future.get();
				} catch (InterruptedException ie) {
					log.error("Caught InterruptedException while waiting for BatchWordImportTask ",
							ie);
				} catch (ExecutionException ee) {
					log.error("Caught ExecutionException while waiting for BatchWordImportTask ",
							ee);
				}
			}
		} finally {
			log.info("Rows updated: " + this.rowUpdateCount);
			log.info("Rows inserted: " + this.rowInsertCount);
			log.info("Time elapsed: " + (System.currentTimeMillis() - start) + "ms");
		}
	}

	/**
	 * A concurrent task for performing a crossover of two parent Chromosomes,
	 * producing one child Chromosome.
	 */
	protected class BatchWordImportTask implements Callable<Void> {

		private List<Word> words;

		public BatchWordImportTask(List<Word> words) {
			this.words = words;
		}

		@Override
		public Void call() throws Exception {
			List<Word> wordUpdateBatch = new ArrayList<Word>();
			List<Word> wordInsertBatch = new ArrayList<Word>();

			for (Word word : this.words) {
				importWord(word, wordInsertBatch, wordUpdateBatch);
			}

			/*
			 * Do updates and inserts if the batch size wasn't reached before
			 * the end of the loop
			 */
			if (wordUpdateBatch.size() > 0) {
				boolean result = wordDao.updateBatch(wordUpdateBatch);

				if (result) {
					rowUpdateCount.addAndGet(wordUpdateBatch.size());
				}
			}

			if (wordInsertBatch.size() > 0) {
				boolean result = wordDao.insertBatch(wordInsertBatch);

				if (result) {
					rowInsertCount.addAndGet(wordInsertBatch.size());
				}
			}

			return null;
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
		List<Word> wordsFromDatabase = wordDao.findByWordString(word.getId().getWord());

		if (wordsFromDatabase == null || wordsFromDatabase.size() == 0) {
			log.debug("No frequency matches found in part_of_speech table for word: " + word
					+ ".  Inserting with a filler value for the part_of_speech column.");

			wordInsertBatch.add(word);

			if (wordInsertBatch.size() >= this.persistenceBatchSize) {
				boolean result = this.wordDao.insertBatch(wordInsertBatch);

				if (result) {
					this.rowInsertCount.addAndGet(wordInsertBatch.size());
				}

				wordInsertBatch.clear();
			}
		} else {
			/*
			 * Loop over the list and update each word with the frequency
			 */
			for (Word w : wordsFromDatabase) {
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
			if (wordUpdateBatch.size() >= this.persistenceBatchSize) {
				boolean result = this.wordDao.updateBatch(wordUpdateBatch);

				if (result) {
					this.rowUpdateCount.addAndGet(wordUpdateBatch.size());
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
		this.frequencyFileParser = fileParser;
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
