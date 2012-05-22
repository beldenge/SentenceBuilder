package com.ciphertool.zodiacengine.beans;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.ciphertool.sentencebuilder.beans.Sentence;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;

public class SentenceTest {
	Sentence sent = null;

	@Before
	public void setUp() throws Exception {
		ArrayList<Word> wordList = new ArrayList<Word>();
		wordList.add(new Word(new WordId("the", 'D')));
		wordList.add(new Word(new WordId("man", 'N')));
		wordList.add(new Word(new WordId("walked", 'V')));
		wordList.add(new Word(new WordId("with", 'P')));
		wordList.add(new Word(new WordId("God", 'N')));
		sent = new Sentence(wordList);
	}

	@Test
	public void testSentence() {
		assertEquals(sent.toString(), "The man walked with God.");
	}
}
