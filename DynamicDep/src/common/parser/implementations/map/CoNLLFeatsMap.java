package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.CoNLLFeats;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class CoNLLFeatsMap extends PackedScoreMap<CoNLLFeats> {

	public CoNLLFeatsMap(final String input_name) {
		super(input_name);
	}

	@Override
	public CoNLLFeats loadKeyFromString(final String str) {
		CoNLLFeats cf = new CoNLLFeats();
		cf.load(str);
		return cf;
	}

	@Override
	public String generateStringFromKey(final CoNLLFeats key) {
		return key.toString();
	}

	@Override
	public CoNLLFeats allocate_key(final CoNLLFeats key) {
		return new CoNLLFeats(key);
	}

}

