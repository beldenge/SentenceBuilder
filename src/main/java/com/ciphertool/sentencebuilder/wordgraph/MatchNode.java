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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchNode {
	private Match self;
	private List<MatchNode> children = new ArrayList<MatchNode>();

	/**
	 * @param self
	 */
	public MatchNode(Match self) {
		this.self = self;
	}

	public final Match getSelf() {
		return self;
	}

	public List<MatchNode> getChildren() {
		return Collections.unmodifiableList(children);
	}

	public void addChild(MatchNode child) {
		this.children.add(child);
	}

	public List<String> printBranches() {
		List<String> branches = new ArrayList<String>();

		walk(branches, this.self.getWord());

		return branches;
	}

	public void walk(List<String> branches, String branch) {
		if (!this.getChildren().isEmpty()) {
			for (MatchNode child : this.children) {
				child.walk(branches, branch + ", " + child.self.getWord());
			}
		}

		branches.add(branch);
	}

	@Override
	public String toString() {
		return "MatchNode [self=" + self + "]";
	}
}
