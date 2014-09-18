package include.linguistics;

import common.parser.MacrosBase;
import common.pos.CCGTag;

/*
 * @author ZhangXun
 */

public final class CCGTaggedWord {
	
	private static final CCGTag empty_tag = new CCGTag(MacrosBase.CCGTAG_NONE);
	
	public Word word;
	public CCGTag tag;
	
	public CCGTaggedWord() {
		word = new Word();
		tag = new CCGTag();
	}
	
	public CCGTaggedWord(final String s, final String t) {
		word = new Word(s);
		tag = new CCGTag(t);
	}
	
	public CCGTaggedWord(final Word word, final CCGTag tag) {
		this.word = word;
		this.tag = tag;
	}
	
	public CCGTaggedWord(final CCGTaggedWord taggedword) {
		word = taggedword.word;
		tag = taggedword.tag;
	}
	
	@Override
	public boolean equals(final Object o) {
		CCGTaggedWord taggedword = (CCGTaggedWord)o;
		return word.equals(taggedword.word) && tag.equals(taggedword.tag);
	}
	
	@Override
	public int hashCode() {
		return ((word.hashCode()) << MacrosBase.CCGTAG_BITS_SIZE) | (tag.hashCode());
	}
	
	public void load(final Word word, final CCGTag tag) {
		this.word = word;
		this.tag = tag;
	}
	
	public void load(final Word word) {
		this.word = word;
		tag = empty_tag;
	}
}
