package com.ciphertool.sentencebuilder.beans;

import java.util.ArrayList;
import java.util.List;

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
	
	public String toString() {
		String s = "";
		boolean first = true;
		for (Word w : words) {
			if (first) {
				//Make first word in proper case
				String firstWord = w.getWord();
				s += firstWord.substring(0, 1).toUpperCase() + firstWord.substring(1, firstWord.length());
				first = false;
			}
			else {
				s += " " + w.getWord();
			}
		}
		s += ".";
		return s;
	}
}
