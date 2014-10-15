package include.linguistics;


public class SyntaxTreePath {
	String m_poses;
	String m_labels;
	
	public SyntaxTreePath() {
		m_poses = "";
		m_labels = "";
	}
	
	public SyntaxTreePath(final SyntaxTreePath path) {
		m_poses = path.m_poses;
		m_labels = path.m_labels;
	}
	
	public SyntaxTreePath(final String poses, final String labels) {
		m_poses = poses;
		m_labels = labels;
	}
	
	@Override
	public String toString() {
		return "[" + m_poses + " @ " + m_labels + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		SyntaxTreePath path = (SyntaxTreePath)o;
		return m_poses.equals(path.m_poses) && m_labels.equals(path.m_labels);
	}
	
	public void clear() {
		m_poses = "";
		m_labels = "";
	}
	
	public void addPos(final String pos) {
		m_poses += "#" + pos;
	}
	
	public void addLabel(final Integer label) {
		m_labels += "#" + label.toString();
	}
}
