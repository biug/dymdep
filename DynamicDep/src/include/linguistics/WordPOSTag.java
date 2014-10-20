package include.linguistics;

import include.util.Tuple2;

import common.pos.POSTag;

/*
 * @author ZhangXun
 */

public final class WordPOSTag extends Tuple2<Word, POSTag> {
	
	public WordPOSTag() {
		super();
	}
	
	public WordPOSTag(final WordPOSTag word_tag) {
		super(word_tag);
	}
	
	public WordPOSTag(final Word word, final POSTag tag) {
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