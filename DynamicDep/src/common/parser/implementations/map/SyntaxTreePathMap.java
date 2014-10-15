package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.SyntaxTreePath;

@SuppressWarnings("serial")
public class SyntaxTreePathMap extends PackedScoreMap<SyntaxTreePath> {

	public SyntaxTreePathMap(String input_name) {
		super(input_name);
	}

	@Override
	public SyntaxTreePath allocate_key(SyntaxTreePath key) {
		return new SyntaxTreePath(key);
	}

	@Override
	public SyntaxTreePath loadKeyFromString(String str) {
		String[] args = str.substring(1, str.length() - 1).split(" @ ");
		return new SyntaxTreePath(args[0], args[1]);
	}

	@Override
	public String generateStringFromKey(SyntaxTreePath key) {
		return key.toString();
	}

}
