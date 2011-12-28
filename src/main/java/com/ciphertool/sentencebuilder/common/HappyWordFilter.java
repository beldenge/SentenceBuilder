package com.ciphertool.sentencebuilder.common;

import com.ciphertool.sentencebuilder.entities.Word;

public class HappyWordFilter implements WordFilter {

	public boolean filter(Word word) {
		/*
		 * We are so happy that we let every word pass our filter!
		 */
		return true;
	}
}
