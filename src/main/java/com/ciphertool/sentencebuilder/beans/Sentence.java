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

package com.ciphertool.sentencebuilder.beans;

import java.util.ArrayList;
import java.util.List;

import com.ciphertool.sentencebuilder.entities.Word;

public class Sentence {
	private List<Word> words;

	public Sentence() {
		this.words = new ArrayList<Word>();
	}

	public Sentence(List<Word> words) {
		super();
		this.words = words;
	}

	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}

	/**
	 * Appends a Word to the end of this Sentence
	 * 
	 * @param w
	 */
	public void append(Word word) {
		this.words.add(word);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		for (Word w : words) {
			if (first) {
				// Make first word in proper case
				String firstWord = w.getWordId().getWord();
				sb.append(firstWord.substring(0, 1).toUpperCase()
						+ firstWord.substring(1, firstWord.length()));
				first = false;
			} else {
				sb.append(" ");
				sb.append(w.getWordId().getWord());
			}
		}
		sb.append(".");
		return sb.toString();
	}

	public int length() {
		int length = 0;
		for (Word w : words) {
			length += w.getWordId().getWord().length();
		}
		return length;
	}
}
