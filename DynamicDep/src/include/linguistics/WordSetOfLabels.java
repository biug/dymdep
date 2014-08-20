package include.linguistics;

import include.util.Tuple2;

/*
 * @author ZhangXun
 */

public final class WordSetOfLabels extends Tuple2<Word, SetOfLabels> {
	
	public WordSetOfLabels() {
		super();
	}
	
	public WordSetOfLabels(final WordSetOfLabels word_tagset) {
		super(word_tagset);
	}
	
	public WordSetOfLabels(final Word word, final SetOfLabels tagset) {
		super(word, tagset);
	}

	@Override
	public Word create_object1(final Word word) {
		return new Word(word);
	}

	@Override
	public SetOfLabels create_object2(final SetOfLabels tagset) {
		return new SetOfLabels(tagset);
	}
	
}