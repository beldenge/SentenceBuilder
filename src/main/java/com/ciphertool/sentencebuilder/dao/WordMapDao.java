package com.ciphertool.sentencebuilder.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.ciphertool.sentencebuilder.common.PartOfSpeech;
import com.ciphertool.sentencebuilder.entities.Word;

public interface WordMapDao {
	public Word findRandomWordByPartOfSpeech(PartOfSpeech pos);

	public HashMap<PartOfSpeech, ArrayList<Word>> getWordMap();
}
