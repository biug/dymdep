package common.parser.implementations.map;

import common.parser.MacrosBase;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.Word;
import include.linguistics.WordWordInt;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class WordWordIntMap extends PackedScoreMap<WordWordInt> {

	public WordWordIntMap(final String input_name) {
		super(input_name);
	}

	@Override
	public WordWordInt loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new WordWordInt(
				new Word(args[0].substring(1, args[0].length() - 1)),
				new Word(args[1].substring(1, args[1].length() - 1)),
				MacrosBase.integer_cache[Integer.parseInt(args[2])]);
	}

	@Override
	public String generateStringFromKey(final WordWordInt key) {
		return "[" + key.first().toString() + "] , [" + key.second().toString() + "] , " + key.third().toString();
	}

	@Override
	public WordWordInt allocate_key(final WordWordInt key) {
		return new WordWordInt(key);
	}

}