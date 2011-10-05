package com.ciphertool.sentencebuilder.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ciphertool.sentencebuilder.beans.Word;
import com.ciphertool.sentencebuilder.common.PartOfSpeech;

//TODO: Should really make a DAOFactory so that the expensive findAll() method does not need to be called over and over again
@Component
public class WordDAO {
	
	private HashMap<PartOfSpeech, ArrayList<Word>> wordMap;
	private WordListDAO wordListDAO;
	
	@Autowired
	public WordDAO (WordListDAO wordListDAO) {
		this.wordListDAO=wordListDAO;
		wordMap = this.wordListDAO.sortByPartOfSpeech((ArrayList<Word>) this.wordListDAO.findAll());
	}
	
	public Word findRandomWordByPartOfSpeech(PartOfSpeech pos) {
		ArrayList<Word> wordList = wordMap.get(pos);
		int randomIndex = (int)(Math.random() * wordList.size());
		return wordList.get(randomIndex);
	}
}
