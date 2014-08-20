package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.TagTagInt;

import common.pos.Tag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class TagTagIntMap extends PackedScoreMap<TagTagInt> {

	public TagTagIntMap(final String input_name, final int table_size) {
		super(input_name, table_size);
	}

	@Override
	public TagTagInt loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new TagTagInt(
				new Tag(args[0]),
				new Tag(args[1]),
				Integer.valueOf(args[2]));
	}

	@Override
	public String generateStringFromKey(final TagTagInt key) {
		return key.first().toString() + " , " + key.second().toString() + " , " + key.third().toString();
	}

	@Override
	public TagTagInt allocate_key(final TagTagInt key) {
		return new TagTagInt(key);
	}

}
