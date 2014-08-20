package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.SetOfLabels;
import include.linguistics.Word;
import include.linguistics.WordSetOfLabels;

import common.dependency.label.DependencyLabel;
import common.parser.implementations.arceager.Macros;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class WordSetOfLabelsMap extends PackedScoreMap<WordSetOfLabels> {

	public WordSetOfLabelsMap(final String input_name) {
		super(input_name);
	}

	@Override
	public WordSetOfLabels loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		SetOfLabels tagset = new SetOfLabels();
		String[] subargs = args[1].substring(2, args[1].length() - 1).split(" ");
		if (!subargs[0].isEmpty()) {
			for (String label : subargs) {
				tagset.add(DependencyLabel.code(label));				
			}
		}
		return new WordSetOfLabels(new Word(args[0].substring(1, args[0].length() - 1)), tagset);
	}

	@Override
	public String generateStringFromKey(final WordSetOfLabels key) {
		String retval = "[" + key.first().toString() + "] , [ ";
		SetOfLabels sot = key.second();
		for (int label = 0; label < Macros.DEP_COUNT; ++label) {
			if (sot.contains(label)) {
				retval += (DependencyLabel.str(label) + " ");
			}
		}
		return retval + "]";
	}

	@Override
	public WordSetOfLabels allocate_key(final WordSetOfLabels key) {
		return new WordSetOfLabels(key);
	}

}
