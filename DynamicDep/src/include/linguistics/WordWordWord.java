package include.linguistics;

import include.util.Tuple3;

public class WordWordWord extends Tuple3<Word, Word, Word> {

	public WordWordWord() {
		super();
	}
	
	public WordWordWord(WordWordWord word_word_word) {
		super(word_word_word);
	}
	
	public WordWordWord(final Word word1, final Word word2, final Word word3) {
		super(word1, word2, word3);
	}

	@Override
	public Word create_object1(final Word word) {
		return new Word(word);
	}

	@Override
	public Word create_object2(final Word word) {
		return new Word(word);
	}

	@Override
	public Word create_object3(final Word word) {
		return new Word(word);
	}

}
