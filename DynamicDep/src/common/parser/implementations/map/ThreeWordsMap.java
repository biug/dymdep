package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.ThreeWords;
import include.linguistics.Word;

@SuppressWarnings("serial")
public final class ThreeWordsMap extends PackedScoreMap<ThreeWords> {

	public ThreeWordsMap(final String input_name) {
		super(input_name);
	}

	@Override
	public ThreeWords loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new ThreeWords(
				new Word(args[0].substring(1, args[0].length() - 1)),
				new Word(args[1].substring(1, args[1].length() - 1)),
				new Word(args[2].substring(1, args[2].length() - 1)));
	}

	@Override
	public String generateStringFromKey(final ThreeWords key) {
		return "[" + key.first().toString() + "] , [" + key.second().toString() + "] , [" + key.third().toString() + "]";
	}

	@Override
	public ThreeWords allocate_key(final ThreeWords key) {
		return new ThreeWords(key);
	}

}