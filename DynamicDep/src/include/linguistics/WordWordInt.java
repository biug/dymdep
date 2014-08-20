package include.linguistics;

import include.util.Tuple3;

/*
 * @author ZhangXun
 */

public final class WordWordInt extends Tuple3<Word, Word, Integer> {

	public WordWordInt() {
		super();
	}
	
	public WordWordInt(WordWordInt word_word_int) {
		super(word_word_int);
	}
	
	public WordWordInt(final Word word1, final Word word2, final Integer integer) {
		super(word1, word2, integer);
	}

	@Override
	public Word create_object1(final Word word) {
		return new Word(word);
	}

	@Override
	public Word create_object2(final Word word) {
		return new Word(word);
	}

	@Override
	public Integer create_object3(final Integer integer) {
		return new Integer(integer);
	}

}
