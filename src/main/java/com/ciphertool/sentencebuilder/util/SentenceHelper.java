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

package com.ciphertool.sentencebuilder.util;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.cfgreader.datastructures.Node;
import com.ciphertool.cfgreader.datastructures.Tree;
import com.ciphertool.cfgreader.generated.ProductionType;
import com.ciphertool.cfgreader.util.ContextFreeGrammarHelper;
import com.ciphertool.sentencebuilder.beans.Sentence;
import com.ciphertool.sentencebuilder.dao.WordMapDao;
import com.ciphertool.sentencebuilder.enumerations.PartOfSpeechType;

public class SentenceHelper {
	private static Logger log = LoggerFactory.getLogger(SentenceHelper.class);

	private WordMapDao wordMapDao;
	private ContextFreeGrammarHelper cfgHelper;

	/**
	 * Constructor requiring a context-free grammar specification file.
	 * 
	 * @param grammarFile
	 *            the context-free grammar specification file
	 */
	public SentenceHelper(String grammarFile) {
		try {
			cfgHelper = new ContextFreeGrammarHelper(new File(grammarFile));
		} catch (JAXBException e) {
			log.error("Could not parse grammar file: " + grammarFile, e);
		}
	}

	/**
	 * Generates a random context-free grammar production tree and then fills in the terminals with words from database.
	 * 
	 * @return the generated Sentence
	 */
	public Sentence generateRandomSentence() {
		Tree<ProductionType> sentenceTree = cfgHelper.generateRandomSyntaxTree();

		Sentence sentence = makeSentenceFromTree(sentenceTree);

		return sentence;
	}

	/**
	 * Generates a String representation of the Sentence from terminal nodes.
	 * 
	 * @param sentenceList
	 *            the List of tree nodes
	 * @return the String representing a Sentence
	 */
	public String makeSentenceString(ArrayList<Node<ProductionType>> sentenceList) {
		StringBuilder sb = new StringBuilder();

		for (Node<ProductionType> node : sentenceList) {
			if (node.getData().getType().equals("Terminal")) {
				sb.append(" ");
				sb.append(node.getData().getSymbol());
			}
		}

		return sb.toString();
	}

	/**
	 * Fills in terminal symbols with random words from database based on the part of speech. The terminal symbols are
	 * case-sensitive when matching to the PartOfSpeech enum.
	 * 
	 * @param sentenceTree
	 * @return
	 */
	public Sentence makeSentenceFromTree(Tree<ProductionType> sentenceTree) {
		ArrayList<Node<ProductionType>> sentenceList = (ArrayList<Node<ProductionType>>) sentenceTree.toList();

		Sentence sentence = new Sentence();
		PartOfSpeechType pos = null;

		for (Node<ProductionType> n : sentenceList) {
			if (n.getData().getType().equals("Terminal")) {
				pos = PartOfSpeechType.valueOf(n.getData().getSymbol());

				sentence.appendWord(wordMapDao.findRandomWordByPartOfSpeech(pos));
			}
		}
		return sentence;
	}

	@Required
	public void setWordMapDao(WordMapDao wordMapDao) {
		this.wordMapDao = wordMapDao;
	}
}
