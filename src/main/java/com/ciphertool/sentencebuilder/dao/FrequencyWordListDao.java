package com.ciphertool.sentencebuilder.dao;

import java.util.ArrayList;
import java.util.List;

import com.ciphertool.sentencebuilder.entities.Word;

public class FrequencyWordListDao implements WordListDao {
	private ArrayList<Word> wordList;
	private WordDao wordDao;

	/*
	 * Calls the super constructor and then stacks the list based on word
	 * frequency
	 */
	public FrequencyWordListDao(WordDao wordDao) {
		this.wordDao = wordDao;
		wordList = (ArrayList<Word>) this.wordDao.findAllUniqueWords();

		List<Word> wordsToAdd = new ArrayList<Word>();

		for (Word w : this.wordList) {
			/*
			 * Add the word to the map by reference a number of times equal to
			 * the frequency value -1 since it already exists in the list once.
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
