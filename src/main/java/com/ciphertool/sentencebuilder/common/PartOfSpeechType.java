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

public enum PartOfSpeechType {
	NOUN('N'),
	PLURAL('p'),
	NOUN_PHRASE('h'),
	VERB_PARTICIPLE('V'),
	VERB_TRANSITIVE('t'),
	VERB_INTRANSITIVE('i'),
	ADJECTIVE('A'),
	ADVERB('v'),
	CONJUNCTION('C'),
	PREPOSITION('P'),
	INTERJECTION('!'),
	PRONOUN('r'),
	ARTICLE('D'),
	NOMINATIVE('o'),
	NONE('X');

	private final char symbol;

	PartOfSpeechType(char symbol) {
		this.symbol = symbol;
	}

	public char getSymbol() {
		return symbol;
	}

	public static PartOfSpeechType getValueFromSymbol(char symbol) {
		for (PartOfSpeechType pos : values()) {
			if (pos.symbol == symbol) {
				return pos;
			}
		}

		return null;
	}
}
