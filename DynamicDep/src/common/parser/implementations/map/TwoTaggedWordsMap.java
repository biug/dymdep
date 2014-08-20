package common.parser.implementations.map;

import common.parser.implementations.arceager.Macros;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.TaggedWord;
import include.linguistics.TwoTaggedWords;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class TwoTaggedWordsMap extends PackedScoreMap<TwoTaggedWords> {
	
	private static int middle1, middle2;

	public TwoTaggedWordsMap(final String input_name) {
		super(input_name);
	}

	@Override
	public TwoTaggedWords loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		middle1 = args[0].lastIndexOf(Macros.SEPARTOR);
		middle2 = args[1].lastIndexOf(Macros.SEPARTOR);
		return new TwoTaggedWords(
				new TaggedWord(args[0].substring(0, middle1), args[0].substring(middle1 + 1)),
				new TaggedWord(args[1].substring(0, middle2), args[1].substring(middle2 + 1)));
	}

	@Override
	public String generateStringFromKey(final TwoTaggedWords key) {
		return key.first().word.toString() + Macros.SEPARTOR + key.first().tag.toString() +
				" , " +
				key.second().word.toString() + Macros.SEPARTOR + key.second().tag.toString();
	}

	@Override
	public TwoTaggedWords allocate_key(final TwoTaggedWords key) {
		return new TwoTaggedWords(key);
	}
	
}
