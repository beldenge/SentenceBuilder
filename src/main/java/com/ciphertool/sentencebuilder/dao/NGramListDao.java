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

package com.ciphertool.sentencebuilder.dao;

import java.util.List;
import java.util.Map;

import com.ciphertool.sentencebuilder.entities.NGram;

public interface NGramListDao {
	/**
	 * Returns a random NGram.
	 * 
	 * @return a random NGram
	 */
	public NGram findRandomNGram();

	/**
	 * @return the Map of NGram Lists
	 */
	public Map<Integer, List<NGram>> getMapOfNGramLists();
}