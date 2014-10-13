package include.linguistics;

import include.util.Bigram;

/*
 * @author ZhangXun
 */

public final class TwoWordInts extends Bigram<WordInt> {
	
	public TwoWordInts() {
		super();
	}
	
	public TwoWordInts(final TwoWordInts twotaggedwords) {
		super(twotaggedwords);
	}
	
	public TwoWordInts(final WordInt wordint1, final WordInt wordint2) {
		super(wordint1, wordint2);
	}

	@Override
	protected WordInt create_unigram(final WordInt wordint) {
		return new WordInt(wordint);
	}

}