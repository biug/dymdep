package include.linguistics;

import common.parser.implementations.MacrosTree;
import common.pos.POSTag;

/*
 * @author ZhangXun
 */

public final class TaggedWord {
	
	private static final POSTag empty_tag = new POSTag(MacrosTree.POSTAG_NONE);
	
	public Word word;
	public POSTag tag;
	
	public TaggedWord() {
		word = new Word();
		tag = new POSTag();
	}
	
	public TaggedWord(final String s, final String t) {
		word = new Word(s);
		tag = new POSTag(t);
	}
	
	public TaggedWord(final Word word, final POSTag tag) {
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
		return ((word.hashCode()) << MacrosTree.POSTAG_BITS_SIZE) | (tag.hashCode());
	}
	
	public void load(final Word word, final POSTag tag) {
		this.word = word;
		this.tag = tag;
	}
	
	public void load(final Word word) {
		this.word = word;
		tag = empty_tag;
	}
}
