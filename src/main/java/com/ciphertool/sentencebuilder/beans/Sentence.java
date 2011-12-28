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
		String s = "";
		boolean first = true;
		for (Word w : words) {
			if (first) {
				//Make first word in proper case
				String firstWord = w.getWordId().getWord();
				s += firstWord.substring(0, 1).toUpperCase() + firstWord.substring(1, firstWord.length());
				first = false;
			}
			else {
				s += " " + w.getWordId().getWord();
			}
		}
		s += ".";
		return s;
	}
	
	public int length() {
		int length = 0;
		for (Word w : words) {
			length += w.getWordId().getWord().length();
		}
		return length;
	}
}
