package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.Word;
import include.linguistics.WordWordTag;

import common.pos.Tag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class WordWordTagMap extends PackedScoreMap<WordWordTag> {

	public WordWordTagMap(final String input_name, final int table_size) {
		super(input_name, table_size);
	}

	@Override
	public WordWordTag loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new WordWordTag(
				new Word(args[0].substring(1, args[0].length() - 1)),
				new Word(args[1].substring(1, args[1].length() - 1)),
				new Tag(args[2]));
	}

	@Override
	public String generateStringFromKey(final WordWordTag key) {
		return "[" + key.first().toString() + "] , [" + key.second().toString() + "] , " + key.third().toString();
	}

	@Override
	public WordWordTag allocate_key(final WordWordTag key) {
		return new WordWordTag(key);
	}

}