package include.linguistics;

import include.util.Tuple2;

import common.pos.POSTag;

/*
 * @author ZhangXun
 */

public final class POSTagSetOfLabels extends Tuple2<POSTag, SetOfLabels> {
	
	public POSTagSetOfLabels() {
		super();
	}
	
	public POSTagSetOfLabels(final POSTagSetOfLabels tag_tagset) {
		super(tag_tagset);
	}
	
	public POSTagSetOfLabels(final POSTag tag, final SetOfLabels tagset) {
		super(tag, tagset);
	}

	@Override
	public POSTag create_object1(final POSTag tag) {
		return new POSTag(tag);
	}

	@Override
	public SetOfLabels create_object2(final SetOfLabels tagset) {
		return new SetOfLabels(tagset);
	}
	
}