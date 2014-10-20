package common.pos;

import common.parser.MacrosBase;

/*
 * @author ZhangXun
 */

public final class POSTag {
	
	protected int m_code;
	
	public POSTag() {
		m_code = MacrosBase.POSTAG_FIRST;
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
		return MacrosBase.POSTAG_STRINGS[m_code];
	}

	public void load(final int i) {
		m_code = i;
	}
	
	public void load(String s) {
		Integer i = MacrosBase.POSTAG_MAP.get(s);
		m_code = i == null ? 0 : i.intValue();	
	}
	
	public static String str(final int t) {
		return MacrosBase.POSTAG_STRINGS[t];
	}
	
	public static int code(final String s) {
		return MacrosBase.POSTAG_MAP.get(s).intValue();
	}
	
}
