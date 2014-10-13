package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.SetOfDepLabels;
import include.linguistics.SetOfDepLabelsInt;

import common.dependency.label.DependencyLabel;
import common.parser.MacrosBase;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class SetOfDepLabelsIntMap extends PackedScoreMap<SetOfDepLabelsInt> {

	public SetOfDepLabelsIntMap(final String input_name) {
		super(input_name);
	}

	@Override
	public SetOfDepLabelsInt loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		SetOfDepLabels tagset = new SetOfDepLabels();
		String[] subargs = args[0].substring(2, args[0].length() - 1).split(" ");
		if (!subargs[0].isEmpty()) {
			for (String label : subargs) {
				tagset.add(DependencyLabel.code(label));				
			}
		}
		return new SetOfDepLabelsInt(tagset, MacrosBase.integer_cache[Integer.parseInt(args[1])]);
	}

	@Override
	public String generateStringFromKey(final SetOfDepLabelsInt key) {
		String retval = "[ ";
		SetOfDepLabels sot = key.first();
		for (int label = 0; label < MacrosBase.DEP_COUNT; ++label) {
			if (sot.contains(label)) {
				retval += (DependencyLabel.str(label) + " ");
			}
		}
		return retval + "] , " + key.second().toString();
	}

	@Override
	public SetOfDepLabelsInt allocate_key(final SetOfDepLabelsInt key) {
		return new SetOfDepLabelsInt(key);
	}

}