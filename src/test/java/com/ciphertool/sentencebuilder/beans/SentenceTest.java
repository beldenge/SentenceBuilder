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

package com.ciphertool.sentencebuilder.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.List;

import org.junit.Test;

import com.ciphertool.sentencebuilder.common.PartOfSpeechType;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;

public class SentenceTest {

	@Test(expected = UnsupportedOperationException.class)
	public void testWordsUnmodifiable() {
		Sentence sentence = new Sentence();
		sentence.appendWord(new Word(new WordId("George", PartOfSpeechType.NOUN)));
		sentence.appendWord(new Word(new WordId("is", PartOfSpeechType.VERB_PARTICIPLE)));
		sentence.appendWord(new Word(new WordId("awesome", PartOfSpeechType.ADJECTIVE)));

		List<Word> words = sentence.getWords();
		words.remove(0); // should throw exception
	}

	@Test
	public void getNullWords() {
		Sentence sentence = new Sentence();
		assertNotNull(sentence.getWords());
	}

	@Test
	public void testAppendWord() {
		Sentence sentence = new Sentence();

		Word word1 = new Word(new WordId("George", PartOfSpeechType.NOUN));
		sentence.appendWord(word1);
		Word word2 = new Word(new WordId("is", PartOfSpeechType.VERB_PARTICIPLE));
		sentence.appendWord(word2);
		Word word3 = new Word(new WordId("awesome", PartOfSpeechType.ADJECTIVE));
		sentence.appendWord(word3);

		assertEquals(3, sentence.getWords().size());
		assertSame(word1, sentence.getWords().get(0));
		assertSame(word2, sentence.getWords().get(1));
		assertSame(word3, sentence.getWords().get(2));
	}

	@Test
	public void testRemoveWord() {
		Sentence sentence = new Sentence();

		Word word1 = new Word(new WordId("George", PartOfSpeechType.NOUN));
		sentence.appendWord(word1);
		Word word2 = new Word(new WordId("is", PartOfSpeechType.VERB_PARTICIPLE));
		sentence.appendWord(word2);
		Word word3 = new Word(new WordId("awesome", PartOfSpeechType.ADJECTIVE));
		sentence.appendWord(word3);

		assertEquals(3, sentence.getWords().size());
		assertSame(word1, sentence.getWords().get(0));
		assertSame(word2, sentence.getWords().get(1));
		assertSame(word3, sentence.getWords().get(2));

		Word wordRemoved = sentence.removeWord(1);

		assertEquals(2, sentence.getWords().size());
		assertSame(word2, wordRemoved);
		assertSame(word1, sentence.getWords().get(0));
		assertSame(word3, sentence.getWords().get(1));
	}

	@Test
	public void testLength() {
		Sentence sentence = new Sentence();
		sentence.appendWord(new Word(new WordId("George", PartOfSpeechType.NOUN)));
		sentence.appendWord(new Word(new WordId("is", PartOfSpeechType.VERB_PARTICIPLE)));
		sentence.appendWord(new Word(new WordId("awesome", PartOfSpeechType.ADJECTIVE)));

		assertEquals(15, sentence.length());
	}

	@Test
	public void testToString() {
		Sentence sentence = new Sentence();
		sentence.appendWord(new Word(new WordId("the", PartOfSpeechType.ARTICLE)));
		sentence.appendWord(new Word(new WordId("man", PartOfSpeechType.NOUN)));
		sentence.appendWord(new Word(new WordId("walked", PartOfSpeechType.VERB_PARTICIPLE)));
		sentence.appendWord(new Word(new WordId("with", PartOfSpeechType.PREPOSITION)));
		sentence.appendWord(new Word(new WordId("God", PartOfSpeechType.NOUN)));

		assertEquals("The man walked with God.", sentence.toString());
	}
}
