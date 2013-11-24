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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PartOfSpeechTest {
	@Test
	public void testGetSymbol() {
		assertEquals('N', PartOfSpeech.NOUN.getSymbol());
		assertEquals('p', PartOfSpeech.PLURAL.getSymbol());
		assertEquals('h', PartOfSpeech.NOUN_PHRASE.getSymbol());
		assertEquals('V', PartOfSpeech.VERB_PARTICIPLE.getSymbol());
		assertEquals('t', PartOfSpeech.VERB_TRANSITIVE.getSymbol());
		assertEquals('i', PartOfSpeech.VERB_INTRANSITIVE.getSymbol());
		assertEquals('A', PartOfSpeech.ADJECTIVE.getSymbol());
		assertEquals('v', PartOfSpeech.ADVERB.getSymbol());
		assertEquals('C', PartOfSpeech.CONJUNCTION.getSymbol());
		assertEquals('P', PartOfSpeech.PREPOSITION.getSymbol());
		assertEquals('!', PartOfSpeech.INTERJECTION.getSymbol());
		assertEquals('r', PartOfSpeech.PRONOUN.getSymbol());
		assertEquals('D', PartOfSpeech.ARTICLE.getSymbol());
		assertEquals('o', PartOfSpeech.NOMINATIVE.getSymbol());
		assertEquals('X', PartOfSpeech.NONE.getSymbol());
	}

	@Test
	public void testGetValue() {
		assertEquals(PartOfSpeech.NOUN, PartOfSpeech.getValueFromSymbol('N'));
		assertEquals(PartOfSpeech.PLURAL, PartOfSpeech.getValueFromSymbol('p'));
		assertEquals(PartOfSpeech.NOUN_PHRASE, PartOfSpeech.getValueFromSymbol('h'));
		assertEquals(PartOfSpeech.VERB_PARTICIPLE, PartOfSpeech.getValueFromSymbol('V'));
		assertEquals(PartOfSpeech.VERB_TRANSITIVE, PartOfSpeech.getValueFromSymbol('t'));
		assertEquals(PartOfSpeech.VERB_INTRANSITIVE, PartOfSpeech.getValueFromSymbol('i'));
		assertEquals(PartOfSpeech.ADJECTIVE, PartOfSpeech.getValueFromSymbol('A'));
		assertEquals(PartOfSpeech.ADVERB, PartOfSpeech.getValueFromSymbol('v'));
		assertEquals(PartOfSpeech.CONJUNCTION, PartOfSpeech.getValueFromSymbol('C'));
		assertEquals(PartOfSpeech.PREPOSITION, PartOfSpeech.getValueFromSymbol('P'));
		assertEquals(PartOfSpeech.INTERJECTION, PartOfSpeech.getValueFromSymbol('!'));
		assertEquals(PartOfSpeech.PRONOUN, PartOfSpeech.getValueFromSymbol('r'));
		assertEquals(PartOfSpeech.ARTICLE, PartOfSpeech.getValueFromSymbol('D'));
		assertEquals(PartOfSpeech.NOMINATIVE, PartOfSpeech.getValueFromSymbol('o'));
		assertEquals(PartOfSpeech.NONE, PartOfSpeech.getValueFromSymbol('X'));
	}
}
