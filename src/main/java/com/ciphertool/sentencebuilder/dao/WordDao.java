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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.ciphertool.sentencebuilder.entities.Word;

public class WordDao {
	private Logger log = Logger.getLogger(getClass());

	private MongoOperations mongoOperations;

	/**
	 * Returns a list of all Words, so words will be duplicated if they have multiple parts of speech.
	 */
	public List<Word> findAll() {
		List<Word> result = mongoOperations.findAll(Word.class);

		return result;
	}

	/**
	 * Returns a list of top N Words, and words can be duplicated if they have multiple parts of speech.
	 */
	public List<Word> findTopByFrequency(int top) {
		Query query = new Query().limit(top).with(new Sort(Sort.Direction.DESC, "frequencyWeight"));
		List<Word> result = mongoOperations.find(query, Word.class); 

		return result;
	}

	/**
	 * Returns a list of all unique Words, irrespective of parts of speech.
	 */
	public List<Word> findAllUniqueWords() {
		Query query = new Query();
		query.fields().include("word").include("frequencyWeight");

		Set<Word> result = new HashSet<Word>(mongoOperations.find(query, Word.class)); 

		// We have to convert between set and list in order to achieve "distinct" functionality not supported by mongoOperations
		return new ArrayList<Word>(result);
	}

	/**
	 * Returns a list of top N unique Words, irrespective of parts of speech.
	 */
	public List<Word> findTopUniqueWordsByFrequency(int top) {
		Query query = new Query().limit(top);
		query.fields().include("word").include("frequencyWeight");

		Set<Word> result = new HashSet<Word>(mongoOperations.find(query, Word.class)); 

		// We have to convert between set and list in order to achieve "distinct" functionality not supported by mongoOperations
		return new ArrayList<Word>(result);
	}

	/**
	 * Finds all occurrences of a particular Word by its String value.
	 * 
	 * @param word
	 *            the String value of the word to find
	 * @return the List of matching Words
	 */
	public List<Word> findByWordString(String word) {
		if (word == null) {
			log.warn("Attempted to find Word by null String.  Unable to continue, thus returning null.");

			return null;
		}

		Query query = new Query();
		query.addCriteria(Criteria.where("word").is(word));

		List<Word> words = mongoOperations.find(query,  Word.class);

		return words;
	}

	/**
	 * Inserts a word.
	 * 
	 * @param word
	 *            the Word to insert
	 * @return whether the insert was successful
	 */
	public boolean insert(Word word) {
		if (word == null) {
			log.warn("Attempted to insert null Word.  Unable to continue, thus returning false.");

			return false;
		}

		mongoOperations.insert(word);

		return true;
	}

	/**
	 * Inserts a List of Words in batch.
	 * 
	 * @param wordBatch
	 *            the batch of Words to insert
	 * @return whether the insert was successful
	 */
	public boolean insertBatch(List<Word> wordBatch) {
		if (wordBatch == null || wordBatch.isEmpty()) {
			log.warn("Attempted to insert Words in batch which was found to be null or empty.  Unable to continue, thus returning false.");

			return false;
		}

		mongoOperations.insert(wordBatch, Word.class);
		
		return true;
	}

	/**
	 * Updates a Word.
	 * 
	 * @param word
	 *            the Word to update
	 * @return whether the update was successful
	 */
	public boolean update(Word word) {
		if (word == null) {
			log.warn("Attempted to update null Word.  Unable to continue, thus returning false.");

			return false;
		}

		Query query = new Query();
		query.addCriteria(Criteria.where("word").is(word.getWord()));
		query.addCriteria(Criteria.where("partOfSpeech").is(word.getPartOfSpeech()));
		
		Update update = new Update();
		update.set("frequencyWeight", word.getFrequencyWeight());
		
		mongoOperations.updateFirst(query, update, Word.class);

		return true;
	}

	/**
	 * Updates a List of Words in batch.
	 * 
	 * @param wordBatch
	 *            the batch of Words
	 * @return whether the update was successful
	 */
	public boolean updateBatch(List<Word> wordBatch) {
		if (wordBatch == null || wordBatch.isEmpty()) {
			log.warn("Attempted to update Words in batch which was found to be null or empty.  Unable to continue, thus returning false.");

			return false;
		}

		for (Word word : wordBatch) {
			Query query = new Query();
			query.addCriteria(Criteria.where("word").is(word.getWord()));
			query.addCriteria(Criteria.where("partOfSpeech").is(word.getPartOfSpeech()));
			
			Update update = new Update();
			update.set("frequencyWeight", word.getFrequencyWeight());
			
			mongoOperations.updateFirst(query, update, Word.class);
		}

		return true;
	}

	@Required
	public void setMongoTemplate(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}
}
