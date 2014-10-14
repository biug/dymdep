package common.pos;

import common.parser.MacrosBase;

public class TreeTag {

	protected int m_code;
	
	public TreeTag() {
		m_code = MacrosBase.TREE_FIRST;
	}
	
	public TreeTag(final int t) {
		m_code = t;
	}
	
	public TreeTag(final TreeTag tag) {
		m_code = tag.m_code;
	}
	
	public TreeTag(final String s) {
		load(s);
	}
	
	@Override
	public int hashCode() {
		return m_code;
	}
	
	@Override
	public boolean equals(Object o) {
		return m_code == ((TreeTag)o).m_code;
	}
	
	@Override
	public String toString() {
		return MacrosBase.TREE_STRINGS[m_code];
	}
	
	public void load(final int i) {
		m_code = i;
	}
	
	public void load(final String s) {
		m_code = MacrosBase.TREE_MAP.get(s).intValue();
	}
	
	public static String str(final int t) {
		return MacrosBase.TREE_STRINGS[t];
	}
	
	public static int code(final String s) {
		return MacrosBase.TREE_MAP.get(s).intValue();
	}
}
