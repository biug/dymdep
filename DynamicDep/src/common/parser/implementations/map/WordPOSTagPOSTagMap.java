package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.Word;
import include.linguistics.WordPOSTagPOSTag;

import common.pos.POSTag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class WordPOSTagPOSTagMap extends PackedScoreMap<WordPOSTagPOSTag> {

	public WordPOSTagPOSTagMap(final String input_name) {
		super(input_name);
	}

	@Override
	public WordPOSTagPOSTag loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new WordPOSTagPOSTag(
				new Word(args[0].substring(1, args[0].length() - 1)),
				new POSTag(args[1]),
				new POSTag(args[2]));
	}

	@Override
	public String generateStringFromKey(final WordPOSTagPOSTag key) {
		return "[" + key.first().toString() + "] , " + key.second().toString() + " , " + key.third().toString();
	}

	@Override
	public WordPOSTagPOSTag allocate_key(final WordPOSTagPOSTag key) {
		return new WordPOSTagPOSTag(key);
	}

}
