package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.SetOfDepLabels;
import include.linguistics.POSTagSetOfDepLabels;

import common.dependency.label.DependencyLabel;
import common.parser.MacrosBase;
import common.pos.POSTag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class POSTagSetOfDepLabelsMap extends PackedScoreMap<POSTagSetOfDepLabels> {

	public POSTagSetOfDepLabelsMap(final String input_name) {
		super(input_name);
	}

	@Override
	public POSTagSetOfDepLabels loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		SetOfDepLabels tagset = new SetOfDepLabels();
		String[] subargs = args[1].substring(2, args[1].length() - 1).split(" ");
		if (!subargs[0].isEmpty()) {
			for (String label : subargs) {
				tagset.add(DependencyLabel.code(label));				
			}
		}
		return new POSTagSetOfDepLabels(new POSTag(args[0]), tagset);
	}

	@Override
	public String generateStringFromKey(final POSTagSetOfDepLabels key) {
		String retval = key.first().toString() + " , [ ";
		SetOfDepLabels sot = key.second();
		for (int label = 0; label < MacrosBase.DEP_COUNT; ++label) {
			if (sot.contains(label)) {
				retval += (DependencyLabel.str(label) + " ");
			}
		}
		return retval + "]";
	}

	@Override
	public POSTagSetOfDepLabels allocate_key(final POSTagSetOfDepLabels key) {
		return new POSTagSetOfDepLabels(key);
	}

}