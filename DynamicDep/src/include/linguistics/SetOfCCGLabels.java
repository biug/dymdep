package include.linguistics;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import common.parser.implementations.MacrosDag;
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
		ccglabels.add(MacrosDag.integer_cache[tag.hashCode()]);
	}
	
	public void add(final int code) {
		ccglabels.add(MacrosDag.integer_cache[code]);
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
	
	public boolean contains(final CCGTag tag) {
		return ccglabels.contains(tag);
	}
	
	public boolean contains(final int code) {
		return ccglabels.contains(MacrosDag.integer_cache[code]);
	}
	
	@Override
	public int hashCode() {
		int code = 0;
		Iterator<Integer> itr = ccglabels.iterator();
		while (itr.hasNext()) {
			code <<= MacrosDag.CCGTAG_BITS_SIZE;
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
