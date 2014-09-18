package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;

import common.pos.CCGTag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class CCGTagMap extends PackedScoreMap<CCGTag> {

	public CCGTagMap(final String input_name) {
		super(input_name);
	}

	@Override
	public CCGTag loadKeyFromString(final String str) {
		return new CCGTag(str);
	}

	@Override
	public String generateStringFromKey(final CCGTag key) {
		return key.toString();
	}

	@Override
	public CCGTag allocate_key(final CCGTag key) {
		return key;
	}

}
