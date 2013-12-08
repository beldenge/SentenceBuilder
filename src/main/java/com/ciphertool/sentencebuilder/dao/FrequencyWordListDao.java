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

package com.ciphertool.sentencebuilder.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ciphertool.sentencebuilder.entities.Word;

public class FrequencyWordListDao implements WordListDao {
	private static Logger log = Logger.getLogger(FrequencyWordListDao.class);

	private List<Word> wordList = new ArrayList<Word>();

	/**
	 * Constructor requiring a WordDao dependency as its only argument. Stacks
	 * the List based on Word frequency.
	 * 
	 * @param wordDao
	 *            the WordDao to use for populating the internal List
	 */
	public FrequencyWordListDao(WordDao wordDao) {
		if (wordDao == null) {
			throw new IllegalArgumentException(
					"Error constructing FrequencyWordListDao.  WordDao cannot be null.");
		}

		log.info("Beginning fetching of words from database.");

		long start = System.currentTimeMillis();

		wordList.addAll(wordDao.findAllUniqueWords());

		log.info("Finished fetching words from database in " + (System.currentTimeMillis() - start)
				+ "ms.");

		List<Word> wordsToAdd = new ArrayList<Word>();

		for (Word w : this.wordList) {
			/*
			 * Add the word to the map by reference a number of times equal to
			 * the frequency value - 1 since it already exists in the list once.
			 */
			for (int i = 0; i < w.getFrequencyWeight() - 1; i++) {
				wordsToAdd.add(w);
			}
		}

		this.wordList.addAll(wordsToAdd);
	}

	@Override
	public Word findRandomWord() {
		int randomIndex = (int) (Math.random() * wordList.size());

		return wordList.get(randomIndex);
	}
}
