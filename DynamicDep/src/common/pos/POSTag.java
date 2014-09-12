package common.pos;

import common.parser.implementations.MacrosTree;

/*
 * @author ZhangXun
 */

public final class POSTag {
	
	protected int m_code;
	
	public POSTag() {
		m_code = MacrosTree.POSTAG_NONE;
	}
	
	public POSTag(final int t) {
		m_code = t;
	}
	
	public POSTag(final POSTag t) {
		m_code = t.m_code;
	}
	
	public POSTag(final String s) {
		load(s);
	}
	
	@Override
	public int hashCode() {
		return m_code;
	}
	
	@Override
	public boolean equals(final Object t) {
		return m_code == ((POSTag)t).m_code;
	}
	
	@Override
	public String toString() {
		return MacrosTree.POSTAG_STRINGS[m_code];
	}

	public void load(final int i) {
		m_code = i;
	}
	
	public void load(String s) {
		m_code = MacrosTree.POSTAG_NONE;
		for (int i = 0; i < MacrosTree.POSTAG_COUNT; ++i) {
			if (MacrosTree.POSTAG_STRINGS[i].equals(s)) {
				m_code = i;
				return;
			}
		}		
	}
	
	public static String str(final int t) {
		return MacrosTree.POSTAG_STRINGS[t];
	}
	
	public static int code(final String s) {
		for (int i = 1; i < MacrosTree.POSTAG_COUNT; ++i) {
			if (MacrosTree.POSTAG_STRINGS[i].equals(s)) {
				return i;
			}
		}
		return MacrosTree.POSTAG_NONE;
	}
	
}
