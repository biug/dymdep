package include.linguistics;

import include.util.Tuple4;

import common.pos.POSTag;

public class WordWordPOSTagPOSTag extends Tuple4<Word, Word, POSTag, POSTag> {
	
	public WordWordPOSTagPOSTag() {
		super();
	}
	
	public WordWordPOSTagPOSTag(WordWordPOSTagPOSTag word_word_postag_postag) {
		super(word_word_postag_postag);
	}
	
	public WordWordPOSTagPOSTag(Word word1, Word word2, POSTag postag1, POSTag postag2) {
		super(word1, word2, postag1, postag2);
	}

	@Override
	public Word create_object1(Word word) {
		return new Word(word);
	}

	@Override
	public Word create_object2(Word word) {
		return new Word(word);
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
