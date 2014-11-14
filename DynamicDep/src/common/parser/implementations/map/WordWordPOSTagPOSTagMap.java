package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.Word;
import include.linguistics.WordWordPOSTagPOSTag;
import common.pos.POSTag;

@SuppressWarnings("serial")
public final class WordWordPOSTagPOSTagMap extends PackedScoreMap<WordWordPOSTagPOSTag> {

	public WordWordPOSTagPOSTagMap(final String input_name) {
		super(input_name);
	}

	@Override
	public WordWordPOSTagPOSTag loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new WordWordPOSTagPOSTag(
				new Word(args[0].substring(1, args[0].length() - 1)),
				new Word(args[1].substring(1, args[1].length() - 1)),
				new POSTag(args[2]),
				new POSTag(args[3]));
	}

	@Override
	public String generateStringFromKey(final WordWordPOSTagPOSTag key) {
		return "[" + key.first().toString() + "] , [" + key.second().toString() + "] , " + key.third().toString() + " , " + key.forth().toString();
	}

	@Override
	public WordWordPOSTagPOSTag allocate_key(final WordWordPOSTagPOSTag key) {
		return new WordWordPOSTagPOSTag(key);
	}

}