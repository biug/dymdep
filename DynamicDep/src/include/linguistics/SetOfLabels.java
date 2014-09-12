package include.linguistics;

import common.dependency.label.DependencyLabel;

/*
 * @author ZhangXun
 */

public final class SetOfLabels {
	
	private int m_code;
	
	public SetOfLabels() {
		m_code = 0;
	}
	
	public SetOfLabels(final SetOfLabels tags) {
		m_code = tags.m_code;
	}
	
	public void add(final DependencyLabel tag) {
		m_code |= (1 << tag.hashCode());
	}
	
	public void add(final int code) {
		m_code |= (1 << code);
	}
	
	public void remove(final DependencyLabel tag) {
		m_code &= ~(1 << tag.hashCode());
	}
	
	public void remove(final int code) {
		m_code &= ~(1 << code);
	}
	
	public void clear() {
		m_code = 0;
	}
	
	public boolean contains(final DependencyLabel tag) {
		return (m_code & (1 << tag.hashCode())) != 0;
	}
	
	public boolean contains(final int code) {
		return (m_code & (1 << code)) != 0;
	}
	
	@Override
	public int hashCode() {
		return m_code;
	}
	
	@Override
	public boolean equals(final Object o) {
		return m_code == ((SetOfLabels)o).m_code;
	}
	
	public void copy(final SetOfLabels tagset) {
		m_code = tagset.m_code;
	}
	
}
