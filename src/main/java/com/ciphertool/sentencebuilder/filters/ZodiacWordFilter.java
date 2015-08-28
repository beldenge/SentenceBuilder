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
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.sentencebuilder.enumerations.PartOfSpeechType;

public class ZodiacWordFilter implements WordFilter {

	private static List<Word> blacklistedWords = new ArrayList<Word>();

	static {
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeechType.ADJECTIVE)));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeechType.ADVERB)));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeechType.ARTICLE)));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeechType.CONJUNCTION)));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeechType.INTERJECTION)));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeechType.NOMINATIVE)));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeechType.NONE)));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeechType.NOUN_PHRASE)));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeechType.PLURAL)));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeechType.PRONOUN)));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeechType.VERB_INTRANSITIVE)));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeechType.VERB_PARTICIPLE)));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeechType.VERB_TRANSITIVE)));
		blacklistedWords.add(new Word(new WordId("on", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("on", PartOfSpeechType.ADVERB)));
		blacklistedWords.add(new Word(new WordId("do", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("No", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("be", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeechType.ADJECTIVE)));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeechType.ADVERB)));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeechType.ARTICLE)));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeechType.CONJUNCTION)));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeechType.INTERJECTION)));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeechType.NOMINATIVE)));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeechType.NONE)));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeechType.NOUN_PHRASE)));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeechType.PLURAL)));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeechType.PREPOSITION)));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeechType.PRONOUN)));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeechType.VERB_INTRANSITIVE)));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeechType.VERB_PARTICIPLE)));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeechType.VERB_TRANSITIVE)));
		blacklistedWords.add(new Word(new WordId("so", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("go", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("go", PartOfSpeechType.ADJECTIVE)));
		blacklistedWords.add(new Word(new WordId("up", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("if", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("at", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("as", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("an", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("an", PartOfSpeechType.CONJUNCTION)));
		blacklistedWords.add(new Word(new WordId("or", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("or", PartOfSpeechType.ADJECTIVE)));
		blacklistedWords.add(new Word(new WordId("by", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("by", PartOfSpeechType.ADJECTIVE)));
		blacklistedWords.add(new Word(new WordId("am", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("am", PartOfSpeechType.ADJECTIVE)));
		blacklistedWords.add(new Word(new WordId("Hi", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeechType.ADJECTIVE)));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeechType.ADVERB)));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeechType.ARTICLE)));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeechType.CONJUNCTION)));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeechType.INTERJECTION)));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeechType.NOMINATIVE)));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeechType.NONE)));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeechType.NOUN_PHRASE)));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeechType.PLURAL)));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeechType.PREPOSITION)));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeechType.PRONOUN)));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeechType.VERB_INTRANSITIVE)));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeechType.VERB_PARTICIPLE)));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeechType.VERB_TRANSITIVE)));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeechType.ADJECTIVE)));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeechType.ADVERB)));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeechType.ARTICLE)));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeechType.CONJUNCTION)));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeechType.INTERJECTION)));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeechType.NOMINATIVE)));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeechType.NONE)));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeechType.NOUN_PHRASE)));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeechType.PLURAL)));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeechType.PREPOSITION)));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeechType.PRONOUN)));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeechType.VERB_INTRANSITIVE)));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeechType.VERB_PARTICIPLE)));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeechType.VERB_TRANSITIVE)));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeechType.ADJECTIVE)));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeechType.ADVERB)));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeechType.ARTICLE)));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeechType.CONJUNCTION)));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeechType.INTERJECTION)));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeechType.NOMINATIVE)));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeechType.NONE)));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeechType.NOUN_PHRASE)));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeechType.PLURAL)));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeechType.PREPOSITION)));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeechType.PRONOUN)));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeechType.VERB_INTRANSITIVE)));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeechType.VERB_PARTICIPLE)));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeechType.VERB_TRANSITIVE)));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeechType.ADJECTIVE)));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeechType.ADVERB)));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeechType.ARTICLE)));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeechType.CONJUNCTION)));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeechType.INTERJECTION)));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeechType.NOMINATIVE)));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeechType.NONE)));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeechType.NOUN_PHRASE)));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeechType.PLURAL)));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeechType.PREPOSITION)));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeechType.PRONOUN)));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeechType.VERB_INTRANSITIVE)));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeechType.VERB_PARTICIPLE)));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeechType.VERB_TRANSITIVE)));
		blacklistedWords.add(new Word(new WordId("ha", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("the", PartOfSpeechType.ADVERB)));
		blacklistedWords.add(new Word(new WordId("for", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("and", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("don", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("are", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("get", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("but", PartOfSpeechType.ADVERB)));
		blacklistedWords.add(new Word(new WordId("but", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("out", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("out", PartOfSpeechType.INTERJECTION)));
		blacklistedWords.add(new Word(new WordId("out", PartOfSpeechType.PREPOSITION)));
		blacklistedWords.add(new Word(new WordId("see", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("see", PartOfSpeechType.ADVERB)));
		blacklistedWords.add(new Word(new WordId("Let", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("yes", PartOfSpeechType.ADVERB)));
		blacklistedWords.add(new Word(new WordId("hey", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("say", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("say", PartOfSpeechType.ADVERB)));
		blacklistedWords.add(new Word(new WordId("put", PartOfSpeechType.NOUN)));
		blacklistedWords.add(new Word(new WordId("guy", PartOfSpeechType.NOUN)));
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
		if (word == null || word.getId() == null) {
			return false;
		}

		if (word.getId().getWord().length() < 3) {
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
