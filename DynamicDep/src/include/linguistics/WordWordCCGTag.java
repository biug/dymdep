package include.linguistics;

import include.util.Tuple3;

import common.pos.CCGTag;

/*
 * @author ZhangXun
 */

public final class WordWordCCGTag extends Tuple3<Word, Word, CCGTag> {
	
	public WordWordCCGTag() {
		super();
	}
	
	public WordWordCCGTag(final WordWordCCGTag word_word_tag) {
		super(word_word_tag);
	}
	
	public WordWordCCGTag(final Word word1, final Word word2, final CCGTag tag) {
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
	public CCGTag create_object3(final CCGTag tag) {
		return new CCGTag(tag);
	}

}
