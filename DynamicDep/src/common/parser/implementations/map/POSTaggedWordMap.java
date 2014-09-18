package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.POSTaggedWord;

import common.parser.MacrosBase;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class POSTaggedWordMap extends PackedScoreMap<POSTaggedWord> {

	private static int middle;

	public POSTaggedWordMap(final String input_name) {
		super(input_name);
	}

	@Override
	public POSTaggedWord loadKeyFromString(final String str) {
		middle = str.lastIndexOf(MacrosBase.SEPARTOR);
		return new POSTaggedWord(str.substring(0, middle), str.substring(middle+1));
	}

	@Override
	public String generateStringFromKey(final POSTaggedWord key) {
		return key.word.toString() + MacrosBase.SEPARTOR + key.tag.toString();
	}

	@Override
	public POSTaggedWord allocate_key(final POSTaggedWord key) {
		return new POSTaggedWord(key);
	}

}