package include.linguistics;

import include.util.Bigram;

/*
 * @author ZhangXun
 */

public final class TwoTaggedWords extends Bigram<TaggedWord> {
	
	public TwoTaggedWords() {
		super();
	}
	
	public TwoTaggedWords(final TwoTaggedWords twotaggedwords) {
		super(twotaggedwords);
	}
	
	public TwoTaggedWords(final TaggedWord taggedword1, final TaggedWord taggedword2) {
		super(taggedword1, taggedword2);
	}

	@Override
	protected TaggedWord create_unigram(final TaggedWord taggedword) {
		return new TaggedWord(taggedword);
	}

}