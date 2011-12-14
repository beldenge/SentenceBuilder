package com.ciphertool.sentencebuilder.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ciphertool.sentencebuilder.common.PartOfSpeech;
import com.ciphertool.sentencebuilder.entities.Word;

//TODO: Should possibly make a DAOFactory in case the expensive findAll() method is called over and over again
@Component
public class WordMapDao {
	
	private HashMap<PartOfSpeech, ArrayList<Word>> wordMap;
	private WordDao wordDao;
	
	@Autowired
	public WordMapDao (WordDao wordDao) {
		this.wordDao=wordDao;
		wordMap = this.mapByPartOfSpeech((ArrayList<Word>) this.wordDao.findAll());
	}
	
	public Word findRandomWordByPartOfSpeech(PartOfSpeech pos) {
		ArrayList<Word> wordList = wordMap.get(pos);
		int randomIndex = (int)(Math.random() * wordList.size());
		return wordList.get(randomIndex);
	}
	
	public HashMap<PartOfSpeech, ArrayList<Word>> mapByPartOfSpeech(ArrayList<Word> allWords) {
		HashMap<PartOfSpeech, ArrayList<Word>> byPartOfSpeech = new HashMap<PartOfSpeech, ArrayList<Word>>();
		for (Word w : allWords) {
			PartOfSpeech pos = PartOfSpeech.typeOf(w.getWordId().getPartOfSpeech());
			
			//Add the part of speech to the map if it doesn't exist
			if (!byPartOfSpeech.containsKey(pos)) {
				byPartOfSpeech.put(pos, new ArrayList<Word>());
				
			}
			
			//Add the word by reference a number of times equal to the frequency weight from the database
			for (long i = 0; i < w.getFrequencyWeight(); i++) {
				byPartOfSpeech.get(pos).add(w);
			}
		}
		return byPartOfSpeech;
	}
}
