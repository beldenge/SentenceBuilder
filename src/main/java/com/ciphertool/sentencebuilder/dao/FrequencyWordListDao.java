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
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ciphertool.sentencebuilder.entities.Word;

public class FrequencyWordListDao implements WordListDao {
	private static Logger log = LoggerFactory.getLogger(FrequencyWordListDao.class);

	private List<Word> wordList = new ArrayList<Word>();

	/**
	 * Constructor requiring a WordDao and top number of Words. Stacks the List based on Word frequency.
	 * 
	 * @param wordDao
	 *            the WordDao to use for populating the internal List
	 * @param top
	 *            the top number of words
	 */
	public FrequencyWordListDao(WordDao wordDao, Integer top) {
		if (wordDao == null) {
			throw new IllegalArgumentException("Error constructing FrequencyWordListDao.  WordDao cannot be null.");
		}

		if (top == null) {
			throw new IllegalArgumentException(
					"Error constructing FrequencyWordListDao.  Top cannot be null.  Please ensure top is either set to a positive number, or to -1 to be unbounded.");
		}

		log.info("Beginning fetching of words from database.");

		long start = System.currentTimeMillis();

		if (top < 0) {
			wordList.addAll(wordDao.findAllUniqueWords());
		} else {
			wordList.addAll(wordDao.findTopUniqueWordsByFrequency(top));
		}

		log.info("Finished fetching words from database in " + (System.currentTimeMillis() - start) + "ms.");

		List<Word> wordsToAdd = new ArrayList<Word>();

		for (Word w : this.wordList) {
			/*
			 * Add the word to the map by reference a number of times equal to the frequency value - 1 since it already
			 * exists in the list once.
			 */
			for (int i = 0; i < w.getFrequencyWeight() - 1; i++) {
				wordsToAdd.add(w);
			}
		}

		this.wordList.addAll(wordsToAdd);
	}

	@Override
	public Word findRandomWord() {
		int randomIndex = (int) (ThreadLocalRandom.current().nextDouble() * wordList.size());

		return wordList.get(randomIndex);
	}
}
