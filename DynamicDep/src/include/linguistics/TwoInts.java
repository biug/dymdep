package include.linguistics;

import include.util.Bigram;

import common.parser.MacrosBase;

public class TwoInts extends Bigram<Integer> {
	
	public TwoInts() {
		super();
	}
	
	public TwoInts(final TwoInts twoints) {
		super(twoints);
	}
	
	public TwoInts(final Integer int1, final Integer int2) {
		super(int1, int2);
	}
			
	@Override
	protected Integer create_unigram(Integer unigram) {
		return MacrosBase.integer_cache[unigram.intValue()];
	}

}
