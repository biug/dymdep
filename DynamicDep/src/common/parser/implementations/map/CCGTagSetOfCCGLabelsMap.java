package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.CCGTagSetOfCCGLabels;
import include.linguistics.SetOfCCGLabels;

import common.parser.MacrosBase;
import common.pos.CCGTag;

@SuppressWarnings("serial")
public final class CCGTagSetOfCCGLabelsMap extends PackedScoreMap<CCGTagSetOfCCGLabels> {

	public CCGTagSetOfCCGLabelsMap(String input_name) {
		super(input_name);
	}

	@Override
	public CCGTagSetOfCCGLabels allocate_key(CCGTagSetOfCCGLabels key) {
		return new CCGTagSetOfCCGLabels(key);
	}

	@Override
	public CCGTagSetOfCCGLabels loadKeyFromString(String str) {
		String[] args = str.split(" \\$\\$\\$ ");
		SetOfCCGLabels tagset = new SetOfCCGLabels();
		String[] subargs = args[1].substring(2, args[1].length() - 1).split(" ");
		if (!subargs[0].isEmpty()) {
			for (String label : subargs) {
				tagset.add(CCGTag.code(label));				
			}
		}
		return new CCGTagSetOfCCGLabels(new CCGTag(args[0]), tagset);
	}

	@Override
	public String generateStringFromKey(CCGTagSetOfCCGLabels key) {
		String retval = key.first().toString() + " $$$ [ ";
		SetOfCCGLabels sot = key.second();
		for (int label = 0; label < MacrosBase.CCGTAG_COUNT; ++label) {
			if (sot.contains(label)) {
				retval += (CCGTag.str(label) + " ");
			}
		}
		return retval + "]";
	}

}
