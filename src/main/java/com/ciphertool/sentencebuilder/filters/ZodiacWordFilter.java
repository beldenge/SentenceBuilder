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

package com.ciphertool.sentencebuilder.filters;

import java.util.ArrayList;
import java.util.List;

import com.ciphertool.sentencebuilder.common.PartOfSpeech;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;

public class ZodiacWordFilter implements WordFilter {

	private static List<Word> blacklistedWords = new ArrayList<Word>();

	static {
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeech.ADJECTIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeech.ADVERB.getSymbol())));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeech.ARTICLE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeech.CONJUNCTION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeech.INTERJECTION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeech.NOMINATIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeech.NONE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeech.NOUN_PHRASE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeech.PLURAL.getSymbol())));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeech.PRONOUN.getSymbol())));
		blacklistedWords
				.add(new Word(new WordId("in", PartOfSpeech.VERB_INTRANSITIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeech.VERB_PARTICIPLE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("in", PartOfSpeech.VERB_TRANSITIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("on", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("on", PartOfSpeech.ADVERB.getSymbol())));
		blacklistedWords.add(new Word(new WordId("do", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("No", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("be", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeech.ADJECTIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeech.ADVERB.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeech.ARTICLE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeech.CONJUNCTION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeech.INTERJECTION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeech.NOMINATIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeech.NONE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeech.NOUN_PHRASE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeech.PLURAL.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeech.PREPOSITION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeech.PRONOUN.getSymbol())));
		blacklistedWords
				.add(new Word(new WordId("ll", PartOfSpeech.VERB_INTRANSITIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeech.VERB_PARTICIPLE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ll", PartOfSpeech.VERB_TRANSITIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("so", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("go", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("go", PartOfSpeech.ADJECTIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("up", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("if", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("at", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("as", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("an", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("an", PartOfSpeech.CONJUNCTION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("or", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("or", PartOfSpeech.ADJECTIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("by", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("by", PartOfSpeech.ADJECTIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("am", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("am", PartOfSpeech.ADJECTIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("Hi", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeech.ADJECTIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeech.ADVERB.getSymbol())));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeech.ARTICLE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeech.CONJUNCTION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeech.INTERJECTION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeech.NOMINATIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeech.NONE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeech.NOUN_PHRASE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeech.PLURAL.getSymbol())));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeech.PREPOSITION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeech.PRONOUN.getSymbol())));
		blacklistedWords
				.add(new Word(new WordId("em", PartOfSpeech.VERB_INTRANSITIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeech.VERB_PARTICIPLE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("em", PartOfSpeech.VERB_TRANSITIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeech.ADJECTIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeech.ADVERB.getSymbol())));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeech.ARTICLE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeech.CONJUNCTION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeech.INTERJECTION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeech.NOMINATIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeech.NONE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeech.NOUN_PHRASE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeech.PLURAL.getSymbol())));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeech.PREPOSITION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeech.PRONOUN.getSymbol())));
		blacklistedWords
				.add(new Word(new WordId("Ah", PartOfSpeech.VERB_INTRANSITIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeech.VERB_PARTICIPLE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("Ah", PartOfSpeech.VERB_TRANSITIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeech.ADJECTIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeech.ADVERB.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeech.ARTICLE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeech.CONJUNCTION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeech.INTERJECTION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeech.NOMINATIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeech.NONE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeech.NOUN_PHRASE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeech.PLURAL.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeech.PREPOSITION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeech.PRONOUN.getSymbol())));
		blacklistedWords
				.add(new Word(new WordId("ma", PartOfSpeech.VERB_INTRANSITIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeech.VERB_PARTICIPLE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ma", PartOfSpeech.VERB_TRANSITIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeech.ADJECTIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeech.ADVERB.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeech.ARTICLE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeech.CONJUNCTION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeech.INTERJECTION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeech.NOMINATIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeech.NONE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeech.NOUN_PHRASE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeech.PLURAL.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeech.PREPOSITION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeech.PRONOUN.getSymbol())));
		blacklistedWords
				.add(new Word(new WordId("ya", PartOfSpeech.VERB_INTRANSITIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeech.VERB_PARTICIPLE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ya", PartOfSpeech.VERB_TRANSITIVE.getSymbol())));
		blacklistedWords.add(new Word(new WordId("ha", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("the", PartOfSpeech.ADVERB.getSymbol())));
		blacklistedWords.add(new Word(new WordId("for", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("and", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("don", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("are", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("get", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("but", PartOfSpeech.ADVERB.getSymbol())));
		blacklistedWords.add(new Word(new WordId("but", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("out", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("out", PartOfSpeech.INTERJECTION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("out", PartOfSpeech.PREPOSITION.getSymbol())));
		blacklistedWords.add(new Word(new WordId("see", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("see", PartOfSpeech.ADVERB.getSymbol())));
		blacklistedWords.add(new Word(new WordId("Let", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("yes", PartOfSpeech.ADVERB.getSymbol())));
		blacklistedWords.add(new Word(new WordId("hey", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("say", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("say", PartOfSpeech.ADVERB.getSymbol())));
		blacklistedWords.add(new Word(new WordId("put", PartOfSpeech.NOUN.getSymbol())));
		blacklistedWords.add(new Word(new WordId("guy", PartOfSpeech.NOUN.getSymbol())));
	}

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
	 * This searches through a huge List of Words and returns whether the
	 * specified Word is blacklisted.
	 * 
	 * @param word
	 *            the Word to check
	 * @return whether this Word is blacklisted
	 */
	private boolean isBlacklisted(Word word) {
		return ZodiacWordFilter.blacklistedWords.contains(word);
	}
}
