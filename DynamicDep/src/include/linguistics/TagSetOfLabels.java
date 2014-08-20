package include.linguistics;

import include.util.Tuple2;

import common.pos.Tag;

/*
 * @author ZhangXun
 */

public final class TagSetOfLabels extends Tuple2<Tag, SetOfLabels> {
	
	public TagSetOfLabels() {
		super();
	}
	
	public TagSetOfLabels(final TagSetOfLabels tag_tagset) {
		super(tag_tagset);
	}
	
	public TagSetOfLabels(final Tag tag, final SetOfLabels tagset) {
		super(tag, tagset);
	}

	@Override
	public Tag create_object1(final Tag tag) {
		return new Tag(tag);
	}

	@Override
	public SetOfLabels create_object2(final SetOfLabels tagset) {
		return new SetOfLabels(tagset);
	}
	
}