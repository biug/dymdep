package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.TagInt;

import common.pos.Tag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class TagIntMap extends PackedScoreMap<TagInt> {
	
	public TagIntMap(final String input_name) {
		super(input_name);
	}

	@Override
	public TagInt loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new TagInt(new Tag(args[0]), Integer.valueOf(args[1]));
	}

	@Override
	public String generateStringFromKey(final TagInt key) {
		return key.first().toString() + " , " + key.second().toString();
	}

	@Override
	public TagInt allocate_key(final TagInt key) {
		return new TagInt(key);
	}
	
}
