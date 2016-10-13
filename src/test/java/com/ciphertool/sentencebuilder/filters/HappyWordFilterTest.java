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

package com.ciphertool.sentencebuilder.filters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.enumerations.PartOfSpeechType;

public class HappyWordFilterTest {
	@Test
	public void testFilter() {
		HappyWordFilter happyWordFilter = new HappyWordFilter();

		assertFalse(happyWordFilter.filter(null));
		assertFalse(happyWordFilter.filter(new Word()));

		/*
		 * Not much to test here -- this filter should return true for any Word.
		 */
		assertTrue(happyWordFilter.filter(new Word("arbitraryWordThatDoesNotExist", PartOfSpeechType.NONE)));
	}
}
