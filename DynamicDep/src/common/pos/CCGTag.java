package common.pos;

import common.parser.MacrosBase;

/*
 * @author ZhangXun
 */

public final class CCGTag {
	
	protected int m_code;
	
	public CCGTag() {
		m_code = MacrosBase.CCGTAG_NONE;
	}
	
	public CCGTag(final int t) {
		m_code = t;
	}
	
	public CCGTag(final CCGTag t) {
		m_code = t.m_code;
	}
	
	public CCGTag(final String s) {
		load(s);
	}
	
	@Override
	public int hashCode() {
		return m_code;
	}
	
	@Override
	public boolean equals(final Object t) {
		return m_code == ((CCGTag)t).m_code;
	}
	
	@Override
	public String toString() {
		return MacrosBase.CCGTAG_STRINGS[m_code];
	}

	public void load(final int i) {
		m_code = i;
	}
	
	public void load(String s) {
		Integer i = MacrosBase.CCGTAG_MAP.get(s);
		m_code = (i == null ? MacrosBase.CCGTAG_NONE : i.intValue());	
	}
	
	public static String str(final int t) {
		return MacrosBase.CCGTAG_STRINGS[t];
	}
	
	public static int code(final String s) {
		Integer i = MacrosBase.CCGTAG_MAP.get(s);
		return (i == null ? MacrosBase.CCGTAG_NONE : i.intValue());	
	}
	
}
