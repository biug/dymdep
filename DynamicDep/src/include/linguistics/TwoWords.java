package include.linguistics;

import include.util.Bigram;

/*
 * @author ZhangXun
 */

public final class TwoWords extends Bigram<Word> {

	public TwoWords() {
		super();
	}
	
	public TwoWords(final TwoWords twowords) {
		super(twowords);
	}
	
	public TwoWords(final Word word1, final Word word2) {
		super(word1, word2);
	}

	@Override
	protected Word create_unigram(final Word word) {
		return new Word(word);
	}
	
}
