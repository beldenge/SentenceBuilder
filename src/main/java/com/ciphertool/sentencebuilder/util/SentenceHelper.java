package com.ciphertool.sentencebuilder.util;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ciphertool.cfgreader.datastructures.Node;
import com.ciphertool.cfgreader.datastructures.Tree;
import com.ciphertool.cfgreader.generated.ProductionType;
import com.ciphertool.cfgreader.util.ContextFreeGrammarHelper;
import com.ciphertool.sentencebuilder.beans.Sentence;
import com.ciphertool.sentencebuilder.common.PartOfSpeech;
import com.ciphertool.sentencebuilder.dao.WordMapDao;

public class SentenceHelper {
	private Logger log = Logger.getLogger(getClass());
	private WordMapDao wordMapDao;
	private ContextFreeGrammarHelper cfgHelper;

	
	public SentenceHelper(String grammarFile)
	{
		try {
			cfgHelper = new ContextFreeGrammarHelper(new File(grammarFile));
		} catch (JAXBException e) {
			log.error("Could not parse grammar file: " + grammarFile, e);
		}
	}
	
	public Sentence generateRandomSentence () {
		Tree<ProductionType> sentenceTree = cfgHelper.generateRandomSyntaxTree();
		Sentence sentence = makeSentenceFromTree(sentenceTree);
		return sentence;
	}
	
	public String makeSentenceString(ArrayList<Node<ProductionType>> sentenceList) {
		String s = "";
		for (Node<ProductionType> n: sentenceList) {
			if (n.getData().getType().equals("Terminal")) {
				s += " " + n.getData().getSymbol();
			}
		}
		return s;
	}
	
	public Sentence makeSentenceFromTree(Tree<ProductionType> sentenceTree) {
		ArrayList<Node<ProductionType>> sentenceList = (ArrayList<Node<ProductionType>>) sentenceTree.toList();
		Sentence s = new Sentence();
		for (Node<ProductionType> n: sentenceList) {
			if (n.getData().getType().equals("Terminal")) {
				PartOfSpeech pos = PartOfSpeech.valueOf(n.getData().getSymbol().toUpperCase());
				s.getWords().add(wordMapDao.findRandomWordByPartOfSpeech(pos));
			}
		}
		return s;
	}
	
	@Autowired
	public void setWordMapDao(WordMapDao wordMapDao) {
		this.wordMapDao = wordMapDao;
	}
}
