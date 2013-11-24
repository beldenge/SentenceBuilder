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
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ciphertool.sentencebuilder.common.PartOfSpeech;
import com.ciphertool.sentencebuilder.entities.Word;

/**
 * This is a lower memory utilization implementation of WordMapDao. It builds an
 * index map using int, which is 32-bit, instead of the 64-bit reference needed
 * to hold each object reference.
 * 
 * @author george
 */
public class IndexedWordMapDao implements WordMapDao {

	private HashMap<PartOfSpeech, ArrayList<Word>> partOfSpeechWordMap;
	private HashMap<Integer, ArrayList<Word>> lengthWordMap;
	private HashMap<PartOfSpeech, int[]> partOfSpeechFrequencyMap;
	private HashMap<Integer, int[]> lengthFrequencyMap;
	private WordDao wordDao;

	@Autowired
	public IndexedWordMapDao(WordDao wordDao) {
		this.wordDao = wordDao;

		ArrayList<Word> allWords = (ArrayList<Word>) this.wordDao.findAll();

		partOfSpeechWordMap = this.mapByPartOfSpeech(allWords);

		partOfSpeechFrequencyMap = buildIndexedFrequencyMapByPartOfSpeech(partOfSpeechWordMap);

		lengthWordMap = this.mapByLength(allWords);

		lengthFrequencyMap = buildIndexedFrequencyMapByLength(lengthWordMap);
	}

	@Override
	public Word findRandomWordByPartOfSpeech(PartOfSpeech pos) {
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

	private HashMap<PartOfSpeech, ArrayList<Word>> mapByPartOfSpeech(ArrayList<Word> allWords) {
		HashMap<PartOfSpeech, ArrayList<Word>> byPartOfSpeech = new HashMap<PartOfSpeech, ArrayList<Word>>();

		for (Word w : allWords) {
			PartOfSpeech pos = PartOfSpeech.getValueFromSymbol(w.getId().getPartOfSpeech());

			// Add the part of speech to the map if it doesn't exist
			if (!byPartOfSpeech.containsKey(pos)) {
				byPartOfSpeech.put(pos, new ArrayList<Word>());
			}

			byPartOfSpeech.get(pos).add(w);
		}

		return byPartOfSpeech;
	}

	private HashMap<Integer, ArrayList<Word>> mapByLength(ArrayList<Word> allWords) {
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

	/*
	 * Add the word to the map by reference a number of times equal to the
	 * frequency value
	 * 
	 * TODO: We can probably reduce memory utilization even more if we combine
	 * the words into one ArrayList not separated by part of speech, and then
	 * only separate the index HashMap by parts of speech
	 */
	private HashMap<PartOfSpeech, int[]> buildIndexedFrequencyMapByPartOfSpeech(
			HashMap<PartOfSpeech, ArrayList<Word>> byPartOfSpeech) {
		HashMap<PartOfSpeech, int[]> byFrequency = new HashMap<PartOfSpeech, int[]>();

		int frequencySum;

		/*
		 * Loop through each pos, add up the frequencyWeights, and create an int
		 * array of the resulting length
		 */
		for (Map.Entry<PartOfSpeech, ArrayList<Word>> pos : byPartOfSpeech.entrySet()) {
			frequencySum = 0;

			for (Word w : byPartOfSpeech.get(pos.getKey())) {
				frequencySum += w.getFrequencyWeight();
			}

			byFrequency.put(pos.getKey(), new int[frequencySum]);
		}

		int frequencyIndex;
		int wordIndex;

		/*
		 * Loop through each pos and word, and add the index to the frequencyMap
		 * a number of times equal to the word's frequency weight
		 */
		for (Map.Entry<PartOfSpeech, ArrayList<Word>> pos : byPartOfSpeech.entrySet()) {
			frequencyIndex = 0;
			wordIndex = 0;

			for (Word w : byPartOfSpeech.get(pos.getKey())) {
				for (int i = 0; i < w.getFrequencyWeight(); i++) {
					byFrequency.get(pos.getKey())[frequencyIndex] = wordIndex;

					frequencyIndex++;
				}
				wordIndex++;
			}
		}

		return byFrequency;
	}

	/*
	 * Add the word to the map by reference a number of times equal to the
	 * frequency value
	 * 
	 * TODO: We can probably reduce memory utilization even more if we combine
	 * the words into one ArrayList not separated by part of speech, and then
	 * only separate the index HashMap by parts of speech
	 */
	private HashMap<Integer, int[]> buildIndexedFrequencyMapByLength(
			HashMap<Integer, ArrayList<Word>> byLength) {
		HashMap<Integer, int[]> byFrequency = new HashMap<Integer, int[]>();

		int frequencySum;

		/*
		 * Loop through each word length, add up the frequencyWeights, and
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
		for (Map.Entry<Integer, ArrayList<Word>> length : byLength.entrySet()) {
			frequencyIndex = 0;
			wordIndex = 0;

			for (Word w : byLength.get(length.getKey())) {
				for (int i = 0; i < w.getFrequencyWeight(); i++) {
					byFrequency.get(length.getKey())[frequencyIndex] = wordIndex;

					frequencyIndex++;
				}
				wordIndex++;
			}
		}

		return byFrequency;
	}

	/**
	 * @return the wordMap
	 */
	@Override
	public HashMap<PartOfSpeech, ArrayList<Word>> getPartOfSpeechWordMap() {
		return partOfSpeechWordMap;
	}

	/**
	 * @return the lengthWordMap
	 */
	@Override
	public HashMap<Integer, ArrayList<Word>> getLengthWordMap() {
		return lengthWordMap;
	}
}
