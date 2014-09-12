package include.linguistics;

import include.util.Tuple2;

import common.pos.POSTag;

/*
 * @author ZhangXun
 */

public final class WordTag extends Tuple2<Word, POSTag> {
	
	public WordTag() {
		super();
	}
	
	public WordTag(final WordTag word_tag) {
		super(word_tag);
	}
	
	public WordTag(final Word word, final POSTag tag) {
		super(word, tag);
	}

	@Override
	public Word create_object1(final Word word) {
		return new Word(word);
	}

	@Override
	public POSTag create_object2(final POSTag tag) {
		return new POSTag(tag);
	}
	
}