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

package com.ciphertool.sentencebuilder.common;

import com.ciphertool.sentencebuilder.entities.Word;

public class ZodiacWordFilter implements WordFilter {

	/*
	 * The number 6000 is not arbitrary. The cut-off was chosen for the
	 * exclamation word "ha" which has a frequency weight of 6260. There are
	 * several words with a lower frequency which might be interpreted as
	 * correct, but they are negligible for the purposes of this cipher.
	 */
	private final static int FREQUENCY_THRESHOLD = 6000;

	/*
	 * The purpose of the conditions on word length and frequency is to reduce
	 * overhead of checking the expensive isBlacklisted() method when it's not
	 * necessary.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ciphertool.sentencebuilder.common.WordFilter#filter(com.ciphertool
	 * .sentencebuilder.entities.Word)
	 */
	public boolean filter(Word word) {
		if ((word.getId().getWord().length() < 3)
				&& ((word.getFrequencyWeight() < FREQUENCY_THRESHOLD) || isBlacklisted(word))) {
			return false;
		}

		return true;
	}

	/**
	 * This goes through a huge list of if/else if checks to sort out
	 * blacklisted words. There has got to be a more elegant way to accomplish
	 * this.
	 * 
	 * @param word
	 * @return
	 */
	private boolean isBlacklisted(Word word) {
		PartOfSpeech pos = PartOfSpeech.typeOf(word.getId().getPartOfSpeech());
		String letters = word.getId().getWord();
		if (word.getId().getWord().length() < 3) {
			if (letters.equalsIgnoreCase("in") && pos != PartOfSpeech.PREPOSITION) {
				return true;
			} else if (letters.equalsIgnoreCase("on")
					&& (pos == PartOfSpeech.NOUN || pos == PartOfSpeech.ADVERB)) {
				return true;
			} else if (letters.equalsIgnoreCase("do") && pos == PartOfSpeech.NOUN) {
				return true;
			} else if (letters.equalsIgnoreCase("No") && pos == PartOfSpeech.NOUN) {
				return true;
			} else if (letters.equalsIgnoreCase("be") && pos == PartOfSpeech.NOUN) {
				return true;
			} else if (letters.equalsIgnoreCase("ll")) {
				return true;
			} else if (letters.equalsIgnoreCase("so") && pos == PartOfSpeech.NOUN) {
				return true;
			} else if (letters.equalsIgnoreCase("go")
					&& (pos == PartOfSpeech.NOUN || pos == PartOfSpeech.ADJECTIVE)) {
				return true;
			} else if (letters.equalsIgnoreCase("up") && (pos == PartOfSpeech.NOUN)) {
				return true;
			} else if (letters.equalsIgnoreCase("if") && (pos == PartOfSpeech.NOUN)) {
				return true;
			} else if (letters.equalsIgnoreCase("at") && (pos == PartOfSpeech.NOUN)) {
				return true;
			} else if (letters.equalsIgnoreCase("as") && (pos == PartOfSpeech.NOUN)) {
				return true;
			} else if (letters.equalsIgnoreCase("an")
					&& (pos == PartOfSpeech.NOUN || pos == PartOfSpeech.CONJUNCTION)) {
				return true;
			} else if (letters.equalsIgnoreCase("or")
					&& (pos == PartOfSpeech.NOUN || pos == PartOfSpeech.ADJECTIVE)) {
				return true;
			} else if (letters.equalsIgnoreCase("by")
					&& (pos == PartOfSpeech.NOUN || pos == PartOfSpeech.ADJECTIVE)) {
				return true;
			} else if (letters.equalsIgnoreCase("am")
					&& (pos == PartOfSpeech.NOUN || pos == PartOfSpeech.ADJECTIVE)) {
				return true;
			} else if (letters.equalsIgnoreCase("Hi") && (pos == PartOfSpeech.NOUN)) {
				return true;
			} else if (letters.equalsIgnoreCase("em")) {
				return true;
			} else if (letters.equalsIgnoreCase("Ah")) {
				return true;
			} else if (letters.equalsIgnoreCase("ma")) {
				return true;
			} else if (letters.equalsIgnoreCase("ya")) {
				return true;
			} else if (letters.equalsIgnoreCase("ha") && (pos == PartOfSpeech.NOUN)) {
				return true;
			}
		} else if (word.getId().getWord().length() == 3) {
			if (letters.equalsIgnoreCase("the") && (pos == PartOfSpeech.ADVERB)) {
				return true;
			} else if (letters.equalsIgnoreCase("for") && (pos == PartOfSpeech.NOUN)) {
				return true;
			} else if (letters.equalsIgnoreCase("and") && (pos == PartOfSpeech.NOUN)) {
				return true;
			} else if (letters.equalsIgnoreCase("don") && (pos == PartOfSpeech.NOUN)) {
				return true;
			} else if (letters.equalsIgnoreCase("are") && (pos == PartOfSpeech.NOUN)) {
				return true;
			} else if (letters.equalsIgnoreCase("get") && (pos == PartOfSpeech.NOUN)) {
				return true;
			} else if (letters.equalsIgnoreCase("but")
					&& (pos == PartOfSpeech.NOUN || pos == PartOfSpeech.ADVERB)) {
				return true;
			} else if (letters.equalsIgnoreCase("out")
					&& (pos != PartOfSpeech.NOUN && pos != PartOfSpeech.INTERJECTION && pos != PartOfSpeech.PREPOSITION)) {
				return true;
			} else if (letters.equalsIgnoreCase("see")
					&& (pos == PartOfSpeech.NOUN || pos == PartOfSpeech.ADVERB)) {
				return true;
			} else if (letters.equalsIgnoreCase("Let") && (pos == PartOfSpeech.NOUN)) {
				return true;
			} else if (letters.equalsIgnoreCase("yes") && (pos == PartOfSpeech.ADVERB)) {
				return true;
			} else if (letters.equalsIgnoreCase("hey") && (pos == PartOfSpeech.NOUN)) {
				return true;
			} else if (letters.equalsIgnoreCase("say")
					&& (pos == PartOfSpeech.NOUN || pos == PartOfSpeech.ADVERB)) {
				return true;
			} else if (letters.equalsIgnoreCase("put") && (pos == PartOfSpeech.NOUN)) {
				return true;
			} else if (letters.equalsIgnoreCase("guy") && (pos != PartOfSpeech.NOUN)) {
				return true;
			}
		}

		return false;
	}
}
