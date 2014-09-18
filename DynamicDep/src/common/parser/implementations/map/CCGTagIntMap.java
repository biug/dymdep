package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.CCGTagInt;
import common.parser.MacrosBase;
import common.pos.CCGTag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class CCGTagIntMap extends PackedScoreMap<CCGTagInt> {
	
	public CCGTagIntMap(final String input_name) {
		super(input_name);
	}

	@Override
	public CCGTagInt loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new CCGTagInt(new CCGTag(args[0]), MacrosBase.integer_cache[Integer.parseInt(args[1])]);
	}

	@Override
	public String generateStringFromKey(final CCGTagInt key) {
		return key.first().toString() + " , " + key.second().toString();
	}

	@Override
	public CCGTagInt allocate_key(final CCGTagInt key) {
		return new CCGTagInt(key);
	}
	
}
