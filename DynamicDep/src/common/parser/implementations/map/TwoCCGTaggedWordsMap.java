package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.CCGTaggedWord;
import include.linguistics.TwoCCGTaggedWords;

import common.parser.MacrosBase;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class TwoCCGTaggedWordsMap extends PackedScoreMap<TwoCCGTaggedWords> {
	
	private static int middle1, middle2;

	public TwoCCGTaggedWordsMap(final String input_name) {
		super(input_name);
	}

	@Override
	public TwoCCGTaggedWords loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		middle1 = args[0].lastIndexOf(MacrosBase.SEPARTOR);
		middle2 = args[1].lastIndexOf(MacrosBase.SEPARTOR);
		return new TwoCCGTaggedWords(
				new CCGTaggedWord(args[0].substring(0, middle1), args[0].substring(middle1 + 1)),
				new CCGTaggedWord(args[1].substring(0, middle2), args[1].substring(middle2 + 1)));
	}

	@Override
	public String generateStringFromKey(final TwoCCGTaggedWords key) {
		return key.first().word.toString() + MacrosBase.SEPARTOR + key.first().tag.toString() +
				" , " +
				key.second().word.toString() + MacrosBase.SEPARTOR + key.second().tag.toString();
	}

	@Override
	public TwoCCGTaggedWords allocate_key(final TwoCCGTaggedWords key) {
		return new TwoCCGTaggedWords(key);
	}
	
}
