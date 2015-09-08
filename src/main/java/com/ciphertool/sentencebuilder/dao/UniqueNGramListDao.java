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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;

import com.ciphertool.sentencebuilder.entities.NGram;

public class UniqueNGramListDao implements NGramListDao {
	private static Logger log = Logger.getLogger(UniqueNGramListDao.class);

	private List<NGram> twoGramList = new ArrayList<NGram>();
	private List<NGram> threeGramList = new ArrayList<NGram>();
	private List<NGram> fourGramList = new ArrayList<NGram>();
	private List<NGram> fiveGramList = new ArrayList<NGram>();

	private Map<Integer, List<NGram>> mapOfNGramLists = new HashMap<Integer, List<NGram>>();

	{
		mapOfNGramLists.put(new Integer(2), twoGramList);
		mapOfNGramLists.put(new Integer(3), threeGramList);
		mapOfNGramLists.put(new Integer(4), fourGramList);
		mapOfNGramLists.put(new Integer(5), fiveGramList);
	}

	/**
	 * Constructor requiring a NGramDao and top number of NGrams.
	 * 
	 * @param nGramDao
	 *            the NGramDao to use for populating the internal Lists
	 * @param top
	 *            the top number of n-grams
	 */
	public UniqueNGramListDao(NGramDao nGramDao, Integer topTwoGrams, Integer topThreeGrams, Integer topFourGrams,
			Integer topFiveGrams) {
		if (nGramDao == null) {
			throw new IllegalArgumentException("Error constructing UniqueNGramListDao.  NGramDao cannot be null.");
		}

		if (topTwoGrams == null) {
			throw new IllegalArgumentException(
					"Error constructing UniqueNGramListDao.  Top cannot be null.  Please ensure top is either set to a positive number, or to -1 to be unbounded.");
		}

		if (topThreeGrams == null) {
			throw new IllegalArgumentException(
					"Error constructing UniqueNGramListDao.  Top cannot be null.  Please ensure top is either set to a positive number, or to -1 to be unbounded.");
		}

		if (topFourGrams == null) {
			throw new IllegalArgumentException(
					"Error constructing UniqueNGramListDao.  Top cannot be null.  Please ensure top is either set to a positive number, or to -1 to be unbounded.");
		}

		if (topFiveGrams == null) {
			throw new IllegalArgumentException(
					"Error constructing UniqueNGramListDao.  Top cannot be null.  Please ensure top is either set to a positive number, or to -1 to be unbounded.");
		}

		log.info("Beginning fetching of n-grams from database.");

		long start = System.currentTimeMillis();

		if (topTwoGrams < 0) {
			twoGramList.addAll(nGramDao.findAllByNumWords(2));

		} else if (topTwoGrams > 0) {
			twoGramList.addAll(nGramDao.findTopMostFrequentByNumWords(2, topTwoGrams));
		}

		if (topThreeGrams < 0) {
			threeGramList.addAll(nGramDao.findAllByNumWords(3));
		} else if (topThreeGrams > 0) {
			threeGramList.addAll(nGramDao.findTopMostFrequentByNumWords(3, topThreeGrams));
		}

		if (topFourGrams < 0) {
			fourGramList.addAll(nGramDao.findAllByNumWords(4));
		} else if (topFourGrams > 0) {
			fourGramList.addAll(nGramDao.findTopMostFrequentByNumWords(4, topFourGrams));
		}

		if (topFiveGrams < 0) {
			fiveGramList.addAll(nGramDao.findAllByNumWords(5));
		} else if (topFiveGrams > 0) {
			fiveGramList.addAll(nGramDao.findTopMostFrequentByNumWords(5, topFiveGrams));
		}

		log.info("Finished fetching n-grams from database in " + (System.currentTimeMillis() - start) + "ms.");

		twoGramList.sort(new FrequencyComparator());
		threeGramList.sort(new FrequencyComparator());
		fourGramList.sort(new FrequencyComparator());
		fiveGramList.sort(new FrequencyComparator());
	}

	private class FrequencyComparator implements Comparator<NGram> {
		@Override
		public int compare(NGram nGram1, NGram nGram2) {
			if (nGram1.getFrequencyWeight() < nGram2.getFrequencyWeight()) {
				return 1;
			} else if (nGram1.getFrequencyWeight() > nGram2.getFrequencyWeight()) {
				return -1;
			}

			return 0;
		}
	}

	@Override
	public NGram findRandomNGram() {
		int randomMapIndex = (int) (ThreadLocalRandom.current().nextDouble() * mapOfNGramLists.size());

		List<NGram> nGramList = mapOfNGramLists.get(randomMapIndex);

		int randomIndex = (int) (ThreadLocalRandom.current().nextDouble() * nGramList.size());

		return nGramList.get(randomIndex);
	}

	@Override
	public Map<Integer, List<NGram>> getMapOfNGramLists() {
		return Collections.unmodifiableMap(mapOfNGramLists);
	}
}
