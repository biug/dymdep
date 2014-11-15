package include.linguistics;

import include.util.Trigram;

public final class ThreeWords extends Trigram<Word> {

	public ThreeWords() {
		super();
	}
	
	public ThreeWords(final ThreeWords twowords) {
		super(twowords);
	}
	
	public ThreeWords(final Word word1, final Word word2, final Word word3) {
		super(word1, word2, word3);
	}

	@Override
	protected Word create_unigram(final Word word) {
		return new Word(word);
	}
	
}
