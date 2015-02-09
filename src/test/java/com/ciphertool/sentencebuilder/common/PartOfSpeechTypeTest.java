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

package com.ciphertool.sentencebuilder.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PartOfSpeechTypeTest {
	@Test
	public void testGetSymbol() {
		assertEquals('N', PartOfSpeechType.NOUN.getSymbol());
		assertEquals('p', PartOfSpeechType.PLURAL.getSymbol());
		assertEquals('h', PartOfSpeechType.NOUN_PHRASE.getSymbol());
		assertEquals('V', PartOfSpeechType.VERB_PARTICIPLE.getSymbol());
		assertEquals('t', PartOfSpeechType.VERB_TRANSITIVE.getSymbol());
		assertEquals('i', PartOfSpeechType.VERB_INTRANSITIVE.getSymbol());
		assertEquals('A', PartOfSpeechType.ADJECTIVE.getSymbol());
		assertEquals('v', PartOfSpeechType.ADVERB.getSymbol());
		assertEquals('C', PartOfSpeechType.CONJUNCTION.getSymbol());
		assertEquals('P', PartOfSpeechType.PREPOSITION.getSymbol());
		assertEquals('!', PartOfSpeechType.INTERJECTION.getSymbol());
		assertEquals('r', PartOfSpeechType.PRONOUN.getSymbol());
		assertEquals('D', PartOfSpeechType.ARTICLE.getSymbol());
		assertEquals('o', PartOfSpeechType.NOMINATIVE.getSymbol());
		assertEquals('X', PartOfSpeechType.NONE.getSymbol());
	}

	@Test
	public void testGetValue() {
		assertEquals(PartOfSpeechType.NOUN, PartOfSpeechType.getValueFromSymbol('N'));
		assertEquals(PartOfSpeechType.PLURAL, PartOfSpeechType.getValueFromSymbol('p'));
		assertEquals(PartOfSpeechType.NOUN_PHRASE, PartOfSpeechType.getValueFromSymbol('h'));
		assertEquals(PartOfSpeechType.VERB_PARTICIPLE, PartOfSpeechType.getValueFromSymbol('V'));
		assertEquals(PartOfSpeechType.VERB_TRANSITIVE, PartOfSpeechType.getValueFromSymbol('t'));
		assertEquals(PartOfSpeechType.VERB_INTRANSITIVE, PartOfSpeechType.getValueFromSymbol('i'));
		assertEquals(PartOfSpeechType.ADJECTIVE, PartOfSpeechType.getValueFromSymbol('A'));
		assertEquals(PartOfSpeechType.ADVERB, PartOfSpeechType.getValueFromSymbol('v'));
		assertEquals(PartOfSpeechType.CONJUNCTION, PartOfSpeechType.getValueFromSymbol('C'));
		assertEquals(PartOfSpeechType.PREPOSITION, PartOfSpeechType.getValueFromSymbol('P'));
		assertEquals(PartOfSpeechType.INTERJECTION, PartOfSpeechType.getValueFromSymbol('!'));
		assertEquals(PartOfSpeechType.PRONOUN, PartOfSpeechType.getValueFromSymbol('r'));
		assertEquals(PartOfSpeechType.ARTICLE, PartOfSpeechType.getValueFromSymbol('D'));
		assertEquals(PartOfSpeechType.NOMINATIVE, PartOfSpeechType.getValueFromSymbol('o'));
		assertEquals(PartOfSpeechType.NONE, PartOfSpeechType.getValueFromSymbol('X'));
	}
}
