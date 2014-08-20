package include.linguistics;

import include.util.Tuple3;

import common.pos.Tag;

/*
 * @author ZhangXun
 */

public final class WordWordTag extends Tuple3<Word, Word, Tag> {
	
	public WordWordTag() {
		super();
	}
	
	public WordWordTag(final WordWordTag word_word_tag) {
		super(word_word_tag);
	}
	
	public WordWordTag(final Word word1, final Word word2, final Tag tag) {
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
	public Tag create_object3(final Tag tag) {
		return new Tag(tag);
	}

}
