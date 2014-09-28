package include.linguistics;

import include.util.Tuple2;

import common.pos.CCGTag;

public final class CCGTagSetOfCCGLabels extends Tuple2<CCGTag, SetOfCCGLabels> {
	
	public CCGTagSetOfCCGLabels() {
		super();
	}
	
	public CCGTagSetOfCCGLabels(final CCGTagSetOfCCGLabels tag_tagset) {
		super(tag_tagset);
	}
	
	public CCGTagSetOfCCGLabels(final CCGTag tag, final SetOfCCGLabels tagset) {
		super(tag, tagset);
	}

	@Override
	public CCGTag create_object1(CCGTag tag) {
		return new CCGTag(tag);
	}

	@Override
	public SetOfCCGLabels create_object2(SetOfCCGLabels tagset) {
		return new SetOfCCGLabels(tagset);
	}

}
