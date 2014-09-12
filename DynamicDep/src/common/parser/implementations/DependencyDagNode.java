package common.parser.implementations;

import java.util.ArrayList;

import common.parser.DependencyNodeBase;

public class DependencyDagNode extends DependencyNodeBase {
	
	public String ccgtag;
	public int righttail;
	public int headstail;
	public int childrentail;
	public ArrayList<Arc> heads;
	public ArrayList<Arc> children;
	public ArrayList<Arc> rightarcs;
	
	public DependencyDagNode() {
		word = "";
		postag = "";
		ccgtag = "";
		righttail = -1;
		headstail = -1;
		childrentail = -1;
		heads = new ArrayList<Arc>();
		children = new ArrayList<Arc>();
		rightarcs = new ArrayList<Arc>();
	}
	
	public DependencyDagNode(final String w, final String p, final String c) {
		word = w;
		postag = p;
		ccgtag = c;
		righttail = -1;
		headstail = -1;
		childrentail = -1;
		heads = new ArrayList<Arc>();
		children = new ArrayList<Arc>();
		rightarcs = new ArrayList<Arc>();
	}
	
	@Override
	public boolean equals(Object o) {
		DependencyDagNode node = (DependencyDagNode)o;
		return word.equals(node.word) &&
				postag.equals(node.postag) &&
				ccgtag.equals(node.ccgtag) &&
				rightarcs.equals(node.rightarcs);
	}
	
	public Arc NearestHead() {
		return heads.get(headstail);
	}
	
	public Arc NearestChild() {
		return children.get(childrentail);
	}
	
	public Arc NearestRight() {
		return rightarcs.get(righttail);
	}
}
