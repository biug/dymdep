package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.SetOfLabels;
import include.linguistics.POSTagSetOfLabels;

import common.dependency.label.DependencyLabel;
import common.parser.MacrosBase;
import common.pos.POSTag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class POSTagSetOfLabelsMap extends PackedScoreMap<POSTagSetOfLabels> {

	public POSTagSetOfLabelsMap(final String input_name) {
		super(input_name);
	}

	@Override
	public POSTagSetOfLabels loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		SetOfLabels tagset = new SetOfLabels();
		String[] subargs = args[1].substring(2, args[1].length() - 1).split(" ");
		if (!subargs[0].isEmpty()) {
			for (String label : subargs) {
				tagset.add(DependencyLabel.code(label));				
			}
		}
		return new POSTagSetOfLabels(new POSTag(args[0]), tagset);
	}

	@Override
	public String generateStringFromKey(final POSTagSetOfLabels key) {
		String retval = key.first().toString() + " , [ ";
		SetOfLabels sot = key.second();
		for (int label = 0; label < MacrosBase.DEP_COUNT; ++label) {
			if (sot.contains(label)) {
				retval += (DependencyLabel.str(label) + " ");
			}
		}
		return retval + "]";
	}

	@Override
	public POSTagSetOfLabels allocate_key(final POSTagSetOfLabels key) {
		return new POSTagSetOfLabels(key);
	}

}