package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.TwoWordInts;
import include.linguistics.Word;
import include.linguistics.WordInt;

import common.parser.MacrosBase;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class TwoWordIntsMap extends PackedScoreMap<TwoWordInts> {
	
	private static int middle1, middle2;

	public TwoWordIntsMap(final String input_name) {
		super(input_name);
	}

	@Override
	public TwoWordInts loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		middle1 = args[0].lastIndexOf(MacrosBase.SEPARTOR);
		middle2 = args[1].lastIndexOf(MacrosBase.SEPARTOR);
		return new TwoWordInts(
				new WordInt(new Word(args[0].substring(0, middle1)), MacrosBase.integer_cache[Integer.parseInt(args[0].substring(middle1 + 1))]),
				new WordInt(new Word(args[1].substring(0, middle2)), MacrosBase.integer_cache[Integer.parseInt(args[1].substring(middle2 + 1))]));
	}

	@Override
	public String generateStringFromKey(final TwoWordInts key) {
		return key.first().first().toString() + MacrosBase.SEPARTOR + key.first().second().toString() +
				" , " +
				key.second().first().toString() + MacrosBase.SEPARTOR + key.second().second().toString();
	}

	@Override
	public TwoWordInts allocate_key(final TwoWordInts key) {
		return new TwoWordInts(key);
	}
	
}
