package include.linguistics;

import include.util.Tuple4;

import common.pos.POSTag;

public class WordPOSTagPOSTagPOSTag extends Tuple4<Word, POSTag, POSTag, POSTag> {
	
	public WordPOSTagPOSTagPOSTag() {
		super();
	}
	
	public WordPOSTagPOSTagPOSTag(WordPOSTagPOSTagPOSTag word_postag_postag_postag) {
		super(word_postag_postag_postag);
	}
	
	public WordPOSTagPOSTagPOSTag(Word word, POSTag postag1, POSTag postag2, POSTag postag3) {
		super(word, postag1, postag2, postag3);
	}

	@Override
	public Word create_object1(Word word) {
		return new Word(word);
	}

	@Override
	public POSTag create_object2(POSTag postag) {
		return new POSTag(postag);
	}
	
	@Override
	public POSTag create_object3(POSTag postag) {
		return new POSTag(postag);
	}
	
	@Override
	public POSTag create_object4(POSTag postag) {
		return new POSTag(postag);
	}
	
}
