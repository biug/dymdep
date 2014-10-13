package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.TwoInts;
import common.parser.MacrosBase;

@SuppressWarnings("serial")
public final class TwoIntsMap extends PackedScoreMap<TwoInts>  {

	public TwoIntsMap(String input_name) {
		super(input_name);
	}

	@Override
	public TwoInts allocate_key(TwoInts key) {
		return new TwoInts(key);
	}

	@Override
	public TwoInts loadKeyFromString(String str) {
		String[] args = str.split(" , ");
		return new TwoInts(MacrosBase.integer_cache[Integer.parseInt((args[0].substring(1, args[0].length() - 1)))], MacrosBase.integer_cache[Integer.parseInt((args[1].substring(1, args[1].length() - 1)))]);
	}

	@Override
	public String generateStringFromKey(TwoInts key) {
		return "[" + key.first().toString() + "] , [" + key.second().toString() + "]";
	}

}
