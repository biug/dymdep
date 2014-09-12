package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.SetOfLabels;
import include.linguistics.TagSetOfLabels;
import common.dependency.label.DependencyLabel;
import common.parser.implementations.MacrosTree;
import common.pos.POSTag;

/*
 * @author ZhangXun
 */

@SuppressWarnings("serial")
public final class TagSetOfLabelsMap extends PackedScoreMap<TagSetOfLabels> {

	public TagSetOfLabelsMap(final String input_name) {
		super(input_name);
	}

	@Override
	public TagSetOfLabels loadKeyFromString(final String str) {
		String[] args = str.split(" , ");
		SetOfLabels tagset = new SetOfLabels();
		String[] subargs = args[1].substring(2, args[1].length() - 1).split(" ");
		if (!subargs[0].isEmpty()) {
			for (String label : subargs) {
				tagset.add(DependencyLabel.code(label));				
			}
		}
		return new TagSetOfLabels(new POSTag(args[0]), tagset);
	}

	@Override
	public String generateStringFromKey(final TagSetOfLabels key) {
		String retval = key.first().toString() + " , [ ";
		SetOfLabels sot = key.second();
		for (int label = 0; label < MacrosTree.DEP_COUNT; ++label) {
			if (sot.contains(label)) {
				retval += (DependencyLabel.str(label) + " ");
			}
		}
		return retval + "]";
	}

	@Override
	public TagSetOfLabels allocate_key(final TagSetOfLabels key) {
		return new TagSetOfLabels(key);
	}

}