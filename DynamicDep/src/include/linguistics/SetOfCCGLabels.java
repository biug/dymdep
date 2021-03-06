package include.linguistics;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import common.parser.implementations.MacrosCCGDag;
import common.pos.CCGTag;

public class SetOfCCGLabels {
	private Set<Integer> ccglabels;
	
	public SetOfCCGLabels() {
		ccglabels = new TreeSet<Integer>();
	}
	
	public SetOfCCGLabels(final SetOfCCGLabels labels) {
		ccglabels = new TreeSet<Integer>();
		ccglabels.addAll(labels.ccglabels);
	}
	
	public void add(final CCGTag tag) {
		ccglabels.add(MacrosCCGDag.integer_cache[tag.hashCode()]);
	}
	
	public void add(final int code) {
		if (code >= 0) {
			ccglabels.add(MacrosCCGDag.integer_cache[code]);
		} else {
			ccglabels.add(MacrosCCGDag.integer_cache[MacrosCCGDag.CCGTAG_NONE]);
		}
	}
	
	public void remove(final CCGTag tag) {
		ccglabels.remove(MacrosCCGDag.integer_cache[tag.hashCode()]);
	}
	
	public void remove(final int code) {
		ccglabels.remove(MacrosCCGDag.integer_cache[code]);
	}
	
	public void clear() {
		ccglabels.clear();
	}
	
	public boolean contains(final CCGTag tag) {
		return ccglabels.contains(tag);
	}
	
	public boolean contains(final int code) {
		return ccglabels.contains(MacrosCCGDag.integer_cache[code]);
	}
	
	@Override
	public int hashCode() {
		int code = 0;
		Iterator<Integer> itr = ccglabels.iterator();
		while (itr.hasNext()) {
			code <<= MacrosCCGDag.CCGTAG_BITS_SIZE;
			code |= itr.next().intValue();
		}
		return code;
	}
	
	@Override
	public boolean equals(Object o) {
		SetOfCCGLabels tagset = (SetOfCCGLabels)o;
		return ccglabels.equals(tagset.ccglabels);
	}
	
	public void copy(SetOfCCGLabels tagset) {
		ccglabels.clear();
		ccglabels.addAll(tagset.ccglabels);
	}
}
