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

import com.ciphertool.sentencebuilder.entities.NGram;

public class NGramFileParser implements FileParser<NGram> {

	private static Logger log = Logger.getLogger(NGramFileParser.class);

	/**
	 * We are expecting the fields to be tab-delimited in the file.
	 */
	private static final String FIELD_DELIMITER = "\t";

	@Override
	public List<NGram> parseFile(String fileName) {
		List<NGram> nGramsFromFile = new ArrayList<NGram>();

		BufferedReader input = null;

		try {
			input = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException fnfe) {
			log.error("File: " + fileName + " not found.  Returning as there is nothing to import.", fnfe);

			return nGramsFromFile;
		}

		int nGramCount = 0;
		int rowCount = 0;
		long start = System.currentTimeMillis();

		try {
			String nextLine = input.readLine();

			log.info("Parsing n-gram file " + fileName + "...");

			while (nextLine != null) {
				rowCount++;

				nGramCount += parseLine(nextLine, nGramsFromFile) ? 1 : 0;

				nextLine = input.readLine();
			}
		} catch (IOException ioe) {
			log.error("Caught IOException while reading next line from n-gram list.", ioe);
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				log.error("Unable to close BufferedReader while finishing n-gram list import.", e);
			}

			log.info("Parsed " + nGramCount + " NGrams from " + rowCount + " rows from file in "
					+ (System.currentTimeMillis() - start) + "ms.");
		}

		return nGramsFromFile;
	}

	protected boolean parseLine(String line, List<NGram> nGramsFromFile) {
		String[] lineParts = line.split(FIELD_DELIMITER);

		int frequency = Integer.parseInt(lineParts[0]);

		StringBuilder sb = new StringBuilder();

		for (int i = 1; i < lineParts.length; i++) {
			sb.append(lineParts[i]);
		}

		String nGram = sb.toString().toLowerCase().replaceAll("[^a-z]", "");

		if (nGram != null && !nGram.isEmpty()) {
			nGramsFromFile.add(new NGram(nGram, frequency));

			return true;
		}

		return false;
	}
}