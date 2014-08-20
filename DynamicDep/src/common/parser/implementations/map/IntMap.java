package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class IntMap extends PackedScoreMap<Integer> {

	public IntMap(String input_name, int table_size) {
		super(input_name, table_size);
	}

	@Override
	public Integer loadKeyFromString(String str) {
		return Integer.valueOf(str);
	}

	@Override
	public String generateStringFromKey(Integer key) {
		return key.toString();
	}

	@Override
	public Integer allocate_key(Integer key) {
		return key;
	}

}
