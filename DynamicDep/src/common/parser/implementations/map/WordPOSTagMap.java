package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.Word;
import include.linguistics.WordPOSTag;

import common.parser.MacrosBase;
import common.pos.POSTag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class WordPOSTagMap extends PackedScoreMap<WordPOSTag> {

	public WordPOSTagMap(final String input_name) {
		super(input_name);
	}

	@Override
	public WordPOSTag loadKeyFromString(final String str) {
		String[] args = str.split(MacrosBase.SEPARTOR);
		return new WordPOSTag(new Word(args[0]), new POSTag(args[1]));
	}

	@Override
	public String generateStringFromKey(final WordPOSTag key) {
		return key.first().toString() + MacrosBase.SEPARTOR + key.second().toString();
	}

	@Override
	public WordPOSTag allocate_key(final WordPOSTag key) {
		return new WordPOSTag(key);
	}

}

