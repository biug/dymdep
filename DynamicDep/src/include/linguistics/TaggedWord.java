package include.linguistics;

import common.parser.implementations.arceager.Macros;
import common.pos.Tag;

/*
 * @author ZhangXun
 */

public final class TaggedWord {
	
	private static final Tag empty_tag = new Tag(Macros.TAG_NONE);
	
	public Word word;
	public Tag tag;
	
	public TaggedWord() {
		word = new Word();
		tag = new Tag();
	}
	
	public TaggedWord(final String s, final String t) {
		word = new Word(s);
		tag = new Tag(t);
	}
	
	public TaggedWord(final Word word, final Tag tag) {
		this.word = word;
		this.tag = tag;
	}
	
	public TaggedWord(final TaggedWord taggedword) {
		word = taggedword.word;
		tag = taggedword.tag;
	}
	
	@Override
	public boolean equals(final Object o) {
		TaggedWord taggedword = (TaggedWord)o;
		return word.equals(taggedword.word) && tag.equals(taggedword.tag);
	}
	
	@Override
	public int hashCode() {
		return ((word.hashCode()) << Macros.TAG_BITS_SIZE) | (tag.hashCode());
	}
	
	public void load(final Word word, final Tag tag) {
		this.word = word;
		this.tag = tag;
	}
	
	public void load(final Word word) {
		this.word = word;
		tag = empty_tag;
	}
}
