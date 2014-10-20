package include.linguistics;

import include.util.Tuple2;

/*
 * @author ZhangXun
 */

public final class WordInt extends Tuple2<Word, Integer> {
	
	public WordInt() {
		super();
	}
	
	public WordInt(final WordInt word_int) {
		super(word_int);
	}
	
	public WordInt(final Word word, final Integer integer) {
		super(word, integer);
	}

	@Override
	public Word create_object1(final Word word) {
		return new Word(word);
	}

	@Override
	public Integer create_object2(final Integer integer) {
		return new Integer(integer);
	}

}
