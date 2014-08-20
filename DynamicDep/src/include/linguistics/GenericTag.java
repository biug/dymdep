package include.linguistics;

/*
 * @author ZhangXun
 */

public abstract class GenericTag {
	
	protected static final int NONE = 0;
	protected static final int FIRST = 1;
	
	protected int m_code;
	
	public GenericTag() {
		m_code = NONE;
	}
	
	public GenericTag(final GenericTag t) {
		m_code = t.m_code;
	}
	
	public GenericTag(final int u) {
		m_code = u;
	}
	
	@Override
	public final int hashCode() {
		return m_code;
	}
	
	@Override
	public final boolean equals(final Object o) {
		return m_code == ((GenericTag)o).m_code;
	}
	
	public abstract void load(final String s);
	
	public final void clear() {
		m_code = NONE;
	}
	
}
