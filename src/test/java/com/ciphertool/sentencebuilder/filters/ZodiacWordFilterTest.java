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

package com.ciphertool.sentencebuilder.filters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ciphertool.sentencebuilder.common.PartOfSpeechType;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;

public class ZodiacWordFilterTest {
	@Test
	public void testFilter() {
		ZodiacWordFilter zodiacWordFilter = new ZodiacWordFilter();

		assertFalse(zodiacWordFilter.filter(null));
		assertFalse(zodiacWordFilter.filter(new Word()));

		assertTrue(zodiacWordFilter.filter(new Word(new WordId("arbitraryWord",
				PartOfSpeechType.NONE))));

		assertFalse(zodiacWordFilter.filter(new Word(new WordId("ON", PartOfSpeechType.NOUN))));
		assertFalse(zodiacWordFilter.filter(new Word(new WordId("ll", PartOfSpeechType.ADJECTIVE))));
	}
}
