package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.Lemma;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class LemmaMap extends PackedScoreMap<Lemma> {

	public LemmaMap(final String input_name, final int table_size) {
		super(input_name, table_size);
	}

	@Override
	public Lemma loadKeyFromString(final String str) {
		return new Lemma(str);
	}

	@Override
	public String generateStringFromKey(final Lemma key) {
		return "[" + key.toString() + "]";
	}

	@Override
	public Lemma allocate_key(final Lemma key) {
		return new Lemma(key);
	}

}
