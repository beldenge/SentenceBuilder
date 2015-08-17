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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "n_gram")
public class NGram {

	@Id
	@Column(name = "n_gram")
	protected String ngram;

	@Column(name = "frequency_weight")
	protected int frequencyWeight;

	public NGram() {
	}

	/**
	 * Business-key constructor
	 * 
	 * @param ngram
	 *            the ngram to set
	 */
	public NGram(String ngram) {
		this.ngram = ngram;
		this.frequencyWeight = 1;
	}

	/**
	 * Full-args constructor
	 * 
	 * @param ngram
	 *            the ngram to set
	 * @param frequencyWeight
	 *            the frequency weight to set
	 */
	public NGram(String ngram, int frequencyWeight) {
		this.ngram = ngram;
		this.frequencyWeight = frequencyWeight;
	}

	/**
	 * @return the NGramId
	 */
	public String getNGram() {
		return ngram;
	}

	/**
	 * @param ngram
	 *            the ngram to set
	 */
	public void setNGram(String ngram) {
		this.ngram = ngram;
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
		result = prime * result + ((ngram == null) ? 0 : ngram.hashCode());
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
		NGram other = (NGram) obj;
		if (ngram == null) {
			if (other.ngram != null) {
				return false;
			}
		} else if (!ngram.equals(other.ngram)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "NGram [ngram=" + ngram + ", frequencyWeight=" + frequencyWeight + "]";
	}
}
