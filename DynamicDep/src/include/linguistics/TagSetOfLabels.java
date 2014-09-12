package include.linguistics;

import include.util.Tuple2;

import common.pos.POSTag;

/*
 * @author ZhangXun
 */

public final class TagSetOfLabels extends Tuple2<POSTag, SetOfLabels> {
	
	public TagSetOfLabels() {
		super();
	}
	
	public TagSetOfLabels(final TagSetOfLabels tag_tagset) {
		super(tag_tagset);
	}
	
	public TagSetOfLabels(final POSTag tag, final SetOfLabels tagset) {
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