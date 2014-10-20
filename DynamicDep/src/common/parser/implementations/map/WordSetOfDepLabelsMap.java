package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.SetOfDepLabels;
import include.linguistics.Word;
import include.linguistics.WordSetOfDepLabels;

import common.dependency.label.DependencyLabel;
import common.parser.MacrosBase;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class WordSetOfDepLabelsMap extends PackedScoreMap<WordSetOfDepLabels> {

	public WordSetOfDepLabelsMap(final String input_name) {
		super(input_name);
	}

	@Override
	public WordSetOfDepLabels loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		SetOfDepLabels tagset = new SetOfDepLabels();
		String[] subargs = args[1].substring(2, args[1].length() - 1).split(" ");
		if (!subargs[0].isEmpty()) {
			for (String label : subargs) {
				tagset.add(DependencyLabel.code(label));				
			}
		}
		return new WordSetOfDepLabels(new Word(args[0].substring(1, args[0].length() - 1)), tagset);
	}

	@Override
	public String generateStringFromKey(final WordSetOfDepLabels key) {
		String retval = "[" + key.first().toString() + "] , [ ";
		SetOfDepLabels sot = key.second();
		for (int label = 0; label < MacrosBase.DEP_COUNT; ++label) {
			if (sot.contains(label)) {
				retval += (DependencyLabel.str(label) + " ");
			}
		}
		return retval + "]";
	}

	@Override
	public WordSetOfDepLabels allocate_key(final WordSetOfDepLabels key) {
		return new WordSetOfDepLabels(key);
	}

}
