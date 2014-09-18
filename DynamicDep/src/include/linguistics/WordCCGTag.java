package include.linguistics;

import include.util.Tuple2;

import common.pos.CCGTag;

/*
 * @author ZhangXun
 */

public final class WordCCGTag extends Tuple2<Word, CCGTag> {
	
	public WordCCGTag() {
		super();
	}
	
	public WordCCGTag(final WordCCGTag word_tag) {
		super(word_tag);
	}
	
	public WordCCGTag(final Word word, final CCGTag tag) {
		super(word, tag);
	}

	@Override
	public Word create_object1(final Word word) {
		return new Word(word);
	}

	@Override
	public CCGTag create_object2(final CCGTag tag) {
		return new CCGTag(tag);
	}
	
}