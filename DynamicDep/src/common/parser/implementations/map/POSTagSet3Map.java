package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.POSTagSet3;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class POSTagSet3Map extends PackedScoreMap<POSTagSet3> {
	
	public POSTagSet3Map(final String input_name) {
		super(input_name);
	}

	@Override
	public POSTagSet3 loadKeyFromString(final String str) {
		POSTagSet3 ts = new POSTagSet3();
		ts.load(str);
		return ts;
	}

	@Override
	public String generateStringFromKey(final POSTagSet3 key) {
		return "[ " + key.toString() + " ]";
	}

	@Override
	public POSTagSet3 allocate_key(final POSTagSet3 key) {
		return new POSTagSet3(key);
	}
	
}
