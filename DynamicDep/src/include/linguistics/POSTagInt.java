package include.linguistics;

import include.util.Tuple2;

import common.pos.POSTag;

/*
 * @author ZhangXun
 */

public final class POSTagInt extends Tuple2<POSTag, Integer> {
	
	public POSTagInt() {
		super();
	}
	
	public POSTagInt(final POSTagInt tag_int) {
		super(tag_int);
	}
	
	public POSTagInt(final POSTag tag, final Integer integer) {
		super(tag, integer);
	}

	@Override
	public POSTag create_object1(final POSTag tag) {
		return new POSTag(tag);
	}

	@Override
	public Integer create_object2(final Integer integer) {
		return new Integer(integer);
	}
	
}
