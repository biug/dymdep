package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.CoNLLCPOS;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class CoNLLCPOSMap extends PackedScoreMap<CoNLLCPOS> {

	public CoNLLCPOSMap(final String input_name) {
		super(input_name);
	}

	@Override
	public CoNLLCPOS loadKeyFromString(final String str) {
		CoNLLCPOS ccpos = new CoNLLCPOS();
		ccpos.load(str);
		return ccpos;
	}

	@Override
	public String generateStringFromKey(final CoNLLCPOS key) {
		return key.toString();
	}

	@Override
	public CoNLLCPOS allocate_key(final CoNLLCPOS key) {
		return new CoNLLCPOS(key);
	}

}
