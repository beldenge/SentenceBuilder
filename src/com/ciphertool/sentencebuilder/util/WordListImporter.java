package com.ciphertool.sentencebuilder.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.sentencebuilder.dao.WordListDAO;

public class WordListImporter {
	private String fileName;
	private WordListDAO wordListDAO;
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public void importWordList(String[] args) throws IOException, SQLException, ClassNotFoundException {
		BufferedReader input = null;
		try {
			input =  new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File: " + fileName + " not found.");
			e.printStackTrace();
		}
		String [] line = null;
		String word = null;
		char [] partOfSpeech = null;
		String nextWord = input.readLine();
		int rowCount = 0;
		
        System.out.println("Starting import...");
		long start = System.currentTimeMillis();
		while (nextWord!=null) {
			line = nextWord.split("\t");
			word = line[0];
			//the pipe character is just informational in the word list
			partOfSpeech = line[1].toCharArray();
			for (int i = 0; i < partOfSpeech.length; i++) {
				if (partOfSpeech[i]!='|')
					rowCount += wordListDAO.insert(word, partOfSpeech[i], 1) ? 1 : 0;
			}
			nextWord = input.readLine();
		}
		long end = System.currentTimeMillis();
		
		System.out.println("Rows inserted: " + rowCount);
		System.out.println("Time elapsed: " + (end-start) + "ms");
	}

	@Required
	public void setFileName(String file) {
		fileName = file;
	}

	@Autowired
	public void setWordListDAO(WordListDAO wordListDAO) {
		this.wordListDAO = wordListDAO;
	}
}