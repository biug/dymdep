package common.parser.implementations.map;

import include.learning.perceptron.PackedScoreMap;
import include.linguistics.SyntaxTreePath;

import java.util.ArrayList;

import common.parser.MacrosBase;

@SuppressWarnings("serial")
public class SyntaxTreePathMap extends PackedScoreMap<SyntaxTreePath> {

	public SyntaxTreePathMap(String input_name) {
		super(input_name);
	}

	@Override
	public SyntaxTreePath allocate_key(SyntaxTreePath key) {
		return new SyntaxTreePath(key);
	}

	@Override
	public SyntaxTreePath loadKeyFromString(String str) {
		ArrayList<String> poses = new ArrayList<String>();
		ArrayList<Integer> labels = new ArrayList<Integer>();
		String[] args = str.substring(1, str.length() - 1).split(" @ ");
		String[] subargs1 = args[0].substring(1, args[0].length() - 1).split(" ");
		for (String arg : subargs1) {
			poses.add(arg);
		}
		String[] subargs2 = args[1].substring(1, args[1].length() - 1).split(" ");
		for (String arg : subargs2) {
			int i = Integer.parseInt(arg);
			labels.add(i == -1 ? MacrosBase.integer_cache[MacrosBase.MAX_INTEGER] : MacrosBase.integer_cache[i]);
		}
		return new SyntaxTreePath(poses, labels);
	}

	@Override
	public String generateStringFromKey(SyntaxTreePath key) {
		return key.toString();
	}

}
