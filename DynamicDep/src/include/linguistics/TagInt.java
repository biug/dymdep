package include.linguistics;

import include.util.Tuple2;

import common.pos.Tag;

/*
 * @author ZhangXun
 */

public final class TagInt extends Tuple2<Tag, Integer> {
	
	public TagInt() {
		super();
	}
	
	public TagInt(final TagInt tag_int) {
		super(tag_int);
	}
	
	public TagInt(final Tag tag, final Integer integer) {
		super(tag, integer);
	}

	@Override
	public Tag create_object1(final Tag tag) {
		return new Tag(tag);
	}

	@Override
	public Integer create_object2(final Integer integer) {
		return new Integer(integer);
	}
	
}
