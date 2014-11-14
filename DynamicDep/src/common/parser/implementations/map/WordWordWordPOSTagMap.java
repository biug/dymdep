package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.Word;
import include.linguistics.WordWordWordPOSTag;
import common.pos.POSTag;

@SuppressWarnings("serial")
public final class WordWordWordPOSTagMap extends PackedScoreMap<WordWordWordPOSTag> {

	public WordWordWordPOSTagMap(final String input_name) {
		super(input_name);
	}

	@Override
	public WordWordWordPOSTag loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new WordWordWordPOSTag(
				new Word(args[0].substring(1, args[0].length() - 1)),
				new Word(args[1].substring(1, args[1].length() - 1)),
				new Word(args[2].substring(1, args[2].length() - 1)),
				new POSTag(args[3]));
	}

	@Override
	public String generateStringFromKey(final WordWordWordPOSTag key) {
		return "[" + key.first().toString() + "] , [" + key.second().toString() + "] , [" + key.third().toString() + "] , " + key.forth().toString();
	}

	@Override
	public WordWordWordPOSTag allocate_key(final WordWordWordPOSTag key) {
		return new WordWordWordPOSTag(key);
	}

}