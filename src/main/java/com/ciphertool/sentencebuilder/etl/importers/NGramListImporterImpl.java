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
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.task.TaskExecutor;

import com.ciphertool.sentencebuilder.dao.NGramDao;
import com.ciphertool.sentencebuilder.entities.NGram;
import com.ciphertool.sentencebuilder.etl.parsers.FileParser;

public class NGramListImporterImpl implements NGramListImporter {

	private static Logger log = Logger.getLogger(NGramListImporterImpl.class);

	private TaskExecutor taskExecutor;
	private FileParser<NGram> nGramFileParser;
	private NGramDao nGramDao;
	private int persistenceBatchSize;
	private AtomicInteger rowCount = new AtomicInteger(0);
	private int concurrencyBatchSize;

	private String[] fileNames;

	@Override
	public void importNGramList() {
		// Reset the counts in case this method is called again
		rowCount.set(0);

		long start = System.currentTimeMillis();

		try {
			List<NGram> nGramsFromFile = new ArrayList<NGram>();
			for (String fileName : fileNames) {
				nGramsFromFile.addAll(nGramFileParser.parseFile(fileName));
			}

			log.info("Starting n-gram list import...");

			List<FutureTask<Void>> futureTasks = new ArrayList<FutureTask<Void>>();
			FutureTask<Void> futureTask = null;
			List<NGram> threadedNGramBatch = new ArrayList<NGram>();
			for (NGram nGram : nGramsFromFile) {
				threadedNGramBatch.add(nGram);

				if (threadedNGramBatch.size() >= this.concurrencyBatchSize) {
					List<NGram> nextThreadedNGramBatch = new ArrayList<NGram>();
					int originalSize = threadedNGramBatch.size();

					for (int i = 0; i < originalSize; i++) {
						/*
						 * It's faster to remove from the end of the List because no elements need to shift
						 */
						nextThreadedNGramBatch.add(threadedNGramBatch.remove(threadedNGramBatch.size() - 1));
					}

					futureTask = new FutureTask<Void>(new BatchNGramImportTask(nextThreadedNGramBatch));
					futureTasks.add(futureTask);
					this.taskExecutor.execute(futureTask);
				}
			}

			/*
			 * Start one last task if there are any leftover NGrams from file that did not reach the batch size.
			 */
			if (threadedNGramBatch.size() > 0) {
				/*
				 * It's safe to use the threadedNGramBatch now, instead of copying into a temporaryList, because this is
				 * the last thread to run.
				 */
				futureTask = new FutureTask<Void>(new BatchNGramImportTask(threadedNGramBatch));
				futureTasks.add(futureTask);
				this.taskExecutor.execute(futureTask);
			}

			for (FutureTask<Void> future : futureTasks) {
				try {
					future.get();
				} catch (InterruptedException ie) {
					log.error("Caught InterruptedException while waiting for BatchNGramImportTask ", ie);
				} catch (ExecutionException ee) {
					log.error("Caught ExecutionException while waiting for BatchNGramImportTask ", ee);
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
	protected class BatchNGramImportTask implements Callable<Void> {

		private List<NGram> nGrams;

		public BatchNGramImportTask(List<NGram> nGrams) {
			this.nGrams = nGrams;
		}

		@Override
		public Void call() throws Exception {
			List<NGram> nGramInsertBatch = new ArrayList<NGram>();

			for (NGram nGram : this.nGrams) {
				importNGram(nGram, nGramInsertBatch);
			}

			if (nGramInsertBatch.size() > 0) {
				boolean result = nGramDao.insertBatch(nGramInsertBatch);

				if (result) {
					rowCount.addAndGet(nGramInsertBatch.size());
				}
			}

			return null;
		}
	}

	/**
	 * Imports an NGram, only calling down to the persistence layer once the batch size is reached.
	 * 
	 * @param nGram
	 *            the next line to import
	 * @param nGramInsertBatch
	 *            the batch of NGrams to insert, maintained across loop iterations
	 */
	protected void importNGram(NGram nGram, List<NGram> nGramBatch) {
		if (nGram == null) {
			// Nothing to do
			return;
		}

		nGramBatch.add(nGram);

		/*
		 * Since the above loop adds a nGram several times depending on how many parts of speech it is related to, the
		 * batch size may be exceeded by a handful, and this is fine.
		 */
		if (nGramBatch.size() >= this.persistenceBatchSize) {
			boolean result = this.nGramDao.insertBatch(nGramBatch);

			if (result) {
				this.rowCount.addAndGet(nGramBatch.size());
			}

			nGramBatch.clear();
		}
	}

	/**
	 * @param nGramDao
	 *            the nGramDao to set
	 */
	@Required
	public void setNGramDao(NGramDao nGramDao) {
		this.nGramDao = nGramDao;
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
	public void setFileParser(FileParser<NGram> fileParser) {
		this.nGramFileParser = fileParser;
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

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	@Required
	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}
}
