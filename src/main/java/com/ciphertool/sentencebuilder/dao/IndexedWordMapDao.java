package com.ciphertool.sentencebuilder.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.ciphertool.sentencebuilder.common.PartOfSpeech;
import com.ciphertool.sentencebuilder.entities.Word;

/**
 * This is a lower memory utilization implementation of WordMapDao.  It builds an index map using int, which is 32-bit, instead of the 64-bit reference needed to hold each object reference.
 * 
 * @author george
 */
public class IndexedWordMapDao implements WordMapDao {
	
	private static HashMap<PartOfSpeech, ArrayList<Word>> wordMap;
	private static HashMap<PartOfSpeech, int []> frequencyMap;
	private WordDao wordDao;
	
	@Autowired
	public IndexedWordMapDao (WordDao wordDao) {
		this.wordDao=wordDao;
		wordMap = this.mapByPartOfSpeech((ArrayList<Word>) this.wordDao.findAll());
		frequencyMap = buildIndexedFrequencyMap(wordMap);
	}
	
	@Override
	public Word findRandomWordByPartOfSpeech(PartOfSpeech pos) {
		int [] indexList = frequencyMap.get(pos);
		
		/*
		 * This returns a pseudorandom number which is bounded by the ArrayList's size - 1, which is perfect since the elements are zero-indexed
		 */
		int randomIndex = (int)(Math.random() * indexList.length);
		
		int selectedIndex = indexList [randomIndex];
		
		ArrayList<Word> wordList = wordMap.get(pos);
		
		return wordList.get(selectedIndex);
	}
	
	private HashMap<PartOfSpeech, ArrayList<Word>> mapByPartOfSpeech(ArrayList<Word> allWords) {
		 HashMap<PartOfSpeech, ArrayList<Word>> byPartOfSpeech = new HashMap<PartOfSpeech, ArrayList<Word>>();
		
		for (Word w : allWords) {
			PartOfSpeech pos = PartOfSpeech.typeOf(w.getWordId().getPartOfSpeech());
			
			//Add the part of speech to the map if it doesn't exist
			if (!byPartOfSpeech.containsKey(pos)) {
				byPartOfSpeech.put(pos, new ArrayList<Word>());
			}
			
			byPartOfSpeech.get(pos).add(w);
		}
		
		return byPartOfSpeech;
	}

	/*
	 * Add the word to the map by reference a number of times equal to the frequency value
	 * 
	 * TODO: We can probably reduce memory utilization even more if we combine the words into one ArrayList not separated by part of speech, and then only separate the index HashMap by parts of speech
	 */
	private HashMap<PartOfSpeech, int []> buildIndexedFrequencyMap(HashMap<PartOfSpeech, ArrayList<Word>> byPartOfSpeech) {
		HashMap<PartOfSpeech, int []> byFrequency = new HashMap<PartOfSpeech, int []>();
		 
		int frequencySum;
		
		/*
		 *  Loop through each pos, add up the frequencyWeights, and create an int array of the resulting length
		 */
		for (PartOfSpeech pos : byPartOfSpeech.keySet()) {
			frequencySum = 0;
			
			for (Word w : byPartOfSpeech.get(pos)) {
				frequencySum += w.getFrequencyWeight();
			}
			
			byFrequency.put(pos, new int [frequencySum]);
		}
		
		int frequencyIndex;
		int wordIndex;
		
		/*
		 *  Loop through each pos and word, and add the index to the frequencyMap a number of times equal to the word's frequency weight
		 */
		for (PartOfSpeech pos : byPartOfSpeech.keySet()) {
			frequencyIndex = 0;
			wordIndex = 0;
			
			for (Word w : byPartOfSpeech.get(pos)) {
				for (int i = 0; i < w.getFrequencyWeight(); i++) {
					byFrequency.get(pos) [frequencyIndex] = wordIndex;
					
					frequencyIndex ++;
				}
				wordIndex ++;
			}
		}
		
		return byFrequency;
	}

	/**
	 * @return the wordMap
	 */
	@Override
	public HashMap<PartOfSpeech, ArrayList<Word>> getWordMap() {
		return wordMap;
	}
}
