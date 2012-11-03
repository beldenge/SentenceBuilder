/**
 * Copyright 2012 George Belden
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

package com.ciphertool.sentencebuilder.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.ciphertool.sentencebuilder.beans.Sentence;
import com.ciphertool.sentencebuilder.entities.Word;

public class SentenceTest {
	Sentence sent = null;

	@Before
	public void setUp() throws Exception {
		ArrayList<Word> wordList = new ArrayList<Word>();
		wordList.add(new Word("the", 'D'));
		wordList.add(new Word("man", 'N'));
		wordList.add(new Word("walked", 'V'));
		wordList.add(new Word("with", 'P'));
		wordList.add(new Word("God", 'N'));
		sent = new Sentence(wordList);
	}

	@Test
	public void testSentence() {
		assertEquals(sent.toString(), "The man walked with God.");
	}
}
