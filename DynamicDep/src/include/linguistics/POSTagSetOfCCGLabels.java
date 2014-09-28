package include.linguistics;

import include.util.Tuple2;

import common.pos.POSTag;

public final class POSTagSetOfCCGLabels extends Tuple2<POSTag, SetOfCCGLabels> {
	
	public POSTagSetOfCCGLabels() {
		super();
	}
	
	public POSTagSetOfCCGLabels(POSTagSetOfCCGLabels tag_tagset) {
		super(tag_tagset);
	}
	
	public POSTagSetOfCCGLabels(POSTag tag, SetOfCCGLabels tagset) {
		super(tag, tagset);
	}

	@Override
	public POSTag create_object1(POSTag tag) {
		return new POSTag(tag);
	}

	@Override
	public SetOfCCGLabels create_object2(SetOfCCGLabels tagset) {
		return new SetOfCCGLabels(tagset);
	}

}
