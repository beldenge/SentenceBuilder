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

package com.ciphertool.sentencebuilder.etl.parsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.ciphertool.sentencebuilder.common.PartOfSpeechType;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;

public class FrequencyFileParserTest {

	@Test
	public void testSetFilename() {
		String fileNameToSet = "arbitraryFileName";
		FrequencyFileParser frequencyFileParser = new FrequencyFileParser();
		frequencyFileParser.setFileName(fileNameToSet);

		Field fileNameField = ReflectionUtils.findField(FrequencyFileParser.class, "fileName");
		ReflectionUtils.makeAccessible(fileNameField);
		String fileNameFromObject = (String) ReflectionUtils.getField(fileNameField,
				frequencyFileParser);

		assertSame(fileNameToSet, fileNameFromObject);
	}

	@Test
	public void testParseFile() {
		String filePath = "src/test/data/frequency.txt";

		FrequencyFileParser frequencyFileParser = new FrequencyFileParser();
		frequencyFileParser.setFileName(filePath);

		List<Word> wordsFromFile = frequencyFileParser.parseFile();

		Word expectedWord1 = new Word(new WordId("the", PartOfSpeechType.NONE), 1501908);
		Word expectedWord2 = new Word(new WordId("to", PartOfSpeechType.NONE), 1156570);
		Word expectedWord3 = new Word(new WordId("a", PartOfSpeechType.NONE), 1041179);

		assertEquals(expectedWord1, wordsFromFile.get(0));
		assertEquals(expectedWord2, wordsFromFile.get(1));
		assertEquals(expectedWord3, wordsFromFile.get(2));
	}

	@Test
	public void testParseFile_InvalidFileName() {
		FrequencyFileParser frequencyFileParser = new FrequencyFileParser();
		frequencyFileParser.setFileName("arbitraryFileName");

		Logger logMock = mock(Logger.class);

		Field logField = ReflectionUtils.findField(FrequencyFileParser.class, "log");
		ReflectionUtils.makeAccessible(logField);
		ReflectionUtils.setField(logField, frequencyFileParser, logMock);

		List<Word> wordsFromFile = frequencyFileParser.parseFile();

		assertTrue(wordsFromFile.isEmpty());
		verify(logMock, times(1)).error(anyString(), any(FileNotFoundException.class));
	}

	@Test
	public void testParseLine() {
		List<Word> wordsFromFile = new ArrayList<Word>();
		String line = "the	1501908	8388	1339811	8388	29449.18	6.1766	100.00	3.9237";

		FrequencyFileParser frequencyFileParser = new FrequencyFileParser();

		assertEquals(0, wordsFromFile.size());
		boolean result = frequencyFileParser.parseLine(line, wordsFromFile);
		assertEquals(1, wordsFromFile.size());

		Word expectedWord1 = new Word(new WordId("the", PartOfSpeechType.NONE), 1501908);

		assertTrue(result);
		assertEquals(expectedWord1, wordsFromFile.get(0));
	}
}
