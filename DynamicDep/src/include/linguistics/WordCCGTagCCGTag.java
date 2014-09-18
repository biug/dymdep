package include.linguistics;

import include.util.Tuple3;

import common.pos.CCGTag;

/*
 * @author ZhangXun
 */

public final class WordCCGTagCCGTag extends Tuple3<Word, CCGTag, CCGTag> {
	
	public WordCCGTagCCGTag() {
		super();
	}
	
	public WordCCGTagCCGTag(final WordCCGTagCCGTag word_tag_tag) {
		super(word_tag_tag);
	}
	
	public WordCCGTagCCGTag(final Word word, final CCGTag tag1, final CCGTag tag2) {
		super(word, tag1, tag2);
	}

	@Override
	public Word create_object1(final Word word) {
		return new Word(word);
	}

	@Override
	public CCGTag create_object2(final CCGTag tag) {
		return new CCGTag(tag);
	}

	@Override
	public CCGTag create_object3(final CCGTag tag) {
		return new CCGTag(tag);
	}

}
