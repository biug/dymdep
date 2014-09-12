package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;

import common.pos.POSTag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class TagMap extends PackedScoreMap<POSTag> {

	public TagMap(final String input_name) {
		super(input_name);
	}

	@Override
	public POSTag loadKeyFromString(final String str) {
		return new POSTag(str);
	}

	@Override
	public String generateStringFromKey(final POSTag key) {
		return key.toString();
	}

	@Override
	public POSTag allocate_key(final POSTag key) {
		return key;
	}

}
