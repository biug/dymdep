package include.linguistics;

import include.util.Tuple2;

import common.pos.CCGTag;

/*
 * @author ZhangXun
 */

public final class CCGTagSetOfDepLabels extends Tuple2<CCGTag, SetOfDepLabels> {
	
	public CCGTagSetOfDepLabels() {
		super();
	}
	
	public CCGTagSetOfDepLabels(final CCGTagSetOfDepLabels tag_tagset) {
		super(tag_tagset);
	}
	
	public CCGTagSetOfDepLabels(final CCGTag tag, final SetOfDepLabels tagset) {
		super(tag, tagset);
	}

	@Override
	public CCGTag create_object1(final CCGTag tag) {
		return new CCGTag(tag);
	}

	@Override
	public SetOfDepLabels create_object2(final SetOfDepLabels tagset) {
		return new SetOfDepLabels(tagset);
	}
	
}