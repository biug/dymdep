package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.CCGTaggedWord;

import common.parser.MacrosBase;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class CCGTaggedWordMap extends PackedScoreMap<CCGTaggedWord> {

	private static int middle;

	public CCGTaggedWordMap(final String input_name) {
		super(input_name);
	}

	@Override
	public CCGTaggedWord loadKeyFromString(final String str) {
		middle = str.lastIndexOf(MacrosBase.SEPARTOR);
		return new CCGTaggedWord(str.substring(0, middle), str.substring(middle+1));
	}

	@Override
	public String generateStringFromKey(final CCGTaggedWord key) {
		return key.word.toString() + MacrosBase.SEPARTOR + key.tag.toString();
	}

	@Override
	public CCGTaggedWord allocate_key(final CCGTaggedWord key) {
		return new CCGTaggedWord(key);
	}

}