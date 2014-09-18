package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.Word;
import include.linguistics.WordCCGTagCCGTag;

import common.pos.CCGTag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class WordCCGTagCCGTagMap extends PackedScoreMap<WordCCGTagCCGTag> {

	public WordCCGTagCCGTagMap(final String input_name) {
		super(input_name);
	}

	@Override
	public WordCCGTagCCGTag loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new WordCCGTagCCGTag(
				new Word(args[0].substring(1, args[0].length() - 1)),
				new CCGTag(args[1]),
				new CCGTag(args[2]));
	}

	@Override
	public String generateStringFromKey(final WordCCGTagCCGTag key) {
		return "[" + key.first().toString() + "] , " + key.second().toString() + " , " + key.third().toString();
	}

	@Override
	public WordCCGTagCCGTag allocate_key(final WordCCGTagCCGTag key) {
		return new WordCCGTagCCGTag(key);
	}

}
