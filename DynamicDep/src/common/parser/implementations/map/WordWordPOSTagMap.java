package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.Word;
import include.linguistics.WordWordPOSTag;

import common.pos.POSTag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class WordWordPOSTagMap extends PackedScoreMap<WordWordPOSTag> {

	public WordWordPOSTagMap(final String input_name) {
		super(input_name);
	}

	@Override
	public WordWordPOSTag loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new WordWordPOSTag(
				new Word(args[0].substring(1, args[0].length() - 1)),
				new Word(args[1].substring(1, args[1].length() - 1)),
				new POSTag(args[2]));
	}

	@Override
	public String generateStringFromKey(final WordWordPOSTag key) {
		return "[" + key.first().toString() + "] , [" + key.second().toString() + "] , " + key.third().toString();
	}

	@Override
	public WordWordPOSTag allocate_key(final WordWordPOSTag key) {
		return new WordWordPOSTag(key);
	}

}