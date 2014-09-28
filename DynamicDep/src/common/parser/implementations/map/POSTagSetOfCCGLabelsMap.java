package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.POSTagSetOfCCGLabels;
import include.linguistics.SetOfCCGLabels;

import common.parser.MacrosBase;
import common.pos.CCGTag;
import common.pos.POSTag;

@SuppressWarnings("serial")
public final class POSTagSetOfCCGLabelsMap extends PackedScoreMap<POSTagSetOfCCGLabels> {
	
	public POSTagSetOfCCGLabelsMap(String input_name) {
		super(input_name);
	}

	@Override
	public POSTagSetOfCCGLabels allocate_key(POSTagSetOfCCGLabels key) {
		return new POSTagSetOfCCGLabels(key);
	}

	@Override
	public POSTagSetOfCCGLabels loadKeyFromString(String str) {
		String[] args = str.split(" \\$\\$\\$ ");
		SetOfCCGLabels tagset = new SetOfCCGLabels();
		String[] subargs = args[1].substring(2, args[1].length() - 1).split(" ");
		if (!subargs[0].isEmpty()) {
			for (String label : subargs) {
				tagset.add(CCGTag.code(label));				
			}
		}
		return new POSTagSetOfCCGLabels(new POSTag(args[0]), tagset);
	}

	@Override
	public String generateStringFromKey(POSTagSetOfCCGLabels key) {
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
