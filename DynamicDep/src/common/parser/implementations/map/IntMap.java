package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class IntMap extends PackedScoreMap<Integer> {

	public IntMap(final String input_name) {
		super(input_name);
	}

	@Override
	public Integer loadKeyFromString(final String str) {
		return Integer.valueOf(str);
	}

	@Override
	public String generateStringFromKey(final Integer key) {
		return key.toString();
	}

	@Override
	public Integer allocate_key(final Integer key) {
		return key;
	}

}
