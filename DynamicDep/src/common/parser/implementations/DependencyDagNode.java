package common.parser.implementations;

import java.util.ArrayList;
import java.util.List;

import common.parser.DependencyNodeBase;
import common.parser.MacrosBase;

public class DependencyDagNode extends DependencyNodeBase {
	
	public static final int DEPENDENCY_LINK_NO_HEAD = -1;
	
	public String ccgtag;
	public int righttail;
	public int rightseek, headsseek, childrenseek;
	public List<Arc> heads, children, rightarcs;
	
	public DependencyDagNode() {
		word = "";
		postag = "";
		treehead = -1;
		treelabel = MacrosBase.integer_cache[MacrosBase.MAX_INTEGER];
		ccgtag = "";
		righttail = -1;
		rightseek = headsseek = childrenseek = 0;
		heads = new ArrayList<Arc>();
		children = new ArrayList<Arc>();
		rightarcs = new ArrayList<Arc>();
	}
	
	public DependencyDagNode(final String w, final String p, final int th, final Integer tl) {
		word = w;
		postag = p;
		treehead = th;
		treelabel = tl;
		ccgtag = "";
		righttail = -1;
		rightseek = headsseek = childrenseek = 0;
		heads = new ArrayList<Arc>();
		children = new ArrayList<Arc>();
		rightarcs = new ArrayList<Arc>();
	}
	
	public DependencyDagNode(final String w, final String p, final int th, final Integer tl, final String c) {
		word = w;
		postag = p;
		treehead = th;
		treelabel = tl;
		ccgtag = c;
		righttail = -1;
		rightseek = headsseek = childrenseek = 0;
		heads = new ArrayList<Arc>();
		children = new ArrayList<Arc>();
		rightarcs = new ArrayList<Arc>();
	}
	
	@Override
	public boolean equals(Object o) {
		DependencyDagNode node = (DependencyDagNode)o;
		return word.equals(node.word) &&
				postag.equals(node.postag) &&
				treehead == node.treehead &&
				treelabel.equals(node.treelabel) &&
				ccgtag.equals(node.ccgtag) &&
				rightarcs.equals(node.rightarcs);
	}
	
	public Arc NearestHead() {
		return heads.get(headsseek);
	}
	
	public Arc NearestChild() {
		return children.get(childrenseek);
	}
	
	public Arc NearestRight() {
		return rightarcs.get(rightseek);
	}
}
