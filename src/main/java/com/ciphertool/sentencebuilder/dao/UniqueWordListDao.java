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

import com.ciphertool.sentencebuilder.entities.Word;

public class UniqueWordListDao implements WordListDao {
	private List<Word> wordList = new ArrayList<Word>();

	/**
	 * Constructor requiring a WordDao dependency as its only argument.
	 * 
	 * @param wordDao
	 *            the WordDao to use for populating the internal List
	 */
	public UniqueWordListDao(WordDao wordDao) {
		if (wordDao == null) {
			throw new IllegalArgumentException(
					"Error constructing UniqueWordListDao.  WordDao cannot be null.");
		}

		wordList.addAll(wordDao.findAllUniqueWords());
	}

	@Override
	public Word findRandomWord() {
		int randomIndex = (int) (Math.random() * wordList.size());

		return wordList.get(randomIndex);
	}
}
