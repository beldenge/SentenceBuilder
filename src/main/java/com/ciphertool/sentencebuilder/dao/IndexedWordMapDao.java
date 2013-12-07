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

import com.ciphertool.sentencebuilder.common.PartOfSpeechType;
import com.ciphertool.sentencebuilder.entities.Word;

/**
 * This is a lower memory utilization implementation of WordMapDao. It builds an
 * index map using int, which is 32-bit, instead of the 64-bit reference needed
 * to hold each Object reference.
 * 
 * @author George Belden
 */
public class IndexedWordMapDao implements WordMapDao {

	private Map<PartOfSpeechType, ArrayList<Word>> partOfSpeechWordMap;
	private Map<Integer, ArrayList<Word>> lengthWordMap;
	private Map<PartOfSpeechType, int[]> partOfSpeechFrequencyMap;
	private Map<Integer, int[]> lengthFrequencyMap;

	/**
	 * Constructor with autowired dependency.
	 * 
	 * @param wordDao
	 *            the WordDao to use for populating the internal Maps
	 */
	@Autowired
	public IndexedWordMapDao(WordDao wordDao) {
		if (wordDao == null) {
			throw new IllegalArgumentException(
					"Error constructing IndexedWordMapDao.  WordDao cannot be null.");
		}

		ArrayList<Word> allWords = new ArrayList<Word>();
		allWords.addAll(wordDao.findAll());

		partOfSpeechWordMap = mapByPartOfSpeech(allWords);

		partOfSpeechFrequencyMap = buildIndexedFrequencyMapByPartOfSpeech(partOfSpeechWordMap);

		lengthWordMap = mapByWordLength(allWords);

		lengthFrequencyMap = buildIndexedFrequencyMapByLength(lengthWordMap);
	}

	@Override
	public Word findRandomWordByPartOfSpeech(PartOfSpeechType pos) {
		int[] indexList = partOfSpeechFrequencyMap.get(pos);

		/*
		 * This returns a pseudorandom number which is bounded by the
		 * ArrayList's size - 1, which is perfect since the elements are
		 * zero-indexed
		 */
		int randomIndex = (int) (Math.random() * indexList.length);

		int selectedIndex = indexList[randomIndex];

		ArrayList<Word> wordList = partOfSpeechWordMap.get(pos);

		return wordList.get(selectedIndex);
	}

	@Override
	public Word findRandomWordByLength(Integer length) {
		int[] indexList = lengthFrequencyMap.get(length);

		/*
		 * This returns a pseudorandom number which is bounded by the
		 * ArrayList's size - 1, which is perfect since the elements are
		 * zero-indexed
		 */
		int randomIndex = (int) (Math.random() * indexList.length);

		int selectedIndex = indexList[randomIndex];

		ArrayList<Word> wordList = lengthWordMap.get(length);

		return wordList.get(selectedIndex);
	}

	/**
	 * @param allWords
	 *            the List of all Words pulled in from the constructor
	 * @return a Map of all Words keyed by their PartOfSpeech
	 */
	protected static Map<PartOfSpeechType, ArrayList<Word>> mapByPartOfSpeech(List<Word> allWords) {
		if (allWords == null || allWords.isEmpty()) {
			throw new IllegalArgumentException(
					"Error mapping Words by PartOfSpeech.  The supplied List of Words cannot be null or empty.");
		}

		HashMap<PartOfSpeechType, ArrayList<Word>> byPartOfSpeech = new HashMap<PartOfSpeechType, ArrayList<Word>>();

		for (Word word : allWords) {
			PartOfSpeechType pos = word.getId().getPartOfSpeech();

			// Add the part of speech to the map if it doesn't exist
			if (!byPartOfSpeech.containsKey(pos)) {
				byPartOfSpeech.put(pos, new ArrayList<Word>());
			}

			byPartOfSpeech.get(pos).add(word);
		}

		return byPartOfSpeech;
	}

	/**
	 * @param allWords
	 *            the List of all Words pulled in from the constructor
	 * @return a Map of all Words keyed by their length
	 */
	protected static Map<Integer, ArrayList<Word>> mapByWordLength(List<Word> allWords) {
		if (allWords == null || allWords.isEmpty()) {
			throw new IllegalArgumentException(
					"Error mapping Words by length.  The supplied List of Words cannot be null or empty.");
		}

		HashMap<Integer, ArrayList<Word>> byLength = new HashMap<Integer, ArrayList<Word>>();

		for (Word w : allWords) {
			Integer wordLength = w.getId().getWord().length();

			// Add the part of speech to the map if it doesn't exist
			if (!byLength.containsKey(wordLength)) {
				byLength.put(wordLength, new ArrayList<Word>());
			}

			byLength.get(wordLength).add(w);
		}

		return byLength;
	}

