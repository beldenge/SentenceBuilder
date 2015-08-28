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

package com.ciphertool.sentencebuilder.wordgraph;

import java.util.HashMap;
import java.util.Map;

public class IndexNode {
	private String terminal;
	private Map<Character, IndexNode> letterMap = new HashMap<Character, IndexNode>();

	/**
	 * Default no-args constructor
	 */
	public IndexNode() {
	}

	/**
	 * @param terminal
	 */
	public IndexNode(String terminal) {
		super();
		this.terminal = terminal;
	}

	public boolean containsChild(Character c) {
		return this.letterMap.containsKey(c);
	}

	public IndexNode getChild(Character c) {
		return this.letterMap.get(c);
	}

	public void putChild(Character c, IndexNode child) {
		this.letterMap.put(c, child);
	}

	/**
	 * @return the terminal
	 */
	public String getTerminal() {
		return terminal;
	}

	/**
	 * @param terminal
	 *            the terminal to set
	 */
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
}
