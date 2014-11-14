package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.POSTagPOSTagPOSTagPOSTag;

import common.pos.POSTag;

@SuppressWarnings("serial")
public final class POSTagPOSTagPOSTagPOSTagMap extends PackedScoreMap<POSTagPOSTagPOSTagPOSTag> {

	public POSTagPOSTagPOSTagPOSTagMap(final String input_name) {
		super(input_name);
	}

	@Override
	public POSTagPOSTagPOSTagPOSTag loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new POSTagPOSTagPOSTagPOSTag(
				new POSTag(args[0]),
				new POSTag(args[1]),
				new POSTag(args[2]),
				new POSTag(args[3]));
	}

	@Override
	public String generateStringFromKey(final POSTagPOSTagPOSTagPOSTag key) {
		return key.first().toString() + " , " + key.second().toString() + " , " + key.third().toString() + " , " + key.forth().toString();
	}

	@Override
	public POSTagPOSTagPOSTagPOSTag allocate_key(final POSTagPOSTagPOSTagPOSTag key) {
		return new POSTagPOSTagPOSTagPOSTag(key);
	}

}