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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.ciphertool.sentencebuilder.entities.NGram;

public class NGramDao {
	private Logger log = LoggerFactory.getLogger(getClass());

	private MongoOperations mongoOperations;

	/**
	 * Returns a list of all NGrams.
	 */
	public List<NGram> findAll() {
		List<NGram> result = mongoOperations.findAll(NGram.class);

		return result;
	}

	/**
	 * Returns a list of all NGrams.
	 */
	public List<NGram> findAllByNumWords(int numWords) {
		Query query = new Query();
		query.addCriteria(Criteria.where("numWords").is(numWords));

		List<NGram> result = mongoOperations.find(query,  NGram.class);

		return result;
	}

	/**
	 * Returns a list of top N NGrams.
	 */
	public List<NGram> findTopMostFrequent(int top) {
		Query query = new Query().limit(top).with(new Sort(Sort.Direction.DESC, "frequencyWeight"));
		List<NGram> result = mongoOperations.find(query, NGram.class); 

		return result;
	}

	/**
	 * Returns a list of top N NGrams.
	 */
	public List<NGram> findTopMostFrequentByNumWords(int numWords, int top) {
		Query query = new Query().limit(top).with(new Sort(Sort.Direction.DESC, "frequencyWeight"));
		query.addCriteria(Criteria.where("numWords").is(numWords));

		List<NGram> result = mongoOperations.find(query, NGram.class); 

		return result;
	}

	/**
	 * Finds all occurrences of a particular NGram by its String value.
	 * 
	 * @param nGram
	 *            the String value of the nGram to find
	 * @return the List of matching NGrams
	 */
	public List<NGram> findByNGramString(String nGram) {
		if (nGram == null) {
			log.warn("Attempted to find NGram by null String.  Unable to continue, thus returning null.");

			return null;
		}

		Query query = new Query();
		query.addCriteria(Criteria.where("nGram").is(nGram));

		List<NGram> result = mongoOperations.find(query, NGram.class); 

		return result;
	}

	/**
	 * Inserts a nGram.
	 * 
	 * @param nGram
	 *            the NGram to insert
	 * @return whether the insert was successful
	 */
	public boolean insert(NGram nGram) {
		if (nGram == null) {
			log.warn("Attempted to insert null NGram.  Unable to continue, thus returning false.");

			return false;
		}

		mongoOperations.insert(nGram);

		return true;
	}

	/**
	 * Inserts a List of NGrams in batch.
	 * 
	 * @param nGramBatch
	 *            the batch of NGrams to insert
	 * @return whether the insert was successful
	 */
	public boolean insertBatch(List<NGram> nGramBatch) {
		if (nGramBatch == null || nGramBatch.isEmpty()) {
			log.warn("Attempted to insert NGrams in batch which was found to be null or empty.  Unable to continue, thus returning false.");

			return false;
		}

		mongoOperations.insert(nGramBatch, NGram.class);

		return true;
	}

	/**
	 * Updates a NGram.
	 * 
	 * @param nGram
	 *            the NGram to update
	 * @return whether the update was successful
	 */
	public boolean update(NGram nGram) {
		if (nGram == null) {
			log.warn("Attempted to update null NGram.  Unable to continue, thus returning false.");

			return false;
		}

		Query query = new Query();
		query.addCriteria(Criteria.where("nGram").is(nGram.getNGram()));
		query.addCriteria(Criteria.where("numWords").is(nGram.getNumWords()));
		
		Update update = new Update();
		update.set("frequencyWeight", nGram.getFrequencyWeight());
		
		mongoOperations.updateFirst(query, update, NGram.class);

		return true;
	}

	/**
	 * Updates a List of NGrams in batch.
	 * 
	 * @param nGramBatch
	 *            the batch of NGrams
	 * @return whether the update was successful
	 */
	public boolean updateBatch(List<NGram> nGramBatch) {
		if (nGramBatch == null || nGramBatch.isEmpty()) {
			log.warn("Attempted to update NGrams in batch which was found to be null or empty.  Unable to continue, thus returning false.");

			return false;
		}

		for (NGram nGram : nGramBatch) {
			Query query = new Query();
			query.addCriteria(Criteria.where("nGram").is(nGram.getNGram()));
			query.addCriteria(Criteria.where("numWords").is(nGram.getNumWords()));
			
			Update update = new Update();
			update.set("frequencyWeight", nGram.getFrequencyWeight());
			
			mongoOperations.updateFirst(query, update, NGram.class);
		}
		
		return true;
	}

	@Required
	public void setMongoTemplate(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}
}
