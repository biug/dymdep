package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.TagSet2;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class TagSet2Map extends PackedScoreMap<TagSet2> {
	
	public TagSet2Map(final String input_name, final int table_size) {
		super(input_name, table_size);
	}

	@Override
	public TagSet2 loadKeyFromString(final String str) {
		TagSet2 ts = new TagSet2();
		ts.load(str);
		return ts;
	}

	@Override
	public String generateStringFromKey(final TagSet2 key) {
		return "[ " + key.toString() + " ]";
	}

	@Override
	public TagSet2 allocate_key(final TagSet2 key) {
		return new TagSet2(key);
	}
	
}

