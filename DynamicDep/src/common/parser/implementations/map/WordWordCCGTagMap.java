package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.Word;
import include.linguistics.WordWordCCGTag;

import common.pos.CCGTag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class WordWordCCGTagMap extends PackedScoreMap<WordWordCCGTag> {

	public WordWordCCGTagMap(final String input_name) {
		super(input_name);
	}

	@Override
	public WordWordCCGTag loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new WordWordCCGTag(
				new Word(args[0].substring(1, args[0].length() - 1)),
				new Word(args[1].substring(1, args[1].length() - 1)),
				new CCGTag(args[2]));
	}

	@Override
	public String generateStringFromKey(final WordWordCCGTag key) {
		return "[" + key.first().toString() + "] , [" + key.second().toString() + "] , " + key.third().toString();
	}

	@Override
	public WordWordCCGTag allocate_key(final WordWordCCGTag key) {
		return new WordWordCCGTag(key);
	}

}