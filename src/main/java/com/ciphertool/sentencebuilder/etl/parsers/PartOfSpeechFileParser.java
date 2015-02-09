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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.sentencebuilder.common.PartOfSpeechType;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;

public class PartOfSpeechFileParser implements FileParser<Word> {

	private static Logger log = Logger.getLogger(PartOfSpeechFileParser.class);

	private String fileName;

	/**
	 * We are expecting the fields to be tab-delimited in the file.
	 */
	private static final String FIELD_DELIMITER = "\t";

	@Override
	public List<Word> parseFile() {
		List<Word> wordsFromFile = new ArrayList<Word>();

		BufferedReader input = null;

		try {
			input = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException fnfe) {
			log.error(
					"File: " + fileName + " not found.  Returning as there is nothing to import.",
					fnfe);

			return wordsFromFile;
		}

		int wordCount = 0;
		int rowCount = 0;
		long start = System.currentTimeMillis();

		try {
			String nextLine = input.readLine();

			log.info("Parsing parts of speech file...");

			while (nextLine != null) {
				rowCount++;

				wordCount += parseLine(nextLine, wordsFromFile);

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

	protected int parseLine(String line, List<Word> wordsFromFile) {
		int wordCount = 0;

		String[] lineParts = line.split(FIELD_DELIMITER);

		String word = lineParts[0];

		char[] partsOfSpeech = lineParts[1].toCharArray();

		for (int i = 0; i < partsOfSpeech.length; i++) {
			/*
			 * The pipe character is just informational in the word list, so we
			 * don't add it as a part of speech.
			 */
			if (partsOfSpeech[i] != '|') {
				wordsFromFile.add(new Word(new WordId(word, PartOfSpeechType
						.getValueFromSymbol(partsOfSpeech[i])), 1));

				wordCount++;
			}
		}

		return wordCount;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	@Required
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
