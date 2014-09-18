package include.linguistics;

import common.parser.MacrosBase;
import common.pos.CCGTag;
import common.pos.POSTag;

public class POSCCGTaggedWord {
	
	public final static int WORD_INDENT = MacrosBase.POSTAG_BITS_SIZE + MacrosBase.CCGTAG_BITS_SIZE;
	public final static POSTag empty_postag = new POSTag();
	public final static CCGTag empty_ccgtag = new CCGTag();
	
	public Word word;
	public POSTag postag;
	public CCGTag ccgtag;
	
	public POSCCGTaggedWord() {
		word = new Word();
		postag = new POSTag();
		ccgtag = new CCGTag();
	}
	
	public POSCCGTaggedWord(final String s, final String pt, final String ct) {
		word = new Word(s);
		postag = new POSTag(pt);
		ccgtag = new CCGTag(ct);
	}
	
	public POSCCGTaggedWord(final Word word, final POSTag postag, final CCGTag ccgtag) {
		this.word = word;
		this.postag = postag;
		this.ccgtag = ccgtag;
	}
	
	public POSCCGTaggedWord(final POSCCGTaggedWord taggedword) {
		word = taggedword.word;
		postag = taggedword.postag;
		ccgtag = taggedword.ccgtag;
	}
	
	@Override
	public boolean equals(final Object o) {
		POSCCGTaggedWord taggedword = (POSCCGTaggedWord)o;
		return word.equals(taggedword.word) && postag.equals(taggedword.postag) && ccgtag.equals(taggedword.ccgtag);
	}
	
	@Override
	public int hashCode() {
		return (word.hashCode() << WORD_INDENT) | (postag.hashCode() << MacrosBase.CCGTAG_BITS_SIZE) | (ccgtag.hashCode());
	}
	
	public void load(final Word word, final POSTag postag, final CCGTag ccgtag) {
		this.word = word;
		this.postag = postag;
		this.ccgtag = ccgtag;
	}
}
