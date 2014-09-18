package include.linguistics;

import include.util.Bigram;

/*
 * @author ZhangXun
 */

public final class TwoCCGTaggedWords extends Bigram<CCGTaggedWord> {
	
	public TwoCCGTaggedWords() {
		super();
	}
	
	public TwoCCGTaggedWords(final TwoCCGTaggedWords twotaggedwords) {
		super(twotaggedwords);
	}
	
	public TwoCCGTaggedWords(final CCGTaggedWord taggedword1, final CCGTaggedWord taggedword2) {
		super(taggedword1, taggedword2);
	}

	@Override
	protected CCGTaggedWord create_unigram(final CCGTaggedWord taggedword) {
		return new CCGTaggedWord(taggedword);
	}

}