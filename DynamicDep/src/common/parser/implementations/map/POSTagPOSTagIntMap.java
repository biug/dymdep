package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.POSTagPOSTagInt;
import common.parser.MacrosBase;
import common.pos.POSTag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class POSTagPOSTagIntMap extends PackedScoreMap<POSTagPOSTagInt> {

	public POSTagPOSTagIntMap(final String input_name) {
		super(input_name);
	}

	@Override
	public POSTagPOSTagInt loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		return new POSTagPOSTagInt(
				new POSTag(args[0]),
				new POSTag(args[1]),
				MacrosBase.integer_cache[Integer.parseInt(args[2]) == -1 ? MacrosBase.MAX_INTEGER : Integer.parseInt(args[2])]);
	}

	@Override
	public String generateStringFromKey(final POSTagPOSTagInt key) {
		return key.first().toString() + " , " + key.second().toString() + " , " + key.third().toString();
	}

	@Override
	public POSTagPOSTagInt allocate_key(final POSTagPOSTagInt key) {
		return new POSTagPOSTagInt(key);
	}

}
