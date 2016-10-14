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

package com.ciphertool.sentencebuilder.etl.parsers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.enumerations.PartOfSpeechType;

public class FrequencyFileParser implements FileParser<Word> {
	private static Logger log = LoggerFactory.getLogger(FrequencyFileParser.class);

	/**
	 * We are expecting the fields to be tab-delimited in the file.
	 */
	private static final String FIELD_DELIMITER = "\t";

	@Override
	public List<Word> parseFile(String fileName) {
		List<Word> wordsFromFile = new ArrayList<Word>();

		BufferedReader input = null;

		try {
			input = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException fnfe) {
			log.error("File: " + fileName + " not found.  Returning as there is nothing to import.", fnfe);

			return wordsFromFile;
		}

		int wordCount = 0;
		int rowCount = 0;
		long start = System.currentTimeMillis();

		try {
			String nextLine = input.readLine();

			/*
			 * Skip to the second record because the first record contains column names
			 */
			nextLine = input.readLine();

			log.info("Parsing frequency file " + fileName + "...");

			while (nextLine != null) {
				rowCount++;

				wordCount += parseLine(nextLine, wordsFromFile) ? 1 : 0;

				nextLine = input.readLine();
			}
		} catch (IOException ioe) {
			log.error("Caught IOException while reading next line from word list.", ioe);
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				log.error("Unable to close BufferedReader while finishing word list import.", e);
			}

			log.info("Parsed " + wordCount + " Words from " + rowCount + " rows from file in "
					+ (System.currentTimeMillis() - start) + "ms.");
		}

		return wordsFromFile;
	}

	protected boolean parseLine(String line, List<Word> wordsFromFile) {
		String[] lineParts = line.split(FIELD_DELIMITER);

		String word = lineParts[0];

		int frequency = Integer.parseInt(lineParts[1]);

		if (word != null && !word.isEmpty()) {
			wordsFromFile.add(new Word(word, PartOfSpeechType.NONE, frequency));

			return true;
		}

		return false;
	}
}
