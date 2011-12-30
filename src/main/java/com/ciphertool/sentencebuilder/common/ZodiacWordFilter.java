package com.ciphertool.sentencebuilder.common;

import com.ciphertool.sentencebuilder.entities.Word;

public class ZodiacWordFilter implements WordFilter {

	/*
	 * The number 6000 is not arbitrary.  The cut-off was chosen for the exclamation word "ha" which has a frequency weight of 6260.  
	 * There are several words with a lower frequency which might be interpreted as correct, but they are negligible for the purposes of this cipher.
	 */
	private final static int FREQUENCY_THRESHOLD = 6000;
	
	/*
	 * The purpose of the conditions on word length and frequency is to reduce overhead of checking the expensive isBlacklisted() method when it's not necessary.
	 * 
	 * (non-Javadoc)
	 * @see com.ciphertool.sentencebuilder.common.WordFilter#filter(com.ciphertool.sentencebuilder.entities.Word)
	 */
	public boolean filter(Word word) {
		if ((word.getWordId().getWord().length() < 3) && ((word.getFrequencyWeight() < FREQUENCY_THRESHOLD) || isBlacklisted(word))) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * This goes through a huge list of if/else if checks to sort out blacklisted words.  There has got to be a more elegant way to accomplish this. 
	 * 
	 * @param word
	 * @return
	 */
	private boolean isBlacklisted(Word word) {
		PartOfSpeech pos = PartOfSpeech.typeOf(word.getWordId().getPartOfSpeech());
		String letters = word.getWordId().getWord();
		if (word.getWordId().getWord().length() < 3) {
			if (letters.equalsIgnoreCase("in") && pos != PartOfSpeech.PREPOSITION) {
				return true;
			}
			else if (letters.equalsIgnoreCase("on") && (pos == PartOfSpeech.NOUN || pos == PartOfSpeech.ADVERB)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("do") && pos == PartOfSpeech.NOUN) {
				return true;
			}
			else if (letters.equalsIgnoreCase("No") && pos == PartOfSpeech.NOUN) {
				return true;
			}
			else if (letters.equalsIgnoreCase("be") && pos == PartOfSpeech.NOUN) {
				return true;
			}
			else if (letters.equalsIgnoreCase("ll")) {
				return true;
			}
			else if (letters.equalsIgnoreCase("so") && pos == PartOfSpeech.NOUN) {
				return true;
			}
			else if (letters.equalsIgnoreCase("go") && (pos == PartOfSpeech.NOUN || pos == PartOfSpeech.ADJECTIVE)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("up") && (pos == PartOfSpeech.NOUN)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("if") && (pos == PartOfSpeech.NOUN)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("at") && (pos == PartOfSpeech.NOUN)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("as") && (pos == PartOfSpeech.NOUN)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("an") && (pos == PartOfSpeech.NOUN || pos == PartOfSpeech.CONJUNCTION)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("or") && (pos == PartOfSpeech.NOUN || pos == PartOfSpeech.ADJECTIVE)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("by") && (pos == PartOfSpeech.NOUN || pos == PartOfSpeech.ADJECTIVE)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("am") && (pos == PartOfSpeech.NOUN || pos == PartOfSpeech.ADJECTIVE)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("Hi") && (pos == PartOfSpeech.NOUN)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("em")) {
				return true;
			}
			else if (letters.equalsIgnoreCase("Ah")) {
				return true;
			}
			else if (letters.equalsIgnoreCase("ma")) {
				return true;
			}
			else if (letters.equalsIgnoreCase("ya")) {
				return true;
			}
			else if (letters.equalsIgnoreCase("ha") && (pos == PartOfSpeech.NOUN)) {
				return true;
			}
		}
		else if (word.getWordId().getWord().length() == 3) {
			if (letters.equalsIgnoreCase("the") && (pos == PartOfSpeech.ADVERB)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("for") && (pos == PartOfSpeech.NOUN)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("and") && (pos == PartOfSpeech.NOUN)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("don") && (pos == PartOfSpeech.NOUN)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("are") && (pos == PartOfSpeech.NOUN)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("get") && (pos == PartOfSpeech.NOUN)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("but") && (pos == PartOfSpeech.NOUN || pos == PartOfSpeech.ADVERB)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("out") && (pos != PartOfSpeech.NOUN && pos != PartOfSpeech.INTERJECTION && pos != PartOfSpeech.PREPOSITION)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("see") && (pos == PartOfSpeech.NOUN || pos == PartOfSpeech.ADVERB)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("Let") && (pos == PartOfSpeech.NOUN)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("yes") && (pos == PartOfSpeech.ADVERB)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("hey") && (pos == PartOfSpeech.NOUN)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("say") && (pos == PartOfSpeech.NOUN || pos == PartOfSpeech.ADVERB)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("put") && (pos == PartOfSpeech.NOUN)) {
				return true;
			}
			else if (letters.equalsIgnoreCase("guy") && (pos != PartOfSpeech.NOUN)) {
				return true;
			}
		}
		
		return false;
	}
}
