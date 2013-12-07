/**
 * Copyright 2013 George Belden
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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.ciphertool.sentencebuilder.common.PartOfSpeechType;

@Embeddable
public class WordId implements Serializable {

	private static final long serialVersionUID = 8960135451615360512L;

	@Column(name = "word")
	private String word;

	@Enumerated(EnumType.STRING)
	@Column(name = "part_of_speech")
	private PartOfSpeechType partOfSpeech;

	public WordId() {
	}

	/**
	 * Full-args constructor
	 * 
	 * @param word
	 *            the word String to set
	 * @param partOfSpeech
	 *            the PartOfSpeech symbol to set
	 */
	public WordId(String word, PartOfSpeechType partOfSpeech) {
		this.word = word;
		this.partOfSpeech = partOfSpeech;
	}

	/**
	 * @return the word String
	 */
	public String getWord() {
		return word;
	}

	/**
	 * @param word
	 *            the word String to set
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * @return the PartOfSpeechType
	 */
	public PartOfSpeechType getPartOfSpeech() {
		return partOfSpeech;
	}

	/**
	 * @param partOfSpeech
	 *            the PartOfSpeech symbol to set
	 */
	public void setPartOfSpeech(PartOfSpeechType partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((partOfSpeech == null) ? 0 : partOfSpeech.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

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
		WordId other = (WordId) obj;
		if (partOfSpeech != other.partOfSpeech) {
			return false;
		}
		if (word == null) {
			if (other.word != null) {
				return false;
			}
		} else if (!word.equalsIgnoreCase(other.word)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "WordId [word=" + word + ", partOfSpeech=" + partOfSpeech + "]";
	}
}
