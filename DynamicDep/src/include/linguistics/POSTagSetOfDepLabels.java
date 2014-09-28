package include.linguistics;

import include.util.Tuple2;

import common.pos.POSTag;

/*
 * @author ZhangXun
 */

public final class POSTagSetOfDepLabels extends Tuple2<POSTag, SetOfDepLabels> {
	
	public POSTagSetOfDepLabels() {
		super();
	}
	
	public POSTagSetOfDepLabels(final POSTagSetOfDepLabels tag_tagset) {
		super(tag_tagset);
	}
	
	public POSTagSetOfDepLabels(final POSTag tag, final SetOfDepLabels tagset) {
		super(tag, tagset);
	}

	@Override
	public POSTag create_object1(final POSTag tag) {
		return new POSTag(tag);
	}

	@Override
	public SetOfDepLabels create_object2(final SetOfDepLabels tagset) {
		return new SetOfDepLabels(tagset);
	}
	
}