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

package com.ciphertool.sentencebuilder.common;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class PartOfSpeechTest {

	private static Logger log = Logger.getLogger(PartOfSpeechTest.class);

	@Before
	public void setUp() {

	}

	@Test
	public void testEnumValueOf() {
		log.info(PartOfSpeech.valueOf("Pronoun".toUpperCase()).getTag());
	}
}
