package include.linguistics;

import common.parser.MacrosBase;
import common.pos.POSTag;

/*
 * @author ZhangXun
 */

public final class POSTaggedWord {
	
	private static final POSTag empty_tag = new POSTag(MacrosBase.POSTAG_FIRST);
	
	public Word word;
	public POSTag tag;
	
	public POSTaggedWord() {
		word = new Word();
		tag = new POSTag();
	}
	
	public POSTaggedWord(final String s, final String t) {
		word = new Word(s);
		tag = new POSTag(t);
	}
	
	public POSTaggedWord(final Word word, final POSTag tag) {
		this.word = word;
		this.tag = tag;
	}
	
	public POSTaggedWord(final POSTaggedWord taggedword) {
		word = taggedword.word;
		tag = taggedword.tag;
	}
	
	@Override
	public boolean equals(final Object o) {
		POSTaggedWord taggedword = (POSTaggedWord)o;
		return word.equals(taggedword.word) && tag.equals(taggedword.tag);
	}
	
	@Override
	public int hashCode() {
		return ((word.hashCode()) << MacrosBase.POSTAG_BITS_SIZE) | (tag.hashCode());
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
