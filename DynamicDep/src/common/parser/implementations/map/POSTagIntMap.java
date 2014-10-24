package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.POSTagInt;
import common.parser.MacrosBase;
import common.pos.POSTag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class POSTagIntMap extends PackedScoreMap<POSTagInt> {
	
	public POSTagIntMap(final String input_name) {
		super(input_name);
	}

	@Override
	public POSTagInt loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new POSTagInt(new POSTag(args[0]), MacrosBase.integer_cache[Integer.parseInt(args[1]) == -1 ? MacrosBase.MAX_INTEGER : Integer.parseInt(args[1])]);
	}

	@Override
	public String generateStringFromKey(final POSTagInt key) {
		return key.first().toString() + " , " + key.second().toString();
	}

	@Override
	public POSTagInt allocate_key(final POSTagInt key) {
		return new POSTagInt(key);
	}
	
}
