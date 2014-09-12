package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.Word;
import include.linguistics.WordTag;
import common.parser.implementations.MacrosTree;
import common.pos.POSTag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class WordTagMap extends PackedScoreMap<WordTag> {

	public WordTagMap(final String input_name) {
		super(input_name);
	}

	@Override
	public WordTag loadKeyFromString(final String str) {
		String[] args = str.split(MacrosTree.SEPARTOR);
		return new WordTag(new Word(args[0]), new POSTag(args[1]));
	}

	@Override
	public String generateStringFromKey(final WordTag key) {
		return key.first().toString() + MacrosTree.SEPARTOR + key.second().toString();
	}

	@Override
	public WordTag allocate_key(final WordTag key) {
		return new WordTag(key);
	}

}

