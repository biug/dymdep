package include.linguistics;

import include.util.Tuple4;

import common.pos.POSTag;

public class POSTagPOSTagPOSTagPOSTag extends Tuple4<POSTag, POSTag, POSTag, POSTag> {

	public POSTagPOSTagPOSTagPOSTag() {
		super();
	}

	public POSTagPOSTagPOSTagPOSTag(POSTagPOSTagPOSTagPOSTag postag_postag_postag_postag) {
		super(postag_postag_postag_postag);
	}

	public POSTagPOSTagPOSTagPOSTag(POSTag postag1, POSTag postag2, POSTag postag3, POSTag postag4) {
		super(postag1, postag2, postag3, postag4);
	}

	@Override
	public POSTag create_object1(POSTag postag) {
		return new POSTag(postag);
	}

	@Override
	public POSTag create_object2(POSTag postag) {
		return new POSTag(postag);
	}

	@Override
	public POSTag create_object3(POSTag postag) {
		return new POSTag(postag);
	}

	@Override
	public POSTag create_object4(POSTag postag) {
		return new POSTag(postag);
	}

}
