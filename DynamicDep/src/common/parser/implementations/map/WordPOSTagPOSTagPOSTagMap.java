package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.Word;
import include.linguistics.WordPOSTagPOSTagPOSTag;
import common.pos.POSTag;

@SuppressWarnings("serial")
public final class WordPOSTagPOSTagPOSTagMap extends PackedScoreMap<WordPOSTagPOSTagPOSTag> {

	public WordPOSTagPOSTagPOSTagMap(final String input_name) {
		super(input_name);
	}

	@Override
	public WordPOSTagPOSTagPOSTag loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new WordPOSTagPOSTagPOSTag(
				new Word(args[0].substring(1, args[0].length() - 1)),
				new POSTag(args[1]),
				new POSTag(args[2]),
				new POSTag(args[3]));
	}

	@Override
	public String generateStringFromKey(final WordPOSTagPOSTagPOSTag key) {
		return "[" + key.first().toString() + "] , " + key.second().toString() + " , " + key.third().toString() + " , " + key.forth().toString();
	}

	@Override
	public WordPOSTagPOSTagPOSTag allocate_key(final WordPOSTagPOSTagPOSTag key) {
		return new WordPOSTagPOSTagPOSTag(key);
	}

}