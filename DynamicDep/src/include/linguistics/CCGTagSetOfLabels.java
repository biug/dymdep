package include.linguistics;

import include.util.Tuple2;

import common.pos.CCGTag;

/*
 * @author ZhangXun
 */

public final class CCGTagSetOfLabels extends Tuple2<CCGTag, SetOfLabels> {
	
	public CCGTagSetOfLabels() {
		super();
	}
	
	public CCGTagSetOfLabels(final CCGTagSetOfLabels tag_tagset) {
		super(tag_tagset);
	}
	
	public CCGTagSetOfLabels(final CCGTag tag, final SetOfLabels tagset) {
		super(tag, tagset);
	}

	@Override
	public CCGTag create_object1(final CCGTag tag) {
		return new CCGTag(tag);
	}

	@Override
	public SetOfLabels create_object2(final SetOfLabels tagset) {
		return new SetOfLabels(tagset);
	}
	
}