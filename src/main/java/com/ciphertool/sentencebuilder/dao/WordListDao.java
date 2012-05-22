package com.ciphertool.sentencebuilder.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.ciphertool.sentencebuilder.entities.Word;

public class WordListDao {

	private ArrayList<Word> wordList;
	private WordDao wordDao;

	@Autowired
	public WordListDao(WordDao wordDao) {
		this.wordDao = wordDao;
		wordList = (ArrayList<Word>) this.wordDao.findAll();
	}

	public Word findRandomWord() {
		int randomIndex = (int) (Math.random() * wordList.size());
		return wordList.get(randomIndex);
	}
}
