package include.linguistics;

import include.util.Tuple2;

import common.parser.MacrosBase;

/*
 * @author ZhangXun
 */

public final class SetOfDepLabelsInt extends Tuple2<SetOfDepLabels, Integer> {
	
	public SetOfDepLabelsInt() {
		super();
	}
	
	public SetOfDepLabelsInt(final SetOfDepLabelsInt tagset_int) {
		super(tagset_int);
	}
	
	public SetOfDepLabelsInt(final SetOfDepLabels tagset, final Integer i) {
		super(tagset, i);
	}

	@Override
	public SetOfDepLabels create_object1(final SetOfDepLabels tagset) {
		return new SetOfDepLabels(tagset);
	}

	@Override
	public Integer create_object2(final Integer i) {
		return MacrosBase.integer_cache[i.intValue()];
	}
	
}