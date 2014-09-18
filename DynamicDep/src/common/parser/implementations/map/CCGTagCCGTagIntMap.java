package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.CCGTagCCGTagInt;
import common.parser.MacrosBase;
import common.pos.CCGTag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class CCGTagCCGTagIntMap extends PackedScoreMap<CCGTagCCGTagInt> {

	public CCGTagCCGTagIntMap(final String input_name) {
		super(input_name);
	}

	@Override
	public CCGTagCCGTagInt loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new CCGTagCCGTagInt(
				new CCGTag(args[0]),
				new CCGTag(args[1]),
				MacrosBase.integer_cache[Integer.parseInt(args[2])]);
	}

	@Override
	public String generateStringFromKey(final CCGTagCCGTagInt key) {
		return key.first().toString() + " , " + key.second().toString() + " , " + key.third().toString();
	}

	@Override
	public CCGTagCCGTagInt allocate_key(final CCGTagCCGTagInt key) {
		return new CCGTagCCGTagInt(key);
	}

}