	/**
	 * Add the word to the map by reference a number of times equal to the
	 * frequency value
	 * 
	 * TODO: We can probably reduce memory utilization even more if we combine
	 * the words into one ArrayList not separated by PartOfSpeech, and then only
	 * separate the index HashMap by parts of speech
	 * 
	 * @param byPartOfSpeech
	 *            the Map of Words keyed by PartOfSpeech
	 * @return the index Map keyed by length
	 */
	protected static Map<PartOfSpeechType, int[]> buildIndexedFrequencyMapByPartOfSpeech(
			Map<PartOfSpeechType, ArrayList<Word>> byPartOfSpeech) {
		if (byPartOfSpeech == null || byPartOfSpeech.isEmpty()) {
			throw new IllegalArgumentException(
					"Error indexing PartOfSpeech map.  The supplied Map of Words cannot be null or empty.");
		}

		HashMap<PartOfSpeechType, int[]> byFrequency = new HashMap<PartOfSpeechType, int[]>();

		int frequencySum;

		/*
		 * Loop through each PartOfSpeech, add up the frequency weights, and
		 * create an int array of the resulting length
		 */
		for (Map.Entry<PartOfSpeechType, ArrayList<Word>> pos : byPartOfSpeech.entrySet()) {
			frequencySum = 0;

			for (Word w : byPartOfSpeech.get(pos.getKey())) {
				frequencySum += w.getFrequencyWeight();
			}

			byFrequency.put(pos.getKey(), new int[frequencySum]);
		}

		int frequencyIndex;
		int wordIndex;

		/*
		 * Loop through each PartOfSpeech and Word, and add the index to the
		 * frequencyMap a number of times equal to the word's frequency weight
		 */
		for (Map.Entry<PartOfSpeechType, ArrayList<Word>> partOfSpeechEntry : byPartOfSpeech
				.entrySet()) {
			frequencyIndex = 0;
			wordIndex = 0;

			for (Word w : byPartOfSpeech.get(partOfSpeechEntry.getKey())) {
				for (int i = 0; i < w.getFrequencyWeight(); i++) {
					byFrequency.get(partOfSpeechEntry.getKey())[frequencyIndex] = wordIndex;

					frequencyIndex++;
				}
				wordIndex++;
			}
		}

		return byFrequency;
	}

	/**
	 * Add the word to the map by reference a number of times equal to the
	 * frequency value
	 * 
	 * TODO: We can probably reduce memory utilization even more if we combine
	 * the words into one ArrayList not separated by Word length, and then only
	 * separate the index HashMap by length
	 * 
	 * @param byLength
	 *            the Map of Words keyed by length
	 * @return the index Map keyed by length
	 */
	protected static Map<Integer, int[]> buildIndexedFrequencyMapByLength(
			Map<Integer, ArrayList<Word>> byLength) {
		if (byLength == null || byLength.isEmpty()) {
			throw new IllegalArgumentException(
					"Error indexing Word length map.  The supplied Map of Words cannot be null or empty.");
		}

		HashMap<Integer, int[]> byFrequency = new HashMap<Integer, int[]>();

		int frequencySum;

		/*
		 * Loop through each Word length, add up the frequency weights, and
		 * create an int array of the resulting length
		 */
		for (Map.Entry<Integer, ArrayList<Word>> length : byLength.entrySet()) {
			frequencySum = 0;

			for (Word w : byLength.get(length.getKey())) {
				frequencySum += w.getFrequencyWeight();
			}

			byFrequency.put(length.getKey(), new int[frequencySum]);
		}

		int frequencyIndex;
		int wordIndex;

		/*
		 * Loop through each length and word, and add the index to the
		 * frequencyMap a number of times equal to the word's frequency weight
		 */
		for (Map.Entry<Integer, ArrayList<Word>> lengthEntry : byLength.entrySet()) {
			frequencyIndex = 0;
			wordIndex = 0;

			for (Word w : byLength.get(lengthEntry.getKey())) {
				for (int i = 0; i < w.getFrequencyWeight(); i++) {
					byFrequency.get(lengthEntry.getKey())[frequencyIndex] = wordIndex;

					frequencyIndex++;
				}
				wordIndex++;
			}
		}

		return byFrequency;
	}

	@Override
	public Map<PartOfSpeechType, ArrayList<Word>> getPartOfSpeechWordMap() {
		return Collections.unmodifiableMap(partOfSpeechWordMap);
	}

	@Override
	public Map<Integer, ArrayList<Word>> getLengthWordMap() {
		return Collections.unmodifiableMap(lengthWordMap);
	}
}
