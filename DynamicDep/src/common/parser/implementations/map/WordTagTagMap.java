package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.Word;
import include.linguistics.WordTagTag;

import common.pos.Tag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class WordTagTagMap extends PackedScoreMap<WordTagTag> {

	public WordTagTagMap(final String input_name) {
		super(input_name);
	}

	@Override
	public WordTagTag loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new WordTagTag(
				new Word(args[0].substring(1, args[0].length() - 1)),
				new Tag(args[1]),
				new Tag(args[2]));
	}

	@Override
	public String generateStringFromKey(final WordTagTag key) {
		return "[" + key.first().toString() + "] , " + key.second().toString() + " , " + key.third().toString();
	}

	@Override
	public WordTagTag allocate_key(final WordTagTag key) {
		return new WordTagTag(key);
	}

}
