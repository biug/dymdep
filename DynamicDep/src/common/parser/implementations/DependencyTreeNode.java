package common.parser.implementations;

import common.parser.DependencyNodeBase;

/*
 * @author ZhangXun
 */

public final class DependencyTreeNode extends DependencyNodeBase {
	
	public static final int DEPENDENCY_LINK_NO_HEAD = -1;
	
	public int head;
	public String label;
	
	public DependencyTreeNode() {
		word = "";
		postag = "";
		head = DEPENDENCY_LINK_NO_HEAD;
		label = "";
	}
	
	public DependencyTreeNode(final String w, final String t, final int h, final String l) {
		word = w;
		postag = t;
		head = h;
		label = l;
	}
	
	@Override
	public boolean equals(Object o) {
		DependencyTreeNode node = (DependencyTreeNode)o;
		return word.equals(node.word) &&
				postag.equals(node.postag) &&
				head == node.head &&
				label.equals(node.label);
	}
	
	@Override
	public final String toString() {
		return word + "\t" + postag +  "\t" + Integer.toString(head) + "\t" + label;
	}
}
