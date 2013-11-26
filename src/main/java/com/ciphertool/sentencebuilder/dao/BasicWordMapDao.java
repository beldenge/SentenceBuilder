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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ciphertool.sentencebuilder.common.PartOfSpeech;
import com.ciphertool.sentencebuilder.entities.Word;

public class BasicWordMapDao implements WordMapDao {

	private Map<PartOfSpeech, ArrayList<Word>> partOfSpeechWordMap = new HashMap<PartOfSpeech, ArrayList<Word>>();
	private Map<Integer, ArrayList<Word>> lengthWordMap = new HashMap<Integer, ArrayList<Word>>();

	/**
	 * Constructor with autowired dependency.
	 * 
	 * @param wordDao
	 *            the WordDao to use for populating the internal Maps
	 */
	@Autowired
	public BasicWordMapDao(WordDao wordDao) {
		if (wordDao == null) {
			throw new IllegalArgumentException(
					"Error constructing BasicWordMapDao.  WordDao cannot be null.");
		}

		ArrayList<Word> allWords = (ArrayList<Word>) wordDao.findAll();

		partOfSpeechWordMap = mapByPartOfSpeech(allWords);

		lengthWordMap = mapByWordLength(allWords);
	}

	@Override
	public Word findRandomWordByPartOfSpeech(PartOfSpeech pos) {
		ArrayList<Word> wordList = partOfSpeechWordMap.get(pos);

		int randomIndex = (int) (Math.random() * wordList.size());

		return wordList.get(randomIndex);
	}

	@Override
	public Word findRandomWordByLength(Integer length) {
		ArrayList<Word> wordList = lengthWordMap.get(length);

		int randomIndex = (int) (Math.random() * wordList.size());

		return wordList.get(randomIndex);
	}

	/**
	 * @param allWords
	 *            the List of all Words pulled in from the constructor
	 * @return a Map of all Words keyed by their PartOfSpeech
	 */
	protected static HashMap<PartOfSpeech, ArrayList<Word>> mapByPartOfSpeech(List<Word> allWords) {
		if (allWords == null || allWords.isEmpty()) {
			throw new IllegalArgumentException(
					"Error mapping Words by PartOfSpeech.  The supplied List of Words cannot be null or empty.");
		}

		HashMap<PartOfSpeech, ArrayList<Word>> byPartOfSpeech = new HashMap<PartOfSpeech, ArrayList<Word>>();
		for (Word w : allWords) {
			PartOfSpeech pos = PartOfSpeech.getValueFromSymbol(w.getId().getPartOfSpeech());

			// Add the part of speech to the map if it doesn't exist
			if (!byPartOfSpeech.containsKey(pos)) {
				byPartOfSpeech.put(pos, new ArrayList<Word>());
			}

			/*
			 * Add the word to the map by reference a number of times equal to
			 * the frequency value
			 */
			for (int i = 0; i < w.getFrequencyWeight(); i++) {
				byPartOfSpeech.get(pos).add(w);
			}
		}
		return byPartOfSpeech;
	}

	/**
	 * @param allWords
	 *            the List of all Words pulled in from the constructor
	 * @return a Map of all Words keyed by their length
	 */
	protected static HashMap<Integer, ArrayList<Word>> mapByWordLength(List<Word> allWords) {
		if (allWords == null || allWords.isEmpty()) {
			throw new IllegalArgumentException(
					"Error mapping Words by length.  The supplied List of Words cannot be null or empty.");
		}

		HashMap<Integer, ArrayList<Word>> byWordLength = new HashMap<Integer, ArrayList<Word>>();

		for (Word w : allWords) {
			Integer length = w.getId().getWord().length();

			// Add the part of speech to the map if it doesn't exist
			if (!byWordLength.containsKey(length)) {
				byWordLength.put(length, new ArrayList<Word>());
			}

			/*
			 * Add the word to the map by reference a number of times equal to
			 * the frequency value
			 */
			for (int i = 0; i < w.getFrequencyWeight(); i++) {
				byWordLength.get(length).add(w);
			}
		}

		return byWordLength;
	}

	@Override
	public Map<PartOfSpeech, ArrayList<Word>> getPartOfSpeechWordMap() {
		return Collections.unmodifiableMap(partOfSpeechWordMap);
	}

	@Override
	public Map<Integer, ArrayList<Word>> getLengthWordMap() {
		return Collections.unmodifiableMap(lengthWordMap);
	}
}
