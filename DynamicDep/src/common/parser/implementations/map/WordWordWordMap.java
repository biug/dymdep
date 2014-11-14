package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.Word;
import include.linguistics.WordWordWord;

@SuppressWarnings("serial")
public final class WordWordWordMap extends PackedScoreMap<WordWordWord> {

	public WordWordWordMap(final String input_name) {
		super(input_name);
	}

	@Override
	public WordWordWord loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new WordWordWord(
				new Word(args[0].substring(1, args[0].length() - 1)),
				new Word(args[1].substring(1, args[1].length() - 1)),
				new Word(args[2].substring(1, args[2].length() - 1)));
	}

	@Override
	public String generateStringFromKey(final WordWordWord key) {
		return "[" + key.first().toString() + "] , [" + key.second().toString() + "] , [" + key.third().toString() + "]";
	}

	@Override
	public WordWordWord allocate_key(final WordWordWord key) {
		return new WordWordWord(key);
	}

}