package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;

@SuppressWarnings("serial")
public final class StringMap extends PackedScoreMap<String> {

	public StringMap(String input_name) {
		super(input_name);
	}

	@Override
	public String allocate_key(String key) {
		return key;
	}

	@Override
	public String loadKeyFromString(String str) {
		return str;
	}

	@Override
	public String generateStringFromKey(String key) {
		return key;
	}

}
