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

public class PartOfSpeechFileParserTest {
	@Test
	public void testSetFilename() {
		String fileNameToSet = "arbitraryFileName";
		PartOfSpeechFileParser partOfSpeechFileParser = new PartOfSpeechFileParser();
		partOfSpeechFileParser.setFileName(fileNameToSet);

		Field fileNameField = ReflectionUtils.findField(PartOfSpeechFileParser.class, "fileName");
		ReflectionUtils.makeAccessible(fileNameField);
		String fileNameFromObject = (String) ReflectionUtils.getField(fileNameField,
				partOfSpeechFileParser);

		assertSame(fileNameToSet, fileNameFromObject);
	}

	@Test
	public void testParseFile() {
		String filePath = "src\\test\\data\\part-of-speech.txt";

		PartOfSpeechFileParser partOfSpeechFileParser = new PartOfSpeechFileParser();
		partOfSpeechFileParser.setFileName(filePath);

		List<Word> wordsFromFile = partOfSpeechFileParser.parseFile();

		Word expectedWord1 = new Word(new WordId("stuff", PartOfSpeechType.NOUN));
		Word expectedWord2 = new Word(new WordId("stuff", PartOfSpeechType.VERB_PARTICIPLE));
		Word expectedWord3 = new Word(new WordId("stuff", PartOfSpeechType.NOUN_PHRASE));
		Word expectedWord4 = new Word(new WordId("boarding pass", PartOfSpeechType.NOUN_PHRASE));
		Word expectedWord5 = new Word(new WordId("george", PartOfSpeechType.NOUN));

		assertEquals(expectedWord1, wordsFromFile.get(0));
		assertEquals(expectedWord2, wordsFromFile.get(1));
		assertEquals(expectedWord3, wordsFromFile.get(2));
		assertEquals(expectedWord4, wordsFromFile.get(3));
		assertEquals(expectedWord5, wordsFromFile.get(4));
	}

	@Test
	public void testParseFile_InvalidFileName() {
		PartOfSpeechFileParser partOfSpeechFileParser = new PartOfSpeechFileParser();
		partOfSpeechFileParser.setFileName("arbitraryFileName");

		Logger logMock = mock(Logger.class);

		Field logField = ReflectionUtils.findField(PartOfSpeechFileParser.class, "log");
		ReflectionUtils.makeAccessible(logField);
		ReflectionUtils.setField(logField, partOfSpeechFileParser, logMock);

		List<Word> wordsFromFile = partOfSpeechFileParser.parseFile();

		assertTrue(wordsFromFile.isEmpty());
		verify(logMock, times(1)).error(anyString(), any(FileNotFoundException.class));
	}

	@Test
	public void testParseLine() {
		List<Word> wordsFromFile = new ArrayList<Word>();
		String line = "stuff	NVh";

		PartOfSpeechFileParser partOfSpeechFileParser = new PartOfSpeechFileParser();

		assertEquals(0, wordsFromFile.size());
		int wordCount = partOfSpeechFileParser.parseLine(line, wordsFromFile);
		assertEquals(3, wordsFromFile.size());

		Word expectedWord1 = new Word(new WordId("stuff", PartOfSpeechType.NOUN));
		Word expectedWord2 = new Word(new WordId("stuff", PartOfSpeechType.VERB_PARTICIPLE));
		Word expectedWord3 = new Word(new WordId("stuff", PartOfSpeechType.NOUN_PHRASE));

		assertEquals(expectedWord1, wordsFromFile.get(0));
		assertEquals(expectedWord2, wordsFromFile.get(1));
		assertEquals(expectedWord3, wordsFromFile.get(2));
		assertEquals(3, wordCount);
	}

	@Test
	public void testParseLine_WithPipeAndSpaces() {
		List<Word> wordsFromFile = new ArrayList<Word>();
		String line = "boarding pass	|h";

		PartOfSpeechFileParser partOfSpeechFileParser = new PartOfSpeechFileParser();

		assertEquals(0, wordsFromFile.size());
		int wordCount = partOfSpeechFileParser.parseLine(line, wordsFromFile);
		assertEquals(1, wordsFromFile.size());

		Word expectedWord = new Word(new WordId("boarding pass", PartOfSpeechType.NOUN_PHRASE));

		assertEquals(expectedWord, wordsFromFile.get(0));
		assertEquals(1, wordCount);
	}
}
