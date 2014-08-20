package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.TagSet3;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class TagSet3Map extends PackedScoreMap<TagSet3> {
	
	public TagSet3Map(final String input_name, final int table_size) {
		super(input_name, table_size);
	}

	@Override
	public TagSet3 loadKeyFromString(final String str) {
		TagSet3 ts = new TagSet3();
		ts.load(str);
		return ts;
	}

	@Override
	public String generateStringFromKey(final TagSet3 key) {
		return "[ " + key.toString() + " ]";
	}

	@Override
	public TagSet3 allocate_key(final TagSet3 key) {
		return new TagSet3(key);
	}
	
}
