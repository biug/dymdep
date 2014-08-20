package include.linguistics;

import common.parser.implementations.arceager.Macros;
import common.pos.Tag;

/*
 * @author ZhangXun
 */

public class TagSet {
	
	private int size;
	
	protected int m_nHash;
	
	public TagSet(final int s) {
		size = s;
		m_nHash = 0;
	}
	
	public TagSet(final TagSet tagset) {
		size = tagset.size;
		m_nHash = tagset.m_nHash;
	}
	
	public TagSet(final int size, final int code) {
		this.size = size;
		this.m_nHash = code;
	}
	
	private final void attach(final int code) {
		m_nHash = ((m_nHash << Macros.TAG_BITS_SIZE) | code);
	}
	
	@Override
	public final int hashCode() {
		return m_nHash;
	}
	
	@Override
	public final boolean equals(final Object o) {
		return m_nHash == ((TagSet)o).m_nHash;
	}
	
	@Override
	public String toString() {
		String retval = "";
		int hs = m_nHash;
		for (int i = 0; i < size; ++i) {
			if (!retval.isEmpty()) {
				retval = " " + retval;
			}
			retval = Tag.str(hs & ((1 << Macros.TAG_BITS_SIZE) - 1)) + retval;
			hs >>= Macros.TAG_BITS_SIZE;
		}
		return retval;
	}
	
	public final void load(final String s) {
		clear();
		String[] args = s.substring(2, s.length() - 2).split(" ");
		for (int i = 0; i < size; ++i) {
			this.attach(Tag.code(args[i]));
		}
	}
	
	public final void load(final int code) {
		m_nHash = code;
	}
	
	public final void clear() {
		m_nHash = 0;
	}
	
}
