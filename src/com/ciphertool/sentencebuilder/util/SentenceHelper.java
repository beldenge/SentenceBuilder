package com.ciphertool.sentencebuilder.util;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.cfgreader.datastructures.Node;
import com.ciphertool.cfgreader.datastructures.Tree;
import com.ciphertool.cfgreader.generated.ProductionType;
import com.ciphertool.cfgreader.util.ContextFreeGrammarHelper;
import com.ciphertool.sentencebuilder.beans.Sentence;
import com.ciphertool.sentencebuilder.common.PartOfSpeech;
import com.ciphertool.sentencebuilder.dao.WordDAO;

public class SentenceHelper {
	
	private String grammarFile;
	private WordDAO wordDAO;
	
	public Sentence generateRandomSentence () throws JAXBException {
		ContextFreeGrammarHelper cfgHelper = new ContextFreeGrammarHelper(new File(grammarFile));
		Tree<ProductionType> sentenceTree = cfgHelper.generateRandomSyntaxTree();
		Sentence sentence = makeSentenceFromTree(sentenceTree);
		return sentence;
	}
	
	//Need to turn this into a list of Word objects, getting a random word based on the part of speech
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
				s.getWords().add(wordDAO.findRandomWordByPartOfSpeech(pos));
			}
		}
		return s;
	}
	
	@Required
	public void setGrammarFile(String grammarFile) {
		this.grammarFile = grammarFile;
	}
	
	@Autowired
	public void setWordDAO(WordDAO wordDAO) {
		this.wordDAO = wordDAO;
	}
	
	
}
