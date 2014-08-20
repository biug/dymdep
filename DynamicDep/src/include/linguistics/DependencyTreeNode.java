package include.linguistics;

/*
 * @author ZhangXun
 */

public final class DependencyTreeNode {
	
	public static final int DEPENDENCY_LINK_NO_HEAD = -1;
	
	public String word;
	public String tag;
	public int head;
	public String label;
	
	public DependencyTreeNode() {
		word = "";
		tag = "";
		head = DEPENDENCY_LINK_NO_HEAD;
		label = "";
	}
	
	public DependencyTreeNode(final String w, final String t, final int h, final String l) {
		word = w;
		tag = t;
		head = h;
		label = l;
	}
	
	@Override
	public boolean equals(Object o) {
		DependencyTreeNode node = (DependencyTreeNode)o;
		return word.equals(node.word) &&
				tag.equals(node.tag) &&
				head == node.head &&
				label.equals(node.label);
	}
	
	@Override
	public final String toString() {
		return word + "\t" + tag +  "\t" + Integer.toString(head) + "\t" + label;
	}
}
