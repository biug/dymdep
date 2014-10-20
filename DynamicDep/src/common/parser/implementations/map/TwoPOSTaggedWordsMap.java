package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.POSTaggedWord;
import include.linguistics.TwoPOSTaggedWords;

import common.parser.MacrosBase;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class TwoPOSTaggedWordsMap extends PackedScoreMap<TwoPOSTaggedWords> {
	
	private static int middle1, middle2;

	public TwoPOSTaggedWordsMap(final String input_name) {
		super(input_name);
	}

	@Override
	public TwoPOSTaggedWords loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		middle1 = args[0].lastIndexOf(MacrosBase.SEPARTOR);
		middle2 = args[1].lastIndexOf(MacrosBase.SEPARTOR);
		return new TwoPOSTaggedWords(
				new POSTaggedWord(args[0].substring(0, middle1), args[0].substring(middle1 + 1)),
				new POSTaggedWord(args[1].substring(0, middle2), args[1].substring(middle2 + 1)));
	}

	@Override
	public String generateStringFromKey(final TwoPOSTaggedWords key) {
		return key.first().word.toString() + MacrosBase.SEPARTOR + key.first().tag.toString() +
				" , " +
				key.second().word.toString() + MacrosBase.SEPARTOR + key.second().tag.toString();
	}

	@Override
	public TwoPOSTaggedWords allocate_key(final TwoPOSTaggedWords key) {
		return new TwoPOSTaggedWords(key);
	}
	
}
