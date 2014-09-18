package include.linguistics;

import include.util.Tuple2;

import common.pos.CCGTag;

/*
 * @author ZhangXun
 */

public final class CCGTagInt extends Tuple2<CCGTag, Integer> {
	
	public CCGTagInt() {
		super();
	}
	
	public CCGTagInt(final CCGTagInt tag_int) {
		super(tag_int);
	}
	
	public CCGTagInt(final CCGTag tag, final Integer integer) {
		super(tag, integer);
	}

	@Override
	public CCGTag create_object1(final CCGTag tag) {
		return new CCGTag(tag);
	}

	@Override
	public Integer create_object2(final Integer integer) {
		return new Integer(integer);
	}
	
}
