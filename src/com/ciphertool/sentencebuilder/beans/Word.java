package com.ciphertool.sentencebuilder.beans;

import com.ciphertool.sentencebuilder.common.PartOfSpeech;

public class Word {
	private String word;
	private PartOfSpeech pos;
	private int weight;
	
	public Word() {
	}
	
	public Word(String word, PartOfSpeech type) {
		super();
		this.word = word;
		this.pos = type;
		this.weight = 1;
	}
	
	public Word(String word, PartOfSpeech type, int weight) {
		super();
		this.word = word;
		this.pos = type;
		this.weight = weight;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public PartOfSpeech getPOS() {
		return pos;
	}
	
	public void setPOS(PartOfSpeech type) {
		this.pos = type;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
