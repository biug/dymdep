package common.dependency.label;

import common.parser.implementations.arceager.Macros;

/*
 * @author ZhangXun
 */

public final class DependencyLabel {
	
	protected int m_code;
	
	public DependencyLabel(final String str) {
		load(str);
	}
	
	public void load(int u) {
		m_code = u;
	}
	
	public void load(final String str) {
		m_code = Macros.DEP_NONE;
		for (int i = Macros.DEP_FIRST; i < Macros.DEP_COUNT; ++i) {
			if (Macros.DEP_STRINGS[i].equals(str)) {
				m_code = i;
				return;
			}
		}
	}
	
	@Override
	public int hashCode() {
		return m_code;
	}
	
	@Override
	public boolean equals(final Object o) {
		return m_code == ((DependencyLabel)o).m_code;
	}
	
	
	@Override
	public String toString() {
		return Macros.DEP_STRINGS[m_code];
	}
	
	public static String str(final int code) {
		return Macros.DEP_STRINGS[code];
	}
	
	public static int code(final String label) {
		for (int i = Macros.DEP_FIRST; i < Macros.DEP_COUNT; ++i) {
			if (Macros.DEP_STRINGS[i].equals(label)) {
				return i;
			}
		}
		return Macros.DEP_NONE;
	}
	
}
