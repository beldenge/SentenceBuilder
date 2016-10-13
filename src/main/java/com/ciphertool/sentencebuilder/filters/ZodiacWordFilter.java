/**
 * Copyright 2015 George Belden
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

package com.ciphertool.sentencebuilder.filters;

import java.util.ArrayList;
import java.util.List;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.enumerations.PartOfSpeechType;

public class ZodiacWordFilter implements WordFilter {

	private static List<Word> blacklistedWords = new ArrayList<Word>();

	static {
		blacklistedWords.add(new Word("in", PartOfSpeechType.ADJECTIVE));
		blacklistedWords.add(new Word("in", PartOfSpeechType.ADVERB));
		blacklistedWords.add(new Word("in", PartOfSpeechType.ARTICLE));
		blacklistedWords.add(new Word("in", PartOfSpeechType.CONJUNCTION));
		blacklistedWords.add(new Word("in", PartOfSpeechType.INTERJECTION));
		blacklistedWords.add(new Word("in", PartOfSpeechType.NOMINATIVE));
		blacklistedWords.add(new Word("in", PartOfSpeechType.NONE));
		blacklistedWords.add(new Word("in", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("in", PartOfSpeechType.NOUN_PHRASE));
		blacklistedWords.add(new Word("in", PartOfSpeechType.PLURAL));
		blacklistedWords.add(new Word("in", PartOfSpeechType.PRONOUN));
		blacklistedWords.add(new Word("in", PartOfSpeechType.VERB_INTRANSITIVE));
		blacklistedWords.add(new Word("in", PartOfSpeechType.VERB_PARTICIPLE));
		blacklistedWords.add(new Word("in", PartOfSpeechType.VERB_TRANSITIVE));
		blacklistedWords.add(new Word("on", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("on", PartOfSpeechType.ADVERB));
		blacklistedWords.add(new Word("do", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("No", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("be", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("ll", PartOfSpeechType.ADJECTIVE));
		blacklistedWords.add(new Word("ll", PartOfSpeechType.ADVERB));
		blacklistedWords.add(new Word("ll", PartOfSpeechType.ARTICLE));
		blacklistedWords.add(new Word("ll", PartOfSpeechType.CONJUNCTION));
		blacklistedWords.add(new Word("ll", PartOfSpeechType.INTERJECTION));
		blacklistedWords.add(new Word("ll", PartOfSpeechType.NOMINATIVE));
		blacklistedWords.add(new Word("ll", PartOfSpeechType.NONE));
		blacklistedWords.add(new Word("ll", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("ll", PartOfSpeechType.NOUN_PHRASE));
		blacklistedWords.add(new Word("ll", PartOfSpeechType.PLURAL));
		blacklistedWords.add(new Word("ll", PartOfSpeechType.PREPOSITION));
		blacklistedWords.add(new Word("ll", PartOfSpeechType.PRONOUN));
		blacklistedWords.add(new Word("ll", PartOfSpeechType.VERB_INTRANSITIVE));
		blacklistedWords.add(new Word("ll", PartOfSpeechType.VERB_PARTICIPLE));
		blacklistedWords.add(new Word("ll", PartOfSpeechType.VERB_TRANSITIVE));
		blacklistedWords.add(new Word("so", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("go", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("go", PartOfSpeechType.ADJECTIVE));
		blacklistedWords.add(new Word("up", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("if", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("at", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("as", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("an", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("an", PartOfSpeechType.CONJUNCTION));
		blacklistedWords.add(new Word("or", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("or", PartOfSpeechType.ADJECTIVE));
		blacklistedWords.add(new Word("by", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("by", PartOfSpeechType.ADJECTIVE));
		blacklistedWords.add(new Word("am", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("am", PartOfSpeechType.ADJECTIVE));
		blacklistedWords.add(new Word("Hi", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("em", PartOfSpeechType.ADJECTIVE));
		blacklistedWords.add(new Word("em", PartOfSpeechType.ADVERB));
		blacklistedWords.add(new Word("em", PartOfSpeechType.ARTICLE));
		blacklistedWords.add(new Word("em", PartOfSpeechType.CONJUNCTION));
		blacklistedWords.add(new Word("em", PartOfSpeechType.INTERJECTION));
		blacklistedWords.add(new Word("em", PartOfSpeechType.NOMINATIVE));
		blacklistedWords.add(new Word("em", PartOfSpeechType.NONE));
		blacklistedWords.add(new Word("em", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("em", PartOfSpeechType.NOUN_PHRASE));
		blacklistedWords.add(new Word("em", PartOfSpeechType.PLURAL));
		blacklistedWords.add(new Word("em", PartOfSpeechType.PREPOSITION));
		blacklistedWords.add(new Word("em", PartOfSpeechType.PRONOUN));
		blacklistedWords.add(new Word("em", PartOfSpeechType.VERB_INTRANSITIVE));
		blacklistedWords.add(new Word("em", PartOfSpeechType.VERB_PARTICIPLE));
		blacklistedWords.add(new Word("em", PartOfSpeechType.VERB_TRANSITIVE));
		blacklistedWords.add(new Word("Ah", PartOfSpeechType.ADJECTIVE));
		blacklistedWords.add(new Word("Ah", PartOfSpeechType.ADVERB));
		blacklistedWords.add(new Word("Ah", PartOfSpeechType.ARTICLE));
		blacklistedWords.add(new Word("Ah", PartOfSpeechType.CONJUNCTION));
		blacklistedWords.add(new Word("Ah", PartOfSpeechType.INTERJECTION));
		blacklistedWords.add(new Word("Ah", PartOfSpeechType.NOMINATIVE));
		blacklistedWords.add(new Word("Ah", PartOfSpeechType.NONE));
		blacklistedWords.add(new Word("Ah", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("Ah", PartOfSpeechType.NOUN_PHRASE));
		blacklistedWords.add(new Word("Ah", PartOfSpeechType.PLURAL));
		blacklistedWords.add(new Word("Ah", PartOfSpeechType.PREPOSITION));
		blacklistedWords.add(new Word("Ah", PartOfSpeechType.PRONOUN));
		blacklistedWords.add(new Word("Ah", PartOfSpeechType.VERB_INTRANSITIVE));
		blacklistedWords.add(new Word("Ah", PartOfSpeechType.VERB_PARTICIPLE));
		blacklistedWords.add(new Word("Ah", PartOfSpeechType.VERB_TRANSITIVE));
		blacklistedWords.add(new Word("ma", PartOfSpeechType.ADJECTIVE));
		blacklistedWords.add(new Word("ma", PartOfSpeechType.ADVERB));
		blacklistedWords.add(new Word("ma", PartOfSpeechType.ARTICLE));
		blacklistedWords.add(new Word("ma", PartOfSpeechType.CONJUNCTION));
		blacklistedWords.add(new Word("ma", PartOfSpeechType.INTERJECTION));
		blacklistedWords.add(new Word("ma", PartOfSpeechType.NOMINATIVE));
		blacklistedWords.add(new Word("ma", PartOfSpeechType.NONE));
		blacklistedWords.add(new Word("ma", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("ma", PartOfSpeechType.NOUN_PHRASE));
		blacklistedWords.add(new Word("ma", PartOfSpeechType.PLURAL));
		blacklistedWords.add(new Word("ma", PartOfSpeechType.PREPOSITION));
		blacklistedWords.add(new Word("ma", PartOfSpeechType.PRONOUN));
		blacklistedWords.add(new Word("ma", PartOfSpeechType.VERB_INTRANSITIVE));
		blacklistedWords.add(new Word("ma", PartOfSpeechType.VERB_PARTICIPLE));
		blacklistedWords.add(new Word("ma", PartOfSpeechType.VERB_TRANSITIVE));
		blacklistedWords.add(new Word("ya", PartOfSpeechType.ADJECTIVE));
		blacklistedWords.add(new Word("ya", PartOfSpeechType.ADVERB));
		blacklistedWords.add(new Word("ya", PartOfSpeechType.ARTICLE));
		blacklistedWords.add(new Word("ya", PartOfSpeechType.CONJUNCTION));
		blacklistedWords.add(new Word("ya", PartOfSpeechType.INTERJECTION));
		blacklistedWords.add(new Word("ya", PartOfSpeechType.NOMINATIVE));
		blacklistedWords.add(new Word("ya", PartOfSpeechType.NONE));
		blacklistedWords.add(new Word("ya", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("ya", PartOfSpeechType.NOUN_PHRASE));
		blacklistedWords.add(new Word("ya", PartOfSpeechType.PLURAL));
		blacklistedWords.add(new Word("ya", PartOfSpeechType.PREPOSITION));
		blacklistedWords.add(new Word("ya", PartOfSpeechType.PRONOUN));
		blacklistedWords.add(new Word("ya", PartOfSpeechType.VERB_INTRANSITIVE));
		blacklistedWords.add(new Word("ya", PartOfSpeechType.VERB_PARTICIPLE));
		blacklistedWords.add(new Word("ya", PartOfSpeechType.VERB_TRANSITIVE));
		blacklistedWords.add(new Word("ha", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("the", PartOfSpeechType.ADVERB));
		blacklistedWords.add(new Word("for", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("and", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("don", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("are", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("get", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("but", PartOfSpeechType.ADVERB));
		blacklistedWords.add(new Word("but", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("out", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("out", PartOfSpeechType.INTERJECTION));
		blacklistedWords.add(new Word("out", PartOfSpeechType.PREPOSITION));
		blacklistedWords.add(new Word("see", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("see", PartOfSpeechType.ADVERB));
		blacklistedWords.add(new Word("Let", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("yes", PartOfSpeechType.ADVERB));
		blacklistedWords.add(new Word("hey", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("say", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("say", PartOfSpeechType.ADVERB));
		blacklistedWords.add(new Word("put", PartOfSpeechType.NOUN));
		blacklistedWords.add(new Word("guy", PartOfSpeechType.NOUN));
	}

	/*
	 * The number 6000 is not arbitrary. The cut-off was chosen for the exclamation word "ha" which has a frequency
	 * weight of 6260. There are several words with a lower frequency which might be interpreted as correct, but they
	 * are negligible for the purposes of this cipher.
	 */
	private final static int FREQUENCY_THRESHOLD = 6000;

	/*
	 * The purpose of the conditions on word length and frequency is to reduce overhead of checking the expensive
	 * isBlacklisted() method when it's not necessary.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.sentencebuilder.common.WordFilter#filter(com.ciphertool .sentencebuilder.entities.Word)
	 */
	public boolean filter(Word word) {
		if (word == null || word.getWord() == null) {
			return false;
		}

		if (word.getWord().length() < 3) {
			if (word.getFrequencyWeight() < FREQUENCY_THRESHOLD) {
				return false;
			}

			if (isBlacklisted(word)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * This searches through a huge List of Words and returns whether the specified Word is blacklisted.
	 * 
	 * @param word
	 *            the Word to check
	 * @return whether this Word is blacklisted
	 */
	private boolean isBlacklisted(Word word) {
		return ZodiacWordFilter.blacklistedWords.contains(word);
	}
}
