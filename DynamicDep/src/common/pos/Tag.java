package common.pos;

import common.parser.implementations.arceager.Macros;

/*
 * @author ZhangXun
 */

public final class Tag {
	
	protected int m_code;
	
	public Tag() {
		m_code = Macros.TAG_NONE;
	}
	
	public Tag(final int t) {
		m_code = t;
	}
	
	public Tag(final Tag t) {
		m_code = t.m_code;
	}
	
	public Tag(final String s) {
		load(s);
	}
	
	@Override
	public int hashCode() {
		return m_code;
	}
	
	@Override
	public boolean equals(final Object t) {
		return m_code == ((Tag)t).m_code;
	}
	
	@Override
	public String toString() {
		return Macros.TAG_STRINGS[m_code];
	}

	public void load(final int i) {
		m_code = i;
	}
	
	public void load(String s) {
		m_code = Macros.TAG_NONE;
		for (int i = 0; i < Macros.TAG_COUNT; ++i) {
			if (Macros.TAG_STRINGS[i].equals(s)) {
				m_code = i;
				return;
			}
		}		
	}
	
	public static String str(final int t) {
		return Macros.TAG_STRINGS[t];
	}
	
	public static int code(final String s) {
		for (int i = 1; i < Macros.TAG_COUNT; ++i) {
			if (Macros.TAG_STRINGS[i].equals(s)) {
				return i;
			}
		}
		return Macros.TAG_NONE;
	}
	
}
