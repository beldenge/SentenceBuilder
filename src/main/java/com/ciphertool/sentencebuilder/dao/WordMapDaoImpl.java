/**
 * Copyright 2012 George Belden
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

import org.springframework.beans.factory.annotation.Autowired;

import com.ciphertool.sentencebuilder.common.PartOfSpeech;
import com.ciphertool.sentencebuilder.entities.Word;

public class WordMapDaoImpl implements WordMapDao {

	private HashMap<PartOfSpeech, ArrayList<Word>> wordMap;
	private WordDao wordDao;

	@Autowired
	public WordMapDaoImpl(WordDao wordDao) {
		this.wordDao = wordDao;
		wordMap = this.mapByPartOfSpeech((ArrayList<Word>) this.wordDao.findAll());
	}

	@Override
	public Word findRandomWordByPartOfSpeech(PartOfSpeech pos) {
		ArrayList<Word> wordList = wordMap.get(pos);
		int randomIndex = (int) (Math.random() * wordList.size());
		return wordList.get(randomIndex);
	}

	private HashMap<PartOfSpeech, ArrayList<Word>> mapByPartOfSpeech(ArrayList<Word> allWords) {
		HashMap<PartOfSpeech, ArrayList<Word>> byPartOfSpeech = new HashMap<PartOfSpeech, ArrayList<Word>>();
		for (Word w : allWords) {
			PartOfSpeech pos = PartOfSpeech.typeOf(w.getWordId().getPartOfSpeech());

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
	 * @return the wordMap
	 */
	@Override
	public HashMap<PartOfSpeech, ArrayList<Word>> getWordMap() {
		return wordMap;
	}
}
