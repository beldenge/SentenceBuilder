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

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "parts_of_speech")
public class Word {

	@EmbeddedId
	protected WordId id;

	@Column(name = "frequency_weight")
	protected int frequencyWeight;

	public Word() {
	}

	/**
	 * Business-key constructor
	 * 
	 * @param id
	 *            the WordId to set
	 */
	public Word(WordId id) {
		this.id = id;
		this.frequencyWeight = 1;
	}

	/**
	 * Full-args constructor
	 * 
	 * @param id
	 *            the WordId to set
	 * @param frequencyWeight
	 *            the frequency weight to set
	 */
	public Word(WordId id, int frequencyWeight) {
		this.id = id;
		this.frequencyWeight = frequencyWeight;
	}

	/**
	 * @return the WordId
	 */
	public WordId getId() {
		return id;
	}

	/**
	 * @param id
	 *            the WordId to set
	 */
	public void setId(WordId id) {
		this.id = id;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Word [id=" + id + ", frequencyWeight=" + frequencyWeight + "]";
	}
}
