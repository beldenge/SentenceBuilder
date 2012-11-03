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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "parts_of_speech")
public class Word {
	private Integer id;
	private char partOfSpeech;
	private String word;
	private int frequencyWeight;

	public Word() {
	}

	/*
	 * full-args constructor
	 */
	public Word(char partOfSpeech, String word, int frequencyWeight) {
		this.partOfSpeech = partOfSpeech;
		this.word = word;
		this.frequencyWeight = frequencyWeight;
	}

	/*
	 * This constructor is used for getting unique words from database
	 * irrespective of parts of speech
	 */
	public Word(String word, int frequencyWeight) {
		this.word = word;
		this.frequencyWeight = frequencyWeight;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	@NaturalId
	@Column(name = "part_of_speech")
	public char getPartOfSpeech() {
		return partOfSpeech;
	}

	public void setPartOfSpeech(char partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	@NaturalId
	@Column(name = "word")
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Column(name = "frequency_weight")
	public int getFrequencyWeight() {
		return frequencyWeight;
	}

	public void setFrequencyWeight(int frequencyWeight) {
		this.frequencyWeight = frequencyWeight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + partOfSpeech;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Word other = (Word) obj;
		if (partOfSpeech != other.partOfSpeech) {
			return false;
		}
		if (word == null) {
			if (other.word != null) {
				return false;
			}
		} else if (!word.equals(other.word)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Word [word=" + word + ", partOfSpeech=" + partOfSpeech + ", frequencyWeight="
				+ frequencyWeight + "]";
	}
}
