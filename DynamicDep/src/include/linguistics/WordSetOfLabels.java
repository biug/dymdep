package include.linguistics;

import include.util.Tuple2;

/*
 * @author ZhangXun
 */

public final class WordSetOfLabels extends Tuple2<Word, SetOfDepLabels> {
	
	public WordSetOfLabels() {
		super();
	}
	
	public WordSetOfLabels(final WordSetOfLabels word_tagset) {
		super(word_tagset);
	}
	
	public WordSetOfLabels(final Word word, final SetOfDepLabels tagset) {
		super(word, tagset);
	}

	@Override
	public Word create_object1(final Word word) {
		return new Word(word);
	}

	@Override
	public SetOfDepLabels create_object2(final SetOfDepLabels tagset) {
		return new SetOfDepLabels(tagset);
	}
	
}