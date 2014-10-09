package include.linguistics;

import common.parser.MacrosBase;
import common.pos.CCGTag;

/*
 * @author ZhangXun
 */

public class CCGTagSet {
	
	private int size;
	
	protected long m_nHash;
	
	public CCGTagSet(final int s) {
		size = s;
		m_nHash = 0;
	}
	
	public CCGTagSet(final CCGTagSet tagset) {
		size = tagset.size;
		m_nHash = tagset.m_nHash;
	}
	
	public CCGTagSet(final int size, final long code) {
		this.size = size;
		this.m_nHash = code;
	}
	
	public final void attach(final long code) {
		m_nHash = ((m_nHash << MacrosBase.CCGTAG_BITS_SIZE) | code);
	}
	
	@Override
	public final int hashCode() {
		return (int)(m_nHash & 0xffffffff);
	}
	
	@Override
	public final boolean equals(final Object o) {
		return m_nHash == ((CCGTagSet)o).m_nHash;
	}
	
	@Override
	public String toString() {
		String retval = "";
		long hs = m_nHash;
//		System.out.println("ccgtagset hash = " + m_nHash);
		for (int i = 0; i < size; ++i) {
			if (!retval.isEmpty()) {
				retval = " " + retval;
			}
			retval = CCGTag.str((int)(hs & ((1 << MacrosBase.CCGTAG_BITS_SIZE) - 1))) + retval;
			hs >>= MacrosBase.CCGTAG_BITS_SIZE;
		}
		return retval;
	}
	
	public final void load(final String s) {
		clear();
		String[] args = s.substring(2, s.length() - 2).split(" ");
		for (int i = 0; i < size; ++i) {
			this.attach(CCGTag.code(args[i]));
		}
	}
	
	public final void load(final long code) {
		m_nHash = code;
	}
	
	public final void clear() {
		m_nHash = 0;
	}
	
}
