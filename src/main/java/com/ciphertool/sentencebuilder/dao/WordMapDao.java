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

package com.ciphertool.sentencebuilder.dao;

import java.util.ArrayList;
import java.util.Map;

import com.ciphertool.sentencebuilder.common.PartOfSpeech;
import com.ciphertool.sentencebuilder.entities.Word;

public interface WordMapDao {
	/**
	 * Finds a random Word by PartOfSpeech
	 * 
	 * @param pos
	 *            the PartOfSpeech to search by
	 * @return the Word found, or null if none is found
	 */
	public Word findRandomWordByPartOfSpeech(PartOfSpeech pos);

	/**
	 * Finds a random Word by length
	 * 
	 * @param length
	 *            the Word length to search by
	 * @return the Word found, or null if none is found
	 */
	public Word findRandomWordByLength(Integer length);

	/**
	 * @return the Map of Words keyed by PartOfSpeech
	 */
	public Map<PartOfSpeech, ArrayList<Word>> getPartOfSpeechWordMap();

	/**
	 * @return the Map of Words keyed by their length
	 */
	public Map<Integer, ArrayList<Word>> getLengthWordMap();
}
