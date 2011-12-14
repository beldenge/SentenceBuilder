package com.ciphertool.sentencebuilder.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="parts_of_speech")
public class Word {
	private WordId wordId;
	private long frequencyWeight;
	
	public Word() {}
	
	public Word(WordId wordId) {
		this.wordId=wordId;
		this.frequencyWeight=1;
	}
	
	public Word(WordId wordId, int frequencyWeight) {
		this.wordId=wordId;
		this.frequencyWeight=frequencyWeight;
	}

	@EmbeddedId
	public WordId getWordId() {
		return wordId;
	}

	public void setWordId(WordId wordId) {
		this.wordId = wordId;
	}
	
	@Column(name="frequency_weight")
	public long getFrequencyWeight() {
		return frequencyWeight;
	}
	
	public void setFrequencyWeight(long frequencyWeight) {
		this.frequencyWeight = frequencyWeight;
	}

	@Override
	public String toString() {
		return "Word [wordId=" + wordId
				+ ", frequencyWeight=" + frequencyWeight + "]";
	}
}
