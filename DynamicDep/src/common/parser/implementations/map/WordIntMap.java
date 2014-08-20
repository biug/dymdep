package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.Word;
import include.linguistics.WordInt;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class WordIntMap extends PackedScoreMap<WordInt> {

	public WordIntMap(final String input_name) {
		super(input_name);
	}

	@Override
	public WordInt loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new WordInt(new Word(args[0].substring(1, args[0].length() - 1)), Integer.valueOf(args[1]));
	}

	@Override
	public String generateStringFromKey(final WordInt key) {
		return "[" + key.first().toString() + "] , " + key.second().toString();
	}

	@Override
	public WordInt allocate_key(final WordInt key) {
		return new WordInt(key);
	}

}
