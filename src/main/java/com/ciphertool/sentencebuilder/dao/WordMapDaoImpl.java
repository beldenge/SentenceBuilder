package com.ciphertool.sentencebuilder.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.ciphertool.sentencebuilder.common.PartOfSpeech;
import com.ciphertool.sentencebuilder.entities.Word;

public class WordMapDaoImpl implements WordMapDao {
	
	private static HashMap<PartOfSpeech, ArrayList<Word>> wordMap;
	private WordDao wordDao;
	
	@Autowired
	public WordMapDaoImpl (WordDao wordDao) {
		this.wordDao=wordDao;
		wordMap = this.mapByPartOfSpeech((ArrayList<Word>) this.wordDao.findAll());
	}
	
	@Override
	public Word findRandomWordByPartOfSpeech(PartOfSpeech pos) {
		ArrayList<Word> wordList = wordMap.get(pos);
		int randomIndex = (int)(Math.random() * wordList.size());
		return wordList.get(randomIndex);
	}
	
	private HashMap<PartOfSpeech, ArrayList<Word>> mapByPartOfSpeech(ArrayList<Word> allWords) {
		HashMap<PartOfSpeech, ArrayList<Word>> byPartOfSpeech = new HashMap<PartOfSpeech, ArrayList<Word>>();
		for (Word w : allWords) {
			PartOfSpeech pos = PartOfSpeech.typeOf(w.getWordId().getPartOfSpeech());
			
			//Add the part of speech to the map if it doesn't exist
			if (!byPartOfSpeech.containsKey(pos)) {
				byPartOfSpeech.put(pos, new ArrayList<Word>());
			}
			
			/*
			 * Add the word to the map by reference a number of times equal to the frequency value
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
