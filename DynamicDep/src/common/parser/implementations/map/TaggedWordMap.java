package common.parser.implementations.map;

import common.parser.implementations.arceager.Macros;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.TaggedWord;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class TaggedWordMap extends PackedScoreMap<TaggedWord> {

	private static int middle;

	public TaggedWordMap(final String input_name, final int table_size) {
		super(input_name, table_size);
	}

	@Override
	public TaggedWord loadKeyFromString(final String str) {
		middle = str.lastIndexOf(Macros.SEPARTOR);
		return new TaggedWord(str.substring(0, middle), str.substring(middle+1));
	}

	@Override
	public String generateStringFromKey(final TaggedWord key) {
		return key.word.toString() + Macros.SEPARTOR + key.tag.toString();
	}

	@Override
	public TaggedWord allocate_key(final TaggedWord key) {
		return new TaggedWord(key);
	}

}