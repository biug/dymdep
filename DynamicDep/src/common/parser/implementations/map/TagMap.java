package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;

import common.pos.Tag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class TagMap extends PackedScoreMap<Tag> {

	public TagMap(final String input_name) {
		super(input_name);
	}

	@Override
	public Tag loadKeyFromString(final String str) {
		return new Tag(str);
	}

	@Override
	public String generateStringFromKey(final Tag key) {
		return key.toString();
	}

	@Override
	public Tag allocate_key(final Tag key) {
		return key;
	}

}
