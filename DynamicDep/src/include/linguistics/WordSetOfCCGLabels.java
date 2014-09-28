package include.linguistics;

import include.util.Tuple2;

public final class WordSetOfCCGLabels extends Tuple2<Word, SetOfCCGLabels> {
	
	public WordSetOfCCGLabels() {
		super();
	}
	
	public WordSetOfCCGLabels(final WordSetOfCCGLabels word_tagset) {
		super(word_tagset);
	}
	
	public WordSetOfCCGLabels(final Word word, final SetOfCCGLabels tagset) {
		super(word, tagset);
	}

	@Override
	public Word create_object1(Word word) {
		return new Word(word);
	}

	@Override
	public SetOfCCGLabels create_object2(SetOfCCGLabels tagset) {
		return new SetOfCCGLabels(tagset);
	}

}
