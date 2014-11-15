package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.POSTagSet4;

@SuppressWarnings("serial")
public final class POSTagSet4Map extends PackedScoreMap<POSTagSet4> {
	
	public POSTagSet4Map(final String input_name) {
		super(input_name);
	}

	@Override
	public POSTagSet4 loadKeyFromString(final String str) {
		POSTagSet4 ts = new POSTagSet4();
		ts.load(str);
		return ts;
	}

	@Override
	public String generateStringFromKey(final POSTagSet4 key) {
		return "[ " + key.toString() + " ]";
	}

	@Override
	public POSTagSet4 allocate_key(final POSTagSet4 key) {
		return new POSTagSet4(key);
	}
	
}