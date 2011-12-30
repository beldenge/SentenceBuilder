package com.ciphertool.sentencebuilder.common;

/**
 * @author George
 *
 */
public enum PartOfSpeech {
	NOUN ('N'),
	PLURAL ('p'),
	NOUN_PHRASE('h'),
	VERB_PARTICIPLE ('V'),
	VERB_TRANSITIVE ('t'),
	VERB_INTRANSITIVE ('i'),
	ADJECTIVE ('A'),
	ADVERB ('v'),
	CONJUNCTION ('C'),
	PREPOSITION ('P'),
	INTERJECTION ('!'),
	PRONOUN ('r'),
	ARTICLE ('D'),
	NOMINATIVE ('o');
	
	private final char tag;
	
	PartOfSpeech(char tag) {
		this.tag = tag;
	}

	public char getTag() {
		return tag;
	}
	
	public static PartOfSpeech typeOf(char other) {
		for (PartOfSpeech pos : values()) {
			if (pos.getTag() == other) {
				return pos;
			}
		}
		return null;
	}
}
