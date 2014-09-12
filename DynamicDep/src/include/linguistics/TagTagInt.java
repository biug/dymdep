package include.linguistics;

import include.util.Tuple3;

import common.pos.POSTag;

/*
 * @author ZhangXun
 */

public final class TagTagInt extends Tuple3<POSTag, POSTag, Integer> {
	
	public TagTagInt() {
		super();
	}
	
	public TagTagInt(final TagTagInt tag_tag_int) {
		super(tag_tag_int);
	}
	
	public TagTagInt(final POSTag tag1, final POSTag tag2, final Integer integer) {
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