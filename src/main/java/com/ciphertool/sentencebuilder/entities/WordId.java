package com.ciphertool.sentencebuilder.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class WordId implements Serializable {

	private static final long serialVersionUID = 8960135451615360512L;
	private String word;
	private char partOfSpeech;
	
	public WordId() {}
	
	public WordId(String word, char partOfSpeech) {
		this.word = word;
		this.partOfSpeech = partOfSpeech;
	}
	
	@Column(name="word")
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}

	@Column(name="part_of_speech")
	public char getPartOfSpeech() {
		return partOfSpeech;
	}
	
	public void setPartOfSpeech(char partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + partOfSpeech;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WordId other = (WordId) obj;
		if (partOfSpeech != other.partOfSpeech)
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WordId [word=" + word + ", partOfSpeech="
				+ partOfSpeech + "]";
	}
}
