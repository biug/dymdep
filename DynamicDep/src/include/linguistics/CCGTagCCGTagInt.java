package include.linguistics;

import include.util.Tuple3;

import common.pos.CCGTag;

/*
 * @author ZhangXun
 */

public final class CCGTagCCGTagInt extends Tuple3<CCGTag, CCGTag, Integer> {
	
	public CCGTagCCGTagInt() {
		super();
	}
	
	public CCGTagCCGTagInt(final CCGTagCCGTagInt tag_tag_int) {
		super(tag_tag_int);
	}
	
	public CCGTagCCGTagInt(final CCGTag tag1, final CCGTag tag2, final Integer integer) {
		super(tag1, tag2, integer);
	}

	@Override
	public CCGTag create_object1(final CCGTag tag) {
		return new CCGTag(tag);
	}

	@Override
	public CCGTag create_object2(final CCGTag tag) {
		return new CCGTag(tag);
	}

	@Override
	public Integer create_object3(final Integer integer) {
		return new Integer(integer);
	}

}