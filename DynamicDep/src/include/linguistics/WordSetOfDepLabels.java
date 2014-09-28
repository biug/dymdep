package include.linguistics;

import include.util.Tuple2;

/*
 * @author ZhangXun
 */

public final class WordSetOfDepLabels extends Tuple2<Word, SetOfDepLabels> {
	
	public WordSetOfDepLabels() {
		super();
	}
	
	public WordSetOfDepLabels(final WordSetOfDepLabels word_tagset) {
		super(word_tagset);
	}
	
	public WordSetOfDepLabels(final Word word, final SetOfDepLabels tagset) {
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