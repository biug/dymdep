package include.linguistics;

import include.util.Tuple4;

import common.pos.POSTag;

public class WordWordWordPOSTag extends Tuple4<Word, Word, Word, POSTag> {
	
	public WordWordWordPOSTag() {
		super();
	}
	
	public WordWordWordPOSTag(WordWordWordPOSTag word_word_word_postag) {
		super(word_word_word_postag);
	}
	
	public WordWordWordPOSTag(Word word1, Word word2, Word word3, POSTag postag) {
		super(word1, word2, word3, postag);
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
	public Word create_object3(Word word) {
		return new Word(word);
	}
	
	@Override
	public POSTag create_object4(POSTag postag) {
		return new POSTag(postag);
	}
	
}
