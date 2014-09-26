package include.linguistics;

import java.util.HashSet;
import java.util.Set;

import common.parser.implementations.MacrosDag;
import common.pos.CCGTag;

public class SetOfCCGLabels {
	private Set<Integer> ccglabels;
	private int m_code;
	
	public SetOfCCGLabels() {
		ccglabels = new HashSet<Integer>();
		m_code = 0;
	}
	
	public SetOfCCGLabels(final SetOfCCGLabels labels) {
		ccglabels = new HashSet<Integer>();
		ccglabels.addAll(labels.ccglabels);
		m_code = labels.m_code;
	}
	
	public void add(final CCGTag tag) {
		ccglabels.add(MacrosDag.integer_cache[tag.hashCode()]);
		m_code <<= MacrosDag.CCGTAG_BITS_SIZE;
		m_code |= tag.hashCode();
	}
	
	public void add(final int code) {
		ccglabels.add(MacrosDag.integer_cache[code]);
		m_code <<= MacrosDag.CCGTAG_BITS_SIZE;
		m_code |= code;
	}
	
	public void remove(final CCGTag tag) {
		ccglabels.remove(MacrosDag.integer_cache[tag.hashCode()]);
	}
	
	public void remove(final int code) {
		ccglabels.remove(MacrosDag.integer_cache[code]);
	}
	
	public void clear() {
		ccglabels.clear();
	}
}
