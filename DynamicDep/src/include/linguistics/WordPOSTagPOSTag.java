package include.linguistics;

import include.util.Tuple3;

import common.pos.POSTag;

/*
 * @author ZhangXun
 */

public final class WordPOSTagPOSTag extends Tuple3<Word, POSTag, POSTag> {
	
	public WordPOSTagPOSTag() {
		super();
	}
	
	public WordPOSTagPOSTag(final WordPOSTagPOSTag word_tag_tag) {
		super(word_tag_tag);
	}
	
	public WordPOSTagPOSTag(final Word word, final POSTag tag1, final POSTag tag2) {
		super(word, tag1, tag2);
	}

	@Override
	public Word create_object1(final Word word) {
		return new Word(word);
	}

	@Override
	public POSTag create_object2(final POSTag tag) {
		return new POSTag(tag);
	}

	@Override
	public POSTag create_object3(final POSTag tag) {
		return new POSTag(tag);
	}

}
