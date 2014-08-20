package include.linguistics;

import include.util.Tuple2;

import common.pos.Tag;

/*
 * @author ZhangXun
 */

public final class WordTag extends Tuple2<Word, Tag> {
	
	public WordTag() {
		super();
	}
	
	public WordTag(final WordTag word_tag) {
		super(word_tag);
	}
	
	public WordTag(final Word word, final Tag tag) {
		super(word, tag);
	}

	@Override
	public Word create_object1(final Word word) {
		return new Word(word);
	}

	@Override
	public Tag create_object2(final Tag tag) {
		return new Tag(tag);
	}
	
}