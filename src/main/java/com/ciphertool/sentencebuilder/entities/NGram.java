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
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "nGram")
public class NGram {
	@Id
	protected String nGram;

	@Id
	protected int numWords;

	protected long frequencyWeight;

	public NGram() {
	}

	/**
	 * @param nGram
	 *            the nGram to set
	 * @param frequencyWeight
	 *            the frequencyWeight to set
	 */
	public NGram(String nGram, long frequencyWeight) {
		this.nGram = nGram;
		this.frequencyWeight = frequencyWeight;
	}

	/**
	 * @return the NGramId
	 */
	public String getNGram() {
		return this.nGram;
	}

	/**
	 * @param nGram
	 *            the nGram to set
	 */
	public void setNGram(String nGram) {
		this.nGram = nGram;
	}

	/**
	 * @return the numWords
	 */
	public int getNumWords() {
		return this.numWords;
	}

	/**
	 * @param numWords
	 *            the numWords to set
	 */
	public void setNumWords(int numWords) {
		this.numWords = numWords;
	}

	/**
	 * @return the frequency weight
	 */
	public long getFrequencyWeight() {
		return this.frequencyWeight;
	}

	/**
	 * @param frequencyWeight
	 *            the frequency weight to set
	 */
	public void setFrequencyWeight(long frequencyWeight) {
		this.frequencyWeight = frequencyWeight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nGram == null) ? 0 : nGram.hashCode());
		result = prime * result + numWords;
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
		if (!(obj instanceof NGram)) {
			return false;
		}
		NGram other = (NGram) obj;
		if (nGram == null) {
			if (other.nGram != null) {
				return false;
			}
		} else if (!nGram.equals(other.nGram)) {
			return false;
		}
		if (numWords != other.numWords) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "NGram [nGram=" + nGram + ", numWords=" + numWords + ", frequencyWeight=" + frequencyWeight + "]";
	}
}
