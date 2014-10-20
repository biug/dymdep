package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.POSTagSet2;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class POSTagSet2Map extends PackedScoreMap<POSTagSet2> {
	
	public POSTagSet2Map(final String input_name) {
		super(input_name);
	}

	@Override
	public POSTagSet2 loadKeyFromString(final String str) {
		POSTagSet2 ts = new POSTagSet2();
		ts.load(str);
		return ts;
	}

	@Override
	public String generateStringFromKey(final POSTagSet2 key) {
		return "[ " + key.toString() + " ]";
	}

	@Override
	public POSTagSet2 allocate_key(final POSTagSet2 key) {
		return new POSTagSet2(key);
	}
	
}

