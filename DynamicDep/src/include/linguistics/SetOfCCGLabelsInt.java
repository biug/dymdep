package include.linguistics;

import include.util.Tuple2;

import common.parser.MacrosBase;

public final class SetOfCCGLabelsInt extends Tuple2<SetOfCCGLabels, Integer> {
	
	public SetOfCCGLabelsInt() {
		super();
	}
	
	public SetOfCCGLabelsInt(final SetOfCCGLabelsInt tagset_int) {
		super(tagset_int);
	}
	
	public SetOfCCGLabelsInt(final SetOfCCGLabels tagset, final Integer i) {
		super(tagset, i);
	}

	@Override
	public SetOfCCGLabels create_object1(SetOfCCGLabels tagset) {
		return new SetOfCCGLabels(tagset);
	}

	@Override
	public Integer create_object2(Integer i) {
		return MacrosBase.integer_cache[i.intValue()];
	}

}
