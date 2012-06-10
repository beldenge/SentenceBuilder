package com.ciphertool.sentencebuilder.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "parts_of_speech")
public class Word {
	protected WordId wordId;
	protected int frequencyWeight;

	public Word() {
	}

	public Word(WordId wordId) {
		this.wordId = wordId;
		this.frequencyWeight = 1;
	}

	public Word(WordId wordId, int frequencyWeight) {
		this.wordId = wordId;
		this.frequencyWeight = frequencyWeight;
	}

	/*
	 * This constructor is used for getting unique words from database
	 * irrespective of parts of speech
	 */
	public Word(String word, int frequencyWeight) {
		this.wordId = new WordId(word, ' ');
		this.frequencyWeight = frequencyWeight;
	}

	@EmbeddedId
	public WordId getWordId() {
		return wordId;
	}

	public void setWordId(WordId wordId) {
		this.wordId = wordId;
	}

	@Column(name = "frequency_weight")
	public int getFrequencyWeight() {
		return frequencyWeight;
	}

	public void setFrequencyWeight(int frequencyWeight) {
		this.frequencyWeight = frequencyWeight;
	}

	@Override
	public String toString() {
		return "Word [wordId=" + wordId + ", frequencyWeight=" + frequencyWeight + "]";
	}
}
