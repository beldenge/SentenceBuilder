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

package com.ciphertool.sentencebuilder.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "parts_of_speech")
public class Word {
	protected WordId id;
	protected int frequencyWeight;

	public Word() {
	}

	public Word(WordId id) {
		this.id = id;
		this.frequencyWeight = 1;
	}

	public Word(WordId id, int frequencyWeight) {
		this.id = id;
		this.frequencyWeight = frequencyWeight;
	}

	/*
	 * This constructor is used for getting unique words from database
	 * irrespective of parts of speech
	 */
	public Word(String word, int frequencyWeight) {
		this.id = new WordId(word, ' ');
		this.frequencyWeight = frequencyWeight;
	}

	@EmbeddedId
	public WordId getId() {
		return id;
	}

	public void setId(WordId id) {
		this.id = id;
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
		return "Word [id=" + id + ", frequencyWeight=" + frequencyWeight + "]";
	}
}
