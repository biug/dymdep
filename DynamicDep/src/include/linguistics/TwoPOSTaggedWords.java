package include.linguistics;

import include.util.Bigram;

/*
 * @author ZhangXun
 */

public final class TwoPOSTaggedWords extends Bigram<POSTaggedWord> {
	
	public TwoPOSTaggedWords() {
		super();
	}
	
	public TwoPOSTaggedWords(final TwoPOSTaggedWords twotaggedwords) {
		super(twotaggedwords);
	}
	
	public TwoPOSTaggedWords(final POSTaggedWord taggedword1, final POSTaggedWord taggedword2) {
		super(taggedword1, taggedword2);
	}

	@Override
	protected POSTaggedWord create_unigram(final POSTaggedWord taggedword) {
		return new POSTaggedWord(taggedword);
	}

}