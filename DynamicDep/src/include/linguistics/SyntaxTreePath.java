package include.linguistics;

import java.util.ArrayList;

public class SyntaxTreePath {
	ArrayList<String> m_poses;
	ArrayList<Integer> m_labels;
	
	public SyntaxTreePath() {
		m_poses = new ArrayList<String>();
		m_labels = new ArrayList<Integer>();
	}
	
	public SyntaxTreePath(final SyntaxTreePath path) {
		m_poses = path.m_poses;
		m_labels = path.m_labels;
	}
	
	public SyntaxTreePath(final ArrayList<String> poses, final ArrayList<Integer> labels) {
		m_poses = poses;
		m_labels = labels;
	}
	
	@Override
	public String toString() {
		String ret = "[";
		ret += "[";
		if (m_poses.size() > 0) {
			ret += m_poses.get(0);
			for (int i = 1, n = m_poses.size(); i < n; ++i) {
				ret += " " + m_poses.get(i);
			}
		}
		ret += "] @ [";
		if (m_labels.size() > 0) {
			ret += m_labels.get(0).toString();
			for (int i = 1, n = m_labels.size(); i < n; ++i) {
				ret += " " + m_labels.get(i).toString();
			}
		}
		ret += "]]";
		return ret;
	}
	
	@Override
	public boolean equals(Object o) {
		SyntaxTreePath path = (SyntaxTreePath)o;
		return m_poses.equals(path.m_poses) && m_labels.equals(path.m_labels);
	}
	
	public void clear() {
		m_poses.clear();
		m_labels.clear();
	}
	
	public void addPos(final String pos) {
		m_poses.add(pos);
	}
	
	public void addLabel(final Integer label) {
		m_labels.add(label);
	}
}
