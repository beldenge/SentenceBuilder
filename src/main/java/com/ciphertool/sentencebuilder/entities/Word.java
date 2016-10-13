/**
 * Copyright 2015 George Belden
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

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ciphertool.sentencebuilder.enumerations.PartOfSpeechType;

@Document(collection = "partsOfSpeech")
@CompoundIndex(def = "{ 'partOfSpeech': 1, 'word': 1 }", unique =  true, background = true)
public class Word {
	@Id
	private String id;

	@Indexed(background = true)
	private String word;

	private PartOfSpeechType partOfSpeech;

	private int frequencyWeight;

	public Word() {
	}

	/**
	 * Business-key constructor
	 * 
	 * @param word the word String to set
	 * @param partOfSpeech the PartOfSpeechType to set
	 */
	public Word(String word, PartOfSpeechType partOfSpeech) {
		this(word, partOfSpeech, 1);
	}

	/**
	 * Full-args constructor
	 * 
	 * @param word the word String to set
	 * @param partOfSpeech the PartOfSpeechType to set
	 * @param frequencyWeight
	 *            the frequency weight to set
	 */
	public Word(String word, PartOfSpeechType partOfSpeech, int frequencyWeight) {
		this.word = word;
		this.partOfSpeech = partOfSpeech;
		this.frequencyWeight = frequencyWeight;
	}

	/**
	 * This constructor is required for querying unique words from database irrespective of part of speech. It's not
	 * called by any other code in this project, but it is used by an HQL query.
	 * 
	 * @param word
	 *            the word String to set
	 * @param frequencyWeight
	 *            the frequency weight to set
	 */
	public Word(String word, int frequencyWeight) {
		this(word, null, frequencyWeight);
	}

	/**
	 * @param nGram
	 *            the NGram
	 */
	public Word(NGram nGram) {
		this(nGram.getNGram(), (int) nGram.getFrequencyWeight());
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	
	/**
	 * @return the frequency weight
	 */
	public int getFrequencyWeight() {
		return frequencyWeight;
	}

	/**
	 * @param frequencyWeight
	 *            the frequency weight to set
	 */
	public void setFrequencyWeight(int frequencyWeight) {
		this.frequencyWeight = frequencyWeight;
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
		Word other = (Word) obj;
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
		return "Word [word=" + word + ", partOfSpeech=" + partOfSpeech + ", frequencyWeight=" + frequencyWeight + "]";
	}
}
