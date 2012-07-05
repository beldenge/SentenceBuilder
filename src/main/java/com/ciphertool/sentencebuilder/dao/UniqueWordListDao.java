package com.ciphertool.sentencebuilder.dao;

import java.util.ArrayList;

import com.ciphertool.sentencebuilder.entities.Word;

public class UniqueWordListDao implements WordListDao {
	private ArrayList<Word> wordList;
	private WordDao wordDao;

	public UniqueWordListDao(WordDao wordDao) {
		this.wordDao = wordDao;
		wordList = (ArrayList<Word>) this.wordDao.findAllUniqueWords();
	}

	@Override
	public Word findRandomWord() {
		int randomIndex = (int) (Math.random() * wordList.size());
		return wordList.get(randomIndex);
	}
}
