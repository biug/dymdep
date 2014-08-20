package include.linguistics;

import include.util.Tuple3;

import common.pos.Tag;

/*
 * @author ZhangXun
 */

public final class TagTagInt extends Tuple3<Tag, Tag, Integer> {
	
	public TagTagInt() {
		super();
	}
	
	public TagTagInt(final TagTagInt tag_tag_int) {
		super(tag_tag_int);
	}
	
	public TagTagInt(final Tag tag1, final Tag tag2, final Integer integer) {
		super(tag1, tag2, integer);
	}

	@Override
	public Tag create_object1(final Tag tag) {
		return new Tag(tag);
	}

	@Override
	public Tag create_object2(final Tag tag) {
		return new Tag(tag);
	}

	@Override
	public Integer create_object3(final Integer integer) {
		return new Integer(integer);
	}

}