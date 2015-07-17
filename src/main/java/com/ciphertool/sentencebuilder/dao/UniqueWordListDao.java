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

package com.ciphertool.sentencebuilder.dao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;

import com.ciphertool.sentencebuilder.entities.Word;

public class UniqueWordListDao implements WordListDao {
	private static Logger log = Logger.getLogger(UniqueWordListDao.class);

	private List<Word> wordList = new ArrayList<Word>();

	/**
	 * Constructor requiring a WordDao dependency as its only argument.
	 * 
	 * @param wordDao
	 *            the WordDao to use for populating the internal List
	 */
	public UniqueWordListDao(WordDao wordDao) {
		if (wordDao == null) {
			throw new IllegalArgumentException("Error constructing UniqueWordListDao.  WordDao cannot be null.");
		}

		log.info("Beginning fetching of words from database.");

		long start = System.currentTimeMillis();

		wordList.addAll(wordDao.findAllUniqueWords());

		log.info("Finished fetching words from database in " + (System.currentTimeMillis() - start) + "ms.");

		wordList.sort(new Comparator<Word>() {
			@Override
			public int compare(Word word1, Word word2) {
				if (word1.getFrequencyWeight() < word2.getFrequencyWeight()) {
					return 1;
				} else if (word1.getFrequencyWeight() > word2.getFrequencyWeight()) {
					return -1;
				}

				return 0;
			}
		});
	}

	@Override
	public Word findRandomWord() {
		int randomIndex = (int) (ThreadLocalRandom.current().nextDouble() * wordList.size());

		return wordList.get(randomIndex);
	}

	public List<Word> getTopWords(int top) {
		return wordList.subList(0, top);
	}
}
