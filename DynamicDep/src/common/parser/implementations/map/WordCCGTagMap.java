package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.Word;
import include.linguistics.WordCCGTag;

import common.parser.MacrosBase;
import common.pos.CCGTag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class WordCCGTagMap extends PackedScoreMap<WordCCGTag> {

	public WordCCGTagMap(final String input_name) {
		super(input_name);
	}

	@Override
	public WordCCGTag loadKeyFromString(final String str) {
		String[] args = str.split(MacrosBase.SEPARTOR);
		return new WordCCGTag(new Word(args[0]), new CCGTag(args[1]));
	}

	@Override
	public String generateStringFromKey(final WordCCGTag key) {
		return key.first().toString() + MacrosBase.SEPARTOR + key.second().toString();
	}

	@Override
	public WordCCGTag allocate_key(final WordCCGTag key) {
		return new WordCCGTag(key);
	}

}

