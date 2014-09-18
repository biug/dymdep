package include.linguistics;

import include.util.Tuple3;

import common.pos.POSTag;

/*
 * @author ZhangXun
 */

public final class WordWordPOSTag extends Tuple3<Word, Word, POSTag> {
	
	public WordWordPOSTag() {
		super();
	}
	
	public WordWordPOSTag(final WordWordPOSTag word_word_tag) {
		super(word_word_tag);
	}
	
	public WordWordPOSTag(final Word word1, final Word word2, final POSTag tag) {
		super(word1, word2, tag);
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
	public POSTag create_object3(final POSTag tag) {
		return new POSTag(tag);
	}

}
