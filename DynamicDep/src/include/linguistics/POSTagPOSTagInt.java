package include.linguistics;

import include.util.Tuple3;

import common.pos.POSTag;

/*
 * @author ZhangXun
 */

public final class POSTagPOSTagInt extends Tuple3<POSTag, POSTag, Integer> {
	
	public POSTagPOSTagInt() {
		super();
	}
	
	public POSTagPOSTagInt(final POSTagPOSTagInt tag_tag_int) {
		super(tag_tag_int);
	}
	
	public POSTagPOSTagInt(final POSTag tag1, final POSTag tag2, final Integer integer) {
		super(tag1, tag2, integer);
	}

	@Override
	public POSTag create_object1(final POSTag tag) {
		return new POSTag(tag);
	}

	@Override
	public POSTag create_object2(final POSTag tag) {
		return new POSTag(tag);
	}

	@Override
	public Integer create_object3(final Integer integer) {
		return new Integer(integer);
	}

}