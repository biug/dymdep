package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.SetOfCCGLabelsInt;
import include.linguistics.SetOfCCGLabels;

import common.parser.MacrosBase;
import common.pos.CCGTag;

@SuppressWarnings("serial")
public final class SetOfCCGLabelsIntMap extends PackedScoreMap<SetOfCCGLabelsInt> {

	public SetOfCCGLabelsIntMap(String input_name) {
		super(input_name);
	}

	@Override
	public SetOfCCGLabelsInt allocate_key(SetOfCCGLabelsInt key) {
		return new SetOfCCGLabelsInt(key);
	}

	@Override
	public SetOfCCGLabelsInt loadKeyFromString(String str) {
		String[] args = str.split(" \\$\\$\\$ ");
		SetOfCCGLabels tagset = new SetOfCCGLabels();
		String[] subargs = args[0].substring(2, args[0].length() - 1).split(" ");
		if (!subargs[0].isEmpty()) {
			for (String label : subargs) {
				tagset.add(CCGTag.code(label));				
			}
		}
		return new SetOfCCGLabelsInt(tagset, MacrosBase.integer_cache[Integer.parseInt(args[1])]);
	}

	@Override
	public String generateStringFromKey(SetOfCCGLabelsInt key) {
		String retval = "[ ";
		SetOfCCGLabels sot = key.first();
		for (int label = 0; label < MacrosBase.CCGTAG_COUNT; ++label) {
			if (sot.contains(label)) {
				retval += (CCGTag.str(label) + " ");
			}
		}
		return retval + "] $$$ " + key.second().toString();
	}

}
