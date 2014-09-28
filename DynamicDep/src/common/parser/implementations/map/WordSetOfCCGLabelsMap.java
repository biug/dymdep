package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.SetOfCCGLabels;
import include.linguistics.Word;
import include.linguistics.WordSetOfCCGLabels;
import common.parser.MacrosBase;
import common.pos.CCGTag;

@SuppressWarnings("serial")
public class WordSetOfCCGLabelsMap extends PackedScoreMap<WordSetOfCCGLabels> {

	public WordSetOfCCGLabelsMap(String input_name) {
		super(input_name);
	}

	@Override
	public WordSetOfCCGLabels allocate_key(WordSetOfCCGLabels key) {
		return new WordSetOfCCGLabels(key);
	}

	@Override
	public WordSetOfCCGLabels loadKeyFromString(String str) {
		String[] args = str.split(" \\$\\$\\$ ");
		SetOfCCGLabels tagset = new SetOfCCGLabels();
		String[] subargs = args[1].substring(2, args[1].length() - 1).split(" ");
		if (!subargs[0].isEmpty()) {
			for (String label : subargs) {
				tagset.add(CCGTag.code(label));				
			}
		}
		return new WordSetOfCCGLabels(new Word(args[0].substring(1, args[0].length() - 1)), tagset);
	}

	@Override
	public String generateStringFromKey(WordSetOfCCGLabels key) {
		String retval = "[" + key.first().toString() + "] $$$ [ ";
		SetOfCCGLabels sot = key.second();
		for (int label = 0; label < MacrosBase.CCGTAG_COUNT; ++label) {
			if (sot.contains(label)) {
				retval += (CCGTag.str(label) + " ");
			}
		}
		return retval + "]";
	}

}
