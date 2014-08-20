package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.Word;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class WordMap extends PackedScoreMap<Word> {

	public WordMap(final String input_name, final int table_size) {
		super(input_name, table_size);
	}

	@Override
	public Word loadKeyFromString(final String str) {
		return new Word(str.substring(1, str.length() - 1));
	}

	@Override
	public String generateStringFromKey(final Word key) {
		return "[" + key.toString() + "]";
	}

	@Override
	public Word allocate_key(final Word key) {
		return key;
	}
	
}
