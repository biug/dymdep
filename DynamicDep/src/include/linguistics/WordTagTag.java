package include.linguistics;

import include.util.Tuple3;

import common.pos.Tag;

/*
 * @author ZhangXun
 */

public final class WordTagTag extends Tuple3<Word, Tag, Tag> {
	
	public WordTagTag() {
		super();
	}
	
	public WordTagTag(final WordTagTag word_tag_tag) {
		super(word_tag_tag);
	}
	
	public WordTagTag(final Word word, final Tag tag1, final Tag tag2) {
		super(word, tag1, tag2);
	}

	@Override
	public Word create_object1(final Word word) {
		return new Word(word);
	}

	@Override
	public Tag create_object2(final Tag tag) {
		return new Tag(tag);
	}

	@Override
	public Tag create_object3(final Tag tag) {
		return new Tag(tag);
	}

}
