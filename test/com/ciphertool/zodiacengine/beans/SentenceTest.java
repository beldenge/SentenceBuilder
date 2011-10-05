package com.ciphertool.zodiacengine.beans;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import com.ciphertool.sentencebuilder.beans.Sentence;
import com.ciphertool.sentencebuilder.beans.Word;

public class SentenceTest {
	Sentence sent = null;
	
	@Before
	public void setUp() throws Exception {
		ArrayList<Word> wordList = new ArrayList<Word>();
		wordList.add(new Word("the", null));
		wordList.add(new Word("man", null));
		wordList.add(new Word("walked", null));
		wordList.add(new Word("with", null));
		wordList.add(new Word("God", null));
		sent = new Sentence(wordList);
	}

	@Test
	public void testSentence() {
		assertEquals(sent.toString(), "The man walked with God.");
	}
}
