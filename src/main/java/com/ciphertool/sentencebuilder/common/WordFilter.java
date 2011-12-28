package com.ciphertool.sentencebuilder.common;

import com.ciphertool.sentencebuilder.entities.Word;

public interface WordFilter {
	
	/**
	 * Should return true if the supplied Word passes the filter.  Otherwise, it should return false, indicating that this Word should not be accepted by the caller.  
	 * 
	 * @return
	 */
	boolean filter(Word word);
	
}
