package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.CCGTagSet3;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class CCGTagSet3Map extends PackedScoreMap<CCGTagSet3> {
	
	public CCGTagSet3Map(final String input_name) {
		super(input_name);
	}

	@Override
	public CCGTagSet3 loadKeyFromString(final String str) {
		CCGTagSet3 ts = new CCGTagSet3();
		ts.load(str);
		return ts;
	}

	@Override
	public String generateStringFromKey(final CCGTagSet3 key) {
		return "[ " + key.toString() + " ]";
	}

	@Override
	public CCGTagSet3 allocate_key(final CCGTagSet3 key) {
		return new CCGTagSet3(key);
	}
	
}
