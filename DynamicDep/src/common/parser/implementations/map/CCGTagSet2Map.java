package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.CCGTagSet2;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class CCGTagSet2Map extends PackedScoreMap<CCGTagSet2> {
	
	public CCGTagSet2Map(final String input_name) {
		super(input_name);
	}

	@Override
	public CCGTagSet2 loadKeyFromString(final String str) {
		CCGTagSet2 ts = new CCGTagSet2();
		ts.load(str);
		return ts;
	}

	@Override
	public String generateStringFromKey(final CCGTagSet2 key) {
		return "[ " + key.toString() + " ]";
	}

	@Override
	public CCGTagSet2 allocate_key(final CCGTagSet2 key) {
		return new CCGTagSet2(key);
	}
	
}

